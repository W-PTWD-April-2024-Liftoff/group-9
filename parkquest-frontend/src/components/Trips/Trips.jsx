import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import style from "./Trips.module.css";
import axios from "axios";

const Trips = ({userId}) => {
    const [trips, setTrips] = useState([]);
    const [error, setError] = useState(null);
    const [hoveredTrip, setHoveredTrip] = useState(null);
    const navigate = useNavigate();
    const storedUserId = localStorage.getItem("userId");


    // Fetch trips for the user when the component mounts or `userId` changes
    useEffect(() => {
        const fetchTrips = async () => {
            try {
                // Use stored or prop `userId`
                const id = userId || storedUserId;

                if (!id) {
                    throw new Error("User ID is missing. Please log in again.");
                }

                const response = await fetch(`http://localhost:8081/trips/user/${id}`);

                if (!response.ok) {
                    throw new Error("Failed to fetch trips. Please try again.");
                }

                const data = await response.json();
                setTrips(data);
            } catch (err) {
                setError(err.message);
            }
        };

        fetchTrips();
    }, [userId, storedUserId]);

    // Function to remove a trip
    const removeTrip = async (tripId) => {
        try {
            if (!tripId) {
                throw new Error("Trip ID is missing.");
            }

            await axios.delete(`http://localhost:8081/trips/${tripId}`);

            // Update state after removing the trip
            const updatedTrips = trips.filter((trip) => trip.tripId !== tripId);
            setTrips(updatedTrips);
        } catch (err) {
            setError(err.message);
        }
    };


    // Navigate to TripDetails for editing
    const handleEdit = (trip) => {
        navigate(`/trips/${trip.tripId}`); // Navigate with tripId
    };



    // Function to fetch a trip by parkCode
    const fetchTripByParkCode = async (parkCode) => {
        try {
            setError(""); // Clear any existing error

            const response = await fetch(
                `http://localhost:8081/lookup?parkCode=${encodeURIComponent(parkCode)}`
            );
            console.log("Fetching trip with parkCode:", parkCode);

            if (!response.ok) {
                throw new Error("Failed to fetch trip details. Please try again.");
                console.error("Error fetching trip:", err);

            }

            const data = await response.json();
            setHoveredTrip(data); // Update the `hoveredPark` state with fetched data
        } catch (err) {
            setError(err.message);
        }
    };

  return (
      <div className={style.trips}>
          <button className={style.outlineButton}>
              <Link to="/favorites">My Favorite Parks</Link>
          </button>
          <button className={style.outlineButton}>
              <Link to="/parklist">Go to Parks List</Link>
          </button>
          <h1>My Trips</h1>
          {error && <p className={style.error}>{error}</p>}
          {trips.length === 0 ? (
              <p>No trips yet!</p>
          ) : (
              <ul className={style.tripList}>
                  {trips.map((trip) => (
                      <li key={trip.tripId} className={style.tripItem}>
                          {/* Park Name (Clickable to navigate to TripDetails) */}
                          <h3
                              className={style.tripName}
                              onClick={() => handleEdit(trip)}
                              role="button"
                              tabIndex={0} // Accessibility for keyboard navigation
                              onKeyDown={(e) => {
                                  if (e.key === "Enter") handleEdit(trip); // Allows "Enter" key to trigger click
                              }}
                          >
                              {trip.parkName}
                          </h3>
                          {/* Park Description */}
                          <p className={style.description}>{trip.parkDescription || "No description available"}</p>
                          {/* Buttons for Delete and Edit */}
                          <div className={style.buttonGroup}>
                              <button
                                  onClick={() => handleEdit(trip)}
                                  className={`${style.tripBtn} ${style.editBtn}`}
                              >
                                  Edit
                              </button>
                              <button
                                  onClick={() => removeTrip(trip.tripId)}
                                  className={`${style.tripBtn} ${style.deleteBtn}`}
                              >
                                  Delete
                              </button>
                          </div>
                      </li>
                  ))}
              </ul>
          )}
      </div>
  );
// <div >
    //     <button className={style.outlineButton}>
    //         <Link to="/favorites">My Favorite Parks</Link>
    //     </button>
    //     <button className={style.outlineButton}>
    //         <Link to="/parklist">Go to Parks List</Link>
    //     </button>
    //   <h1>Trips</h1>
    //     {/* Display error if any */}
    //     {error && <p className={style.error}>{error}</p>}
    //
    //     {trips.length === 0 ? <p>No trips yet!</p> : (
    //         <ul>
    //             {trips.map((trip) => (
    //                 <li key={trip.id}>
    //                     {/* Title */}
    //                     <h3
    //                         onMouseEnter={() => fetchParkByParkCode(trip.parkCode)} // Trigger fetching park data on hover
    //                     >
    //                         <Link
    //                             to={`/tripdetails/${trip.parkCode}`} // Pass the parkCode in the link
    //                             state={{
    //                                 trip: hoveredTrip || {}, // Pass hover data if available
    //                             }}
    //                             userId={storedUserId}
    //                         >
    //                             `Trip Plan to {trip.fullName}`
    //                         </Link>
    //                     </h3>
    //                     <p>{trip.parkDescription}</p>
    //                     <br />
    //                     <button onClick={() => removeTrip(trip.parkCode)} >Remove</button>
    //                 </li>
    //             ))}
    //         </ul>
    //     )}
    // </div>
  // );
};

export default Trips;