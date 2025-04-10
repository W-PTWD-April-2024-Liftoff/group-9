import {useLocation, useNavigate, Link} from "react-router-dom";
import style from "./TripDetails.module.css";
import {useRef} from "react";
import axios from "axios";
import TrailTripButton from "./Buttons/TrailTripButton.jsx";
import {useState} from "react";
import CampTripButton from "./Buttons/CampTripButton.jsx";

export default function TripDetails() {
    const location = useLocation();
    const navigate = useNavigate();
    const [trip, setTrip] = useState(location.state?.trip); // Local copy of trip data

    if (!trip) {
        return <p>No trip data available.</p>; // Handle case where data is missing
    }

    const goBack = () => {
        navigate(-1); // Navigate back to the previous page
    };

    // Handle start and end date updates
    const handleDateUpdate = async (startDate, endDate) => {
        try {
            const response = await axios.put(
                `http://localhost:8081/trips/${trip.parkCode}/dates`,
                {startDate, endDate}
            );
            setTrip(response.data); // Update local state with new trip data
        } catch (err) {
            console.error("Error updating dates:", err);
        }
    };

    // Delete dates
    const handleDateDelete = async () => {
        try {
            await axios.delete(`http://localhost:8081/trips/${trip.parkCode}/dates`, {
                params: {clearStartDate: true, clearEndDate: true},
            });
            setTrip({...trip, startDate: null, endDate: null}); // Clear local state
        } catch (err) {
            console.error("Error deleting dates:", err);
        }
    };

    // Delete trip
    const handleTripDelete = async () => {
        try {
            await axios.delete(`http://localhost:8081/trips/${trip.parkCode}`);
            navigate(-1); // Go back after deletion
        } catch (err) {
            console.error("Error deleting trip:", err);
        }
    };

    return (
        <div className={style.tripDetails}>
            <button className={style.tripBtn} onClick={goBack}>
                Back to Trips
            </button>

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

            <h3>Hiking Trails</h3>
            {trip.hikingTrail ? (
                <p>
                    <strong>Trail:</strong> {trip.hikingTrail} <br/>
                    <strong>Description:</strong> {trip.trailDescription || "No description"}
                </p>
            ) : (
                <p>No hiking trail information available.</p>
            )}
            {/* Add TrailTripButton */}
            <TrailTripButton
                userId={localStorage.getItem("userId")}
                parkCode={trip.parkCode}
                title={trip.hikingTrail || ""} // Pass existing trail info
                shortDescription={trip.trailDescription || ""}
            />


            <h3>Campground</h3>
            {trip.campground ? (
                <p>
                    <strong>Campground:</strong> {trip.campground} <br/>
                    <strong>Description:</strong> {trip.campgroundDescription || "No description"}
                </p>
            ) : (
                <p>No campground information available.</p>
            )}
            {/* Add CampTripButton */}
            <CampTripButton
                userId={localStorage.getItem("userId")}
                parkCode={trip.parkCode}
                name={trip.campground || ""} // Pass existing campground info
                description={trip.campgroundDescription || ""}
            />

            {/* Delete Trip Button */}
            <button className={style.tripBtn} onClick={handleTripDelete}>
                Delete Trip
            </button>

            <p className={style.tripUrl}>
                <Link
                    to={`/park/details/${trip.parkCode}`}
                    state={{parkCode: trip.parkCode}}
                >
                    Visit {trip.parkName} Details Page
                </Link>
            </p>

            <button className={style.tripBtn}>
                <Link
                    to={`/park/hiking/${trip.parkCode}`}
                    state={{parkName: trip.parkName}}
                >
                    See Hiking Trails in {trip.parkName}
                </Link>
            </button>

            <button className={style.tripBtn}>
                <Link
                    to={`/park/campgrounds/${trip.parkCode}`}
                    state={{parkName: trip.parkName}}
                >
                    Find Campgrounds in {trip.parkName}
                </Link>
            </button>
        </div>
    )
        ;
}
