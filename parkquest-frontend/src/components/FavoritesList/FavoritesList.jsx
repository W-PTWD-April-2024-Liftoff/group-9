import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import style from "./FavoritesList.module.css";

const FavoritesList = ({ userId }) => {
    const [favorites, setFavorites] = useState([]);

    // Fetch favorites from backend on component mount
    useEffect(() => {
        const fetchFavorites = async () => {
            try {
                const response = await axios.get(`http://localhost:8081/api/favorites/${userId}`);
                setFavorites(response.data);
                localStorage.setItem("favorites", JSON.stringify(response.data));
            } catch (err) {
                console.error("Error fetching favorites:", err);
            }
        };
        fetchFavorites();
    }, [userId]);

    const removeFavorite = async (parkId) => {
        try {
            await axios.delete("http://localhost:8081/api/favorites", {
                params: { userId, parkId },
            });

            const updatedFavorites = favorites.filter((park) => park.parkId !== parkId);
            setFavorites(updatedFavorites);
            localStorage.setItem("favorites", JSON.stringify(updatedFavorites));
        } catch (err) {
            console.error("Error removing favorite:", err);
        }
    };

    return (
        <div className={style.favoriteList}>
            <button>
                <Link to="/parklist">Go to Parks List</Link>
            </button>
            <h1>My Favorite Parks</h1>
            {favorites.length === 0 ? (
                <p>No favorites yet!</p>
            ) : (
                <ul>
                    {favorites.map((park) => (
                        <li key={park.parkId}>
                            <h3>{park.fullName}</h3>
                            <button onClick={() => removeFavorite(park.parkId)}>Remove</button>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default FavoritesList;