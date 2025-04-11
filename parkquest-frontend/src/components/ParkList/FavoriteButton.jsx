import React, { useState, useEffect } from "react";
import axios from "axios";
import style from "../ParkDetail/ParkDetail.module.css"

const FavoriteButton = ({ userId, parkCode, fullName, description }) => {
    const [isFavorited, setIsFavorited] = useState(false);

    const storedUserId = localStorage.getItem("userId");
    if (userId === null) userId = storedUserId;

    // Check if the current park is favorited on component mount
    useEffect(() => {
        const checkIfFavorited = async () => {
                if (!userId) {
                    throw new Error("User ID is missing. Please log in again.");
                }

            try {
                const response = await axios.get(`http://localhost:8081/favorites/${userId}`);
                const favorites = response.data;

                if (favorites && favorites.some((fav) => fav.parkCode === parkCode)) {
                    setIsFavorited(true);
                }
            } catch (err) {
                console.error("Error fetching favorites:", err);
            }
        };

        checkIfFavorited();
    }, [parkCode]);

    const handleAddToFavorites = async () => {
        try {
            console.log({ userId, parkCode, fullName, description });
            await axios.post("http://localhost:8081/favorites", null, {
                params: {
                    userId,
                    parkCode,
                    fullName,
                    description,
                    noteToSelf: "" //Initial note to self can be empty
                },
            });
            setIsFavorited(true);
        } catch (err) {
            console.error("Error adding to favorites:", err);
        }
    };

    //remove the park from favorites
    const handleRemoveFromFavorites = async () => {
        try {
            await axios.delete("http://localhost:8081/favorites", {
                params: { userId, parkCode }, // Use parkCode
            });
            setIsFavorited(false);
        } catch (err) {
            console.error("Error removing from favorites:", err);
        }
    };


    return (
        <button
            onClick={isFavorited ? handleRemoveFromFavorites : handleAddToFavorites}
            className={`favorite-button ${isFavorited ? "favorited" : ""}`} id={style.parkBtn}
        >
            {isFavorited ? "Favorited" : "Add to Favorites"}
        </button>
    );
};

export default FavoriteButton;