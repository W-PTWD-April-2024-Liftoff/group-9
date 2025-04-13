import {useNavigate, useParams, Link} from "react-router-dom";
import {useState, useEffect} from "react";
import axios from "axios";
import style from "./TripDetails.module.css";
import TrailTripButton from "./Buttons/TrailTripButton.jsx";
import CampTripButton from "./Buttons/CampTripButton.jsx";

export default function TripDetails() {
    const {tripId} = useParams();
    const navigate = useNavigate();

    //State management
    const [trip, setTrip] = useState(location.state?.trip); // Local copy of trip data
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [park, setPark] = useState(null);

    // Fetch trip details when the component mounts
    useEffect(() => {
        const fetchTrip = async () => {
            setLoading(true); // Start loading
            setError(null); // Clear any previous errors

            try {
                console.log(`Fetching trip with tripId: ${tripId}`); // Debug log
                const response = await axios.get(`http://localhost:8081/trips/${tripId}`);
                console.log(`Trip fetched successfully: `, response.data); // Debug log

                if (response.data) {
                    setTrip(response.data); // Set trip data
                } else {
                    setError("No trip found for the provided trip ID."); // Handle empty data
                }
            } catch (err) {
                console.error("Error fetching trip details:", err); // Debug log
                setError("Failed to load trip details.");
            } finally {
                setLoading(false); // Stop loading
            }
        };

        fetchTrip();
    }, [tripId]); // Dependencies: Re-fetch when tripId changes

    // Go back handler
    const goBack = () => {
        navigate(-1); // Navigate back to the previous page
    };

    // Handle updating the trip start and end dates
    const handleDateUpdate = async (startDate, endDate) => {
        if (!tripId) return; // Ensure tripId exists before making the request

        try {
            const response = await axios.put(
                `http://localhost:8081/trips/${tripId}/dates`,
                { startDate, endDate }
            );
            console.log("Dates updated successfully:", response.data);

            setTrip(response.data); // Update trip with the new data
        } catch (err) {
            console.error("Error updating trip dates:", err);
        }
    };

// Handle clearing trip start and end dates
    const handleDateDelete = async () => {
        if (!tripId) return; // Ensure tripId exists before making the request

        try {
            await axios.delete(`http://localhost:8081/trips/${tripId}/dates`, {
                params: { clearStartDate: true, clearEndDate: true },
            });
            console.log("Dates cleared successfully.");

            setTrip({ ...trip, startDate: null, endDate: null }); // Clear dates in the local trip state
        } catch (err) {
            console.error("Error clearing trip dates:", err);
        }
    };

    // Handle deleting the trip
    const handleTripDelete = async () => {
        if (!tripId) return; // Ensure tripId exists before making the request

        try {
            await axios.delete(`http://localhost:8081/trips/${tripId}`);
            console.log("Trip deleted successfully.");

            navigate(-1); // Navigate back after deletion
        } catch (err) {
            console.error("Error deleting trip:", err);
        }
    };

    // Function to fetch a park's details by its parkCode
    const fetchParkByParkCode = async (parkCode) => {
        try {
            setError(""); // Clear any existing error
            console.log("Fetching park with parkCode:", parkCode);
            const response = await fetch(
                `http://localhost:8081/lookup?parkCode=${encodeURIComponent(parkCode)}`
            );

            if (!response.ok) {
                throw new Error("Failed to fetch park details. Please try again.");
            }

            const data = await response.json();
            return data; // Return the fetched park data
        } catch (err) {
            console.error("Error fetching park details:", err);
            setError(err.message);
            return null;
        }
    };


    // Render handlers for different states
    if (loading) return <p>Loading trip details...</p>; // Loading indicator
    if (error) return <p>{error}</p>; // Error message if fetching fails

    // // If no trip is loaded successfully
    // if (!trip) return <p>No trip data available.</p>;

    // If trip data is available
    return (
        <div className={style.tripDetails}>
            {/* Back button */}
            <button className={style.tripBtn} onClick={goBack}>
                Back to Trips
            </button>

            {/* Trip Details */}
            <h1>{trip.parkName}</h1>
            <p className={style.description}>{trip.parkDescription || "No description available"}</p>

            <h2>Trip Details</h2>
            <p>
                <strong>Start Date:</strong> {trip.startDate || "Not set"}
            </p>
            <p>
                <strong>End Date:</strong> {trip.endDate || "Not set"}
            </p>

            {/* Buttons for managing dates */}
            <div className={style.dateBtns}>
                <button
                    className={style.tripBtn}
                    onClick={() => {
                        const startDate = prompt("Enter Start Date (YYYY-MM-DD):", trip.startDate || "");
                        const endDate = prompt("Enter End Date (YYYY-MM-DD):", trip.endDate || "");
                        if (startDate || endDate) handleDateUpdate(startDate, endDate);
                    }}
                >
                    Add/Edit Dates
                </button>
                <button className={style.tripBtn} onClick={handleDateDelete}>
                    Clear Dates
                </button>
            </div>

            {/* Hiking Trails Section */}
            <h3>Hiking Trails</h3>
            <p>
                <strong>Trail:</strong> {trip?.hikingTrail || "No hiking trail information available."}
                <br />
                <strong>Description:</strong> {trip?.trailDescription || "No trail description available."}
            </p>

            {/* Campground Section */}
            <h3>Campground</h3>
            <p>
                <strong>Campground:</strong> {trip?.campground || "No campground information available."}
                <br />
                <strong>Description:</strong> {trip?.campgroundDescription || "No campground description available."}
            </p>

            <div className={style.additionalBtns}>
                <button className={style.tripBtn}>
                    <Link
                        to={`/park/hiking/${trip.parkCode}`}// Assuming this route leads to HikingTrails
                        state={{ fromTripDetails: true, tripId: trip.tripId, parkName: trip.parkName }} // Pass state
                    >
                    See Hiking Trails in {trip.parkName}
                    </Link>
                </button>
                <button className={style.tripBtn}>
                    <Link
                        to={`/park/campgrounds/${trip.parkCode}`}
                        state={{ fromTripDetails: true, tripId: trip.tripId, parkName: trip.parkName }}// Pass state
                    >
                        Find Campgrounds in {trip.parkName}
                    </Link>
                </button>
                <button
                className={style.tripBtn}
                onClick={async () => {
                if (!trip) return; // Ensure trip data exists
                try {
                    const parkData = await fetchParkByParkCode(trip.parkCode); // Fetch park data
                    if (parkData) {
                        navigate(`/parklist/${trip.parkCode}`, { state: { park: parkData } }); // Pass park data
                    }
                } catch (err) {
                    console.error("Failed to fetch park data or navigate:", err);
                }
            }}
                >
                View Park Details
            </button>

        </div>

            {/* Delete Trip Button */}
            <button className={style.deleteTripBtn} onClick={handleTripDelete}>
                Delete Trip
            </button>
        </div>
    )
        ;
}
