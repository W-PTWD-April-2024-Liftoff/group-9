import { useEffect, useState } from "react";
import { useParams, Link, useLocation } from "react-router-dom";
import style from "./CampgroundsList.module.css";
import CampTripButton from "../Trips/Buttons/CampTripButton.jsx";

const API_KEY = import.meta.env.VITE_PARKS_API_KEY;
const API_BASE_URL = "https://developer.nps.gov/api/v1/campgrounds";

export default function CampgroundsList() {
  const { id: parkCode } = useParams();
  const [campgrounds, setCampgrounds] = useState([]);
  const location = useLocation();
  const parkName = location.state?.parkName;
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { fromTripDetails = false, tripId = null } = location.state || {}; // Default values


    useEffect(() => {
    if (!parkCode) return;

    fetch(`${API_BASE_URL}?parkCode=${parkCode}&api_key=${API_KEY}`)
      .then((response) => response.json())
      .then((data) => {
        console.log("Fetched campgrounds:", data);
        if (data.data.length > 0) {
          setCampgrounds(data.data);
        } else {
          setError("No campgrounds found for this park.");
        }
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error fetching campgrounds:", error);
        setError("Failed to load campgrounds.");
        setLoading(false);
      });
  }, [parkCode]);

  if (loading) return <p>Loading campgrounds...</p>;
  if (error) return <p style={{ color: "red", textAlign: "center", marginTop: "20px" }}>{error}</p>;

  return (
    <div className={style.campgroundsList}>
      <h1>Campgrounds for {parkName}</h1>
      <ul>
        {campgrounds.map((campground) => (
          <li key={campground.id}>
            <h3>
              <Link 
                to={`/campgrounds/${campground.id}`} 
                state={{ parkCode, parkName, campground }}>
                  {campground.name}
              </Link>
            </h3>
            <p>{campground.description}</p>
              {/* Conditionally render TrailTripButton */}
              {fromTripDetails && tripId && (
                  <CampTripButton
                      tripId={tripId}
                      name={campground.name}
                      description={campground.description}
                      userId={localStorage.getItem("userId")}
                  />
                  )}
          </li>
        ))}
      </ul>
    </div>
  );
}
