import React, { useState, useEffect } from "react";
import axios from "axios";

const FavoriteButton = ({ userId, parkId }) => {
    const [isFavorited, setIsFavorited] = useState(false);

    // Update `isFavorited` on component mount
    useEffect(() => {
        const existingFavorites = JSON.parse(localStorage.getItem("favorites")) || [];
        const favoriteExists = existingFavorites.some((fav) => fav.parkId === parkId);
        setIsFavorited(favoriteExists);
    }, [parkId]);

    const handleAddToFavorites = async () => {
        try {
            await axios.post(`http://localhost:8081/api/favorites`, {
                userId,
                parkId,
            });

            // Sync with localStorage
            const updatedFavoritesResponse = await axios.get(
                `http://localhost:8081/api/favorites/${userId}`
            );
            const updatedFavorites = updatedFavoritesResponse.data;

            localStorage.setItem("favorites", JSON.stringify(updatedFavorites));
            setIsFavorited(true); // Update state
        } catch (err) {
            console.error("Error adding to favorites:", err);
        }
    };

    return (
        <button
            onClick={handleAddToFavorites}
            className={`favorite-button ${isFavorited ? "favorited" : ""}`}
            disabled={isFavorited} // Disable button if already favorited
        >
            {isFavorited ? "Favorited" : "Add to Favorites"}
        </button>
    );
};

export default FavoriteButton;