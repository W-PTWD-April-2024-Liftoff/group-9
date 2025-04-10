import React, { useState, useEffect } from "react";
import axios from "axios";
import style from "../Trips.module.css";


const CampTripButton = ({userId, parkCode, name, description}) => {
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
                if (trips && trips.some((trip) => trip.campground === name)) {
                    setIsSaved(true);
                }
            } catch (err) {
                console.error("Error fetching trips:", err);
            }
        };
        checkIfSaved();
    }, [parkCode]);

    const handleAddCampgroundToTrip = async () => {
        try{
            console.log({userId, parkCode, name, description});
            await axios.post(`http://localhost:8081/trips/${parkCode}`, null, {
                params: {
                    name,
                    description
                },
            });
            setIsSaved(true);
        } catch (err) {
            console.error("Error adding to trips:", err);
        }
    };

    const handleRemoveCampgroundFromTrip = async () => {
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
            onClick={isSaved ? handleRemoveCampgroundFromTrip : handleAddCampgroundToTrip}
            className={`camp-trip-button ${isSaved ? "saved" : ""}`}
            id={style.parkBtn}
        >
            {isSaved ? "Saved" : "Add to Trip"}
        </button>
    );
};

export default CampTripButton;