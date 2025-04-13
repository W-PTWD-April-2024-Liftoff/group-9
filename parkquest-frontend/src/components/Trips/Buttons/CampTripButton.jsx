import React, { useState, useEffect } from "react";
import axios from "axios";
import style from "../Trips.module.css";


const CampTripButton = ({userId, tripId, name, description}) => {
    const [isSaved, setIsSaved] = useState(false);

    const storedUserId = localStorage.getItem("userId");
    if (userId === null) userId = storedUserId;

    //check if the trail is saved
    useEffect(() => {
        const checkIfSaved = async () => {
            if (!userId || !tripId) {
                tconsole.error("Missing User ID or Trip ID. Please log in again.");
                return;
            }
            try {
                const response = await axios.get(`http://localhost:8081/trips/${tripId}`);
                const trip = response.data;
                if (trip?.campground === name) {
                    setIsSaved(true);
                }
            } catch (err) {
                console.error("Error fetching trip data:", err);
            }
        };
        checkIfSaved();
    }, [tripId, name, userId]);

    const handleAddCampgroundToTrip = async () => {
        try {
            console.log({ userId, tripId, name, description });
            await axios.put(`http://localhost:8081/trips/${tripId}/campgrounds`, {
                campground: name,
                campgroundDescription: description,
            });
            setIsSaved(true);
        } catch (err) {
            console.error("Error adding campground to trip:", err);
        }
    };

    // Remove a campground from the trip
    const handleRemoveCampgroundFromTrip = async () => {
        try {
            await axios.delete(`http://localhost:8081/trips/${tripId}/campgrounds`, {
                data: { campground: name }, // Pass the campground name to identify which to remove
            });
            setIsSaved(false);
        } catch (err) {
            console.error("Error removing campground from trip:", err);
        }
    };

    return (
        <button
            onClick={isSaved ? handleRemoveCampgroundFromTrip : handleAddCampgroundToTrip}
            className={`${style.tripBtn} ${isSaved ? style.saved : ""}`}
        >
            {isSaved ? "Saved" : "Add to Trip"}
        </button>
    );
};

export default CampTripButton;