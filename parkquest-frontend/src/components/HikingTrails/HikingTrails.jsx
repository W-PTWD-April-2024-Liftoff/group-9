import style from "./HikingTrails.module.css";
import { useParams, useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import TrailTripButton from "../Trips/Buttons/TrailTripButton.jsx";

const API_KEY = import.meta.env.VITE_PARKS_API_KEY;
const API_BASE_URL = "https://developer.nps.gov/api/v1/thingstodo";

const HikingTrails = () => {
  const { id: parkCode } = useParams();
  const [trails, setTrails] = useState([]);
  const location = useLocation();
  const parkName = location.state?.parkName;
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { fromTripDetails = false, tripId = null } = useLocation().state || {}; //default values

  useEffect(() => {
      if (!parkCode) return;
  
      fetch(`${API_BASE_URL}?parkCode=${parkCode}&api_key=${API_KEY}`)
        .then((response) => response.json())
        .then((data) => {
          console.log("Fetched things to do:", data);

          const hikingTrails = data.data.filter((item) =>
            item.activities.some((activity) => activity.name === "Hiking")
          );

          console.log("Fetched hiking trails:", hikingTrails);

          if (hikingTrails.length > 0) {
            setTrails(hikingTrails);
          } else {
            setError("No hiking trails found for this park.");
          }

          setLoading(false);
        })
        .catch((error) => {
          console.error("Error fetching hiking trails:", error);
          setError("Failed to load hiking trails.");
          setLoading(false);
        });
    }, [parkCode]);
  
  if (loading) return <p>Loading hiking trails...</p>;
  if (error) return <p style={{ color: "red", textAlign: "center", marginTop: "20px" }}>{error}</p>;

  const stripHtmlTags = (html) => {
    const tempDiv = document.createElement('div');
    tempDiv.innerHTML = html;
    
    return tempDiv.textContent || tempDiv.innerText || '';
  };


    return (
    <div className={style.trailsList}>
      <h1>Hiking Trails for {parkName}</h1>
      
      <ul>
        {trails.map((trail) => (
          <li key={trail.id}>
            <h2>{trail.title}</h2>
            <p>{trail.shortDescription}</p>

            {trail.activityDescription && (
              <p><strong>Difficulty Level:</strong> {stripHtmlTags(trail.activityDescription)}</p>
            )}

            {trail.duration && (
              <p><strong>Duration:</strong> {stripHtmlTags(trail.duration)}</p>
            )}

            {trail.durationDescription && (
              <p><strong>Description: </strong>{stripHtmlTags(trail.durationDescription)}</p>
            )}

            {trail.url && (
              <a href={trail.url} target="_blank" rel="noopener noreferrer">
                Learn more
              </a>
            )}
              {/* Conditionally render TrailTripButton */}
              {fromTripDetails && tripId && (
                  <TrailTripButton
                      tripId={tripId}
                      title={trail.title}
                      shortDescription={trail.shortDescription}
                      userId={localStorage.getItem("userId")}
                  />
              )}
          </li>
        ))}
      </ul>
    </div>
  )
}

export default HikingTrails;