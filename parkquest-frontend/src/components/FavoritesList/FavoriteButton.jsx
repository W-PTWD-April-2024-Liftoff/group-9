import React, { useState, useEffect } from "react";
import axios from "axios";

const FavoriteButton = ({ userId, parkCode }) => {
    const [isFavorited, setIsFavorited] = useState(false);

    // Check if the current park is favorited on component mount
    useEffect(() => {
        const checkIfFavorited = async () => {
            if (!userId) {
                console.error("Missing userId");
                return;
            }
            try {
                const response = await axios.get(`http://localhost:8081/api/favorites/${userId}`);
                const favorites = response.data;

                if (favorites && favorites.some((fav) => fav.parkCode === parkCode)) {
                    setIsFavorited(true);
                }
            } catch (err) {
                console.error("Error fetching favorites:", err);
            }
        };

        checkIfFavorited();
    }, [userId, parkCode]);

    const handleAddToFavorites = async () => {
        try {
            await axios.post(`http://localhost:8081/api/favorites`, null, {
                params: { userId, parkCode }, // Use parkCode
            });
            setIsFavorited(true);
        } catch (err) {
            console.error("Error adding to favorites:", err);
        }
    };

    const handleRemoveFromFavorites = async () => {
        try {
            await axios.delete("http://localhost:8081/api/favorites", {
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
            className={`favorite-button ${isFavorited ? "favorited" : ""}`}
        >
            {isFavorited ? "Favorited" : "Add to Favorites"}
        </button>
    );
};

export default FavoriteButton;