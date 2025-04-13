import React, { useState, useEffect } from "react";
import axios from "axios";
import style from "../Trips.module.css";


const TrailTripButton = ({userId, tripId, title, shortDescription}) => {
    const [isSaved, setIsSaved] = useState(false);

    const storedUserId = localStorage.getItem("userId");
    if (userId === null) userId = storedUserId;

    //check if the trail is favorited
    useEffect(() => {
        const checkIfSaved = async () => {
            if (!userId || !tripId) {
                throw new Error("Missing User ID or Trip ID. Please log in again.");
            }
            try {
                const response = await axios.get(`http://localhost:8081/trips/${tripId}`);
                const trip = response.data;
                if (trip?.hikingTrail === title) {
                    setIsSaved(true);
                }
            } catch (err) {
                console.error("Error fetching trip data:", err);
            }
        };
        checkIfSaved();
    }, [tripId, title]);

    const handleAddTrailToTrip = async () => {
        try {
            console.log({ userId, tripId, title, shortDescription });
            await axios.put(`http://localhost:8081/trips/${tripId}/hiking-trails`, {
                hikingTrail: title,
                trailDescription: shortDescription,
            });
            setIsSaved(true);
        } catch (err) {
            console.error("Error adding to trip:", err);
        }
    };

    const handleRemoveTrailFromTrip = async () => {
        try {
            await axios.delete(`http://localhost:8081/trips/${tripId}/hiking-trails`);
            setIsSaved(false);
        } catch (err) {
            console.error("Error removing from trip:", err);
        }
    };

    return (
        <button
            onClick={isSaved ? handleRemoveTrailFromTrip : handleAddTrailToTrip}
            className={`${style.tripBtn} ${isSaved ? style.saved : ""}`}
            >
            {isSaved ? "Saved" : "Add to Trip"}
        </button>
    );
};

export default TrailTripButton;