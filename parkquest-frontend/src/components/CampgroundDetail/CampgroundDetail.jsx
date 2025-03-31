import { useLocation, useNavigate } from "react-router-dom";
import style from "./CampgroundDetail.module.css";

export default function CampgroundDetail() {
  const navigate = useNavigate();
  const location = useLocation();
  const campground = location.state?.campground; // Get passed data
  const parkName = location.state?.parkName;

  if (!campground) {
    return <p style={{ color: "red" }}>Campground details are missing.</p>;
  }

  return (
    <div className={style.campground}>
      <button className={style.campgroundBtn} onClick={() => navigate(-1)}>
        Back to Campgrounds List
      </button>

      <h1>Campground at {parkName}: {campground.name}</h1>
      {campground.images?.length > 0 && (
        <img
          src={campground.images[0].url}
          alt={campground.images[0].altText || "Campground Image"}
        />
      )}
      <p className={style.description}>{campground.description}</p>

      <div className={style.extra}>
        <a href={campground.url} className={style.url} target="_blank" rel="noopener noreferrer">
          More Info
        </a>

        <h3>Amenities</h3>
        <ul className={style.amenitiesList}>
          <li><strong>Camp Store:</strong> {campground.amenities?.campStore || "N/A"}</li>
          <li><strong>Dump Station:</strong> {campground.amenities?.dumpStation || "N/A"}</li>
          <li><strong>Potable Water:</strong> {campground.amenities?.potableWater?.join(", ") || "N/A"}</li>
          <li><strong>Showers:</strong> {campground.amenities?.showers?.join(", ") || "N/A"}</li>
          <li><strong>Toilets:</strong> {campground.amenities?.toilets?.join(", ") || "N/A"}</li>
        </ul>

        <p className={style.reservation}><strong>Reservation Info: </strong>{campground.reservationInfo || "N/A"}</p>
        
        <a href={campground.reservationUrl} className={style.url} target="_blank" rel="noopener noreferrer">
          Reservation link
        </a>
      </div>
      
      
    </div>
  );
}
