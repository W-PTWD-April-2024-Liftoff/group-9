import {useState} from "react";
import {Link} from "react-router-dom";
import style from "./ParkList.module.css";
import FavoritesList from "../FavoritesList/FavoritesList";
import FavoriteButton from "../FavoritesList/FavoriteButton.jsx";
import {useUserContext} from "../UserContext.jsx";

const ParksList = () => {
    const [selectedState, setSelectedState] = useState("");
    const [searchQuery, setSearchQuery] = useState("");
    const [parks, setParks] = useState([]);
    const [error, setError] = useState("");
    const [favorites, setFavorites] = useState(JSON.parse(localStorage.getItem("favorites")) || []);
    const [hasSearched, setHasSearched] = useState(false);
    const userId = useUserContext();

    if (!userId) {
        console.error("User ID is not defined. Ensure you are logged in.");
        return <p>Error: User ID is missing. Please log in.</p>;
    }

    const states = {
        "Alabama": "AL", "Alaska": "AK", "Arizona": "AZ", "Arkansas": "AR", "California": "CA",
        "Colorado": "CO", "Connecticut": "CT", "Delaware": "DE", "Florida": "FL", "Georgia": "GA",
        "Hawaii": "HI", "Idaho": "ID", "Illinois": "IL", "Indiana": "IN", "Iowa": "IA",
        "Kansas": "KS", "Kentucky": "KY", "Louisiana": "LA", "Maine": "ME", "Maryland": "MD",
        "Massachusetts": "MA", "Michigan": "MI", "Minnesota": "MN", "Mississippi": "MS", "Missouri": "MO",
        "Montana": "MT", "Nebraska": "NE", "Nevada": "NV", "New Hampshire": "NH", "New Jersey": "NJ",
        "New Mexico": "NM", "New York": "NY", "North Carolina": "NC", "North Dakota": "ND",
        "Ohio": "OH", "Oklahoma": "OK", "Oregon": "OR", "Pennsylvania": "PA", "Rhode Island": "RI",
        "South Carolina": "SC", "South Dakota": "SD", "Tennessee": "TN", "Texas": "TX", "Utah": "UT",
        "Vermont": "VT", "Virginia": "VA", "Washington": "WA", "West Virginia": "WV",
        "Wisconsin": "WI", "Wyoming": "WY"
    };

    // Fetch parks by state
    const fetchParksByState = async () => {
        if (!selectedState) {
            setError("Please select a state.");
            return;
        }

        setError("");

        try {
            const stateCode = states[selectedState]; // Convert full name to abbreviation

            console.log("Selected State Code:", stateCode);

            const response = await fetch(
                `http://localhost:8081/parks/searches?stateCode=${stateCode}`
            );

            if (!response.ok) {
                throw new Error("Failed to fetch parks. Please try again.");
            }

            const data = await response.json();
            setParks(data);
            setHasSearched(true); // Mark that a search has been performed
            console.log("API Response:", data);
        } catch (err) {
            setError(err.message);
        }
    };

    // Fetch parks by name
    const fetchParksByName = async () => {
        if (!searchQuery.trim()) {
            setError("please enter a park name.");
            return;
        }

        setError("");

        try {
            const response = await fetch(
                `http://localhost:8081/parks/searches?parkName=${encodeURIComponent(searchQuery)}`
            );

            if (!response.ok) {
                throw new Error("Failed to fetch parks. Please try again.");
            }

            const data = await response.json();
            setParks(data);
            setHasSearched(true); // Mark that a search has been performed
        } catch (err) {
            setError(err.message);
        }
    };

    // Save favorite park
    const saveToFavorites = (park) => {
        const existingFavorites = JSON.parse(localStorage.getItem("favorites")) || [];
        const isAlreadyFavorite = existingFavorites.some((fav) => fav.parkId === park.parkId);

        if (!isAlreadyFavorite) {
            const updatedFavorites = [...existingFavorites, park];
            setFavorites(updatedFavorites); // Update state
            localStorage.setItem("favorites", JSON.stringify(updatedFavorites)); // Persist to localStorage
            console.log(`Park "${park.fullName}" added to favorites!`);
        } else {
            console.log(`Park "${park.fullName}" is already a favorite!`);
        }
    };

    return (
        <div className={style.parksList}>
            <h1>Find National Parks</h1>

            <section>
                <label>Search by State:</label>
                <select value={selectedState} onChange={(e) => setSelectedState(e.target.value)}
                        className={style.searchInput}>
                    <option value="">Select a state</option>
                    {Object.keys(states).map((state) => (
                        <option key={state} value={state}>
                            {state}
                        </option>
                    ))}
                </select>
                <button onClick={fetchParksByState} className={style.searchBtn}>Search</button>
            </section>

            <section>
                <label>Search by Name:</label>
                <input
                    type="text"
                    value={searchQuery}
                    onChange={(e) => setSearchQuery(e.target.value)}
                    placeholder="Enter park name"
                    className={style.searchInput}
                />
                <button onClick={fetchParksByName} className={style.searchBtn}>Search</button>
            </section>

            {error && <p style={{color: "red"}}>{error}</p>}
            {/*<ul className={style.parksList}>*/}
            {/*    {parks.length > 0 ? (*/}
            {/*        parks.map((park) => (*/}
            {/*            <li key={park.parkId} className={style.parkItem}>*/}
            {/*                <h2>{park.fullName}</h2>*/}
            {/*                <p>{park.description}</p>*/}
            {/*                <FavoriteButton userId={userId} parkId={park.parkId} />*/}
            {/*            </li>*/}
            {/*        ))*/}
            {/*    ) : hasSearched ? ( // Display the message only if a search was performed*/}
            {/*        <li>No parks found. Please refine your search.</li>*/}
            {/*    ) : null}*/}
            {/*</ul>*/}
            <ul>
                {parks.map((park) => (
                    <li key={park.id}>
                        <h3>
                            <Link to={`/parklist/${park.parkCode}`} state={{park}}>
                                {park.fullName}
                            </Link>
                        </h3>
                        <p>{park.description}</p>
                        <FavoriteButton userId={userId} parkCode={park.parkCode} />

                    </li>
                ))}
            </ul>
        </div>
    );
};

export default ParksList;
