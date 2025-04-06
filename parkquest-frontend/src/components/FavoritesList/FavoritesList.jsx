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
                console.log("Favorites fetched:", response.data); // Debug
                setFavorites(response.data);
                localStorage.setItem("favorites", JSON.stringify(response.data));
            } catch (err) {
                console.error("Error fetching favorites:", err);
            }
        };
        fetchFavorites();
    }, [userId]);

    const removeFavorite = async (parkCode) => {
        try {
            await axios.delete("http://localhost:8081/api/favorites", {
                params: { userId, parkCode },
            });

            const response = await axios.get(`http://localhost:8081/api/favorites/${userId}`);
            setFavorites(response.data);
            localStorage.setItem("favorites", JSON.stringify(response.data));
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
                    {favorites.map((fav) => (
                        <li key={fav.parkCode}>
                            <h3>{fav.parkCode}</h3> {/* Display parkCode or add a lookup */}
                            <button onClick={() => removeFavorite(fav.parkCode)}>Remove</button>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};


export default FavoritesList;