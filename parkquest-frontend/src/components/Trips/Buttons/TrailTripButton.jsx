import React, { useState, useEffect } from "react";
import axios from "axios";
import style from "../Trips.module.css";


const TrailTripButton = ({userId, parkCode, title, shortDescription}) => {
    const [isSaved, setIsSaved] = useState(false);

    const storedUserId = localStorage.getItem("userId");
    if (userId === null) userId = storedUserId;

    //check if the trail is favorited
    useEffect(() => {
        const checkIfSaved = async () => {
            if (!userId) {
                throw new Error("User ID is missing. Please log in again.");
            }
            try {
                const response = await axios.get(`http://localhost:8081/trips/${parkCode}`);
                const trails = response.data;
                if (trips && trips.some((trip) => trip.hikingTrail === title)) {
                    setIsSaved(true);
                }
            } catch (err) {
                console.error("Error fetching trips:", err);
            }
        };
        checkIfSaved();
    }, [parkCode]);

    const handleAddTrailToTrip = async () => {
        try{
            console.log({userId, parkCode, title, shortDescription});
            await axios.post(`http://localhost:8081/trips/${parkCode}`, null, {
                params: {
                    title,
                    shortDescription
                },
            });
            setIsSaved(true);
        } catch (err) {
            console.error("Error adding to trips:", err);
        }
    };

    const handleRemoveTrailFromTrip = async () => {
        try{
            await axios.delete(`http://localhost:8081/trips/${parkCode}`, {
                params: {userId, parkCode},
            });
            setIsSaved(false);
        } catch (err) {
            console.error("Error removing from trips:", err);
        }
    };

    return (
        <button
            onClick={isSaved ? handleRemoveTrailFromTrip : handleAddTrailToTrip}
            className={`trail-trip-button ${isSaved ? "saved" : ""}`}
            id={style.parkBtn}
            >
            {isSaved ? "Saved" : "Add to Trip"}
        </button>
    );
};

export default TrailTripButton;