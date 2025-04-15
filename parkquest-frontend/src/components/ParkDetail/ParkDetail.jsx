import { useLocation, useNavigate, Link } from "react-router-dom";
import style from "./ParkDetail.module.css";
import { useRef } from "react";
import FavoriteButton from "../ParkList/FavoriteButton.jsx";
import ParkReview from "../ParkReview/ParkReview.jsx";
import TripButton from "../Trips/Buttons/TripButton.jsx";
import SubscriptionButton from "../Subscription/SubscriptionButton.jsx";


export default function ParkDetail({ userId }) {
  const location = useLocation();
  const navigate = useNavigate();
  const carouselRef = useRef(null);
  const park = location.state?.park; // Retrieve passed park data

  if (!park) {
    return <p>No park data available.</p>; // Handle case where data is missing
  }

    // Utility function to safely extract and convert `zipcode` to a long
    const extractZipCodeAsLong = (code) => {
        const parsedCode = Number(code); // Convert string to a number
        return isNaN(parsedCode) ? null : Math.trunc(parsedCode); // Return null if invalid, truncate decimals
    };

    // Safely retrieve and convert the zipcode from park data
    const zipcodeAsLong = park?.addresses?.[0]?.postalCode
        ? extractZipCodeAsLong(park.addresses[0].postalCode)
        : "00000"; // Default or fallback value


    const goBack = () => {
    navigate(-1);
  };

  const scrollLeft = () => {
    carouselRef.current.scrollBy({ left: -320, behavior: "smooth" });
  };

  const scrollRight = () => {
    carouselRef.current.scrollBy({ left: 320, behavior: "smooth" });
  };

  return (
    <div className={style.parkDetails}>
      <SubscriptionButton userId={localStorage.getItem("userId")} parkCode={park.parkCode} />

      <FavoriteButton userId={localStorage.getItem("userId")} parkCode={park.parkCode} fullName={park.fullName} description={park.description} />
      
      <button className={style.parkBtn}>
        <Link to="/favorites" className={style.linkBtn}>My Favorite Parks</Link>
      </button>

      <TripButton
          userId={localStorage.getItem("userId")}
          parkCode={park.parkCode}
          fullName={park.fullName}
          description={park.description}
          postalCode={zipcodeAsLong}
      />
      
      <button className={style.parkBtn}>
        <Link to="/trips" className={style.linkBtn}>My Trips</Link>
      </button>
      
      <button className={style.parkBtn} onClick={goBack}>Back to Search</button>

      <h1>{park.fullName}</h1>

      <div className={style.carouselWrapper}>
      <button className={style.arrow} onClick={scrollLeft}>◀</button>
      
      <div className={style.carousel} ref={carouselRef}>
        {park.images.map((img, index) => (
          <figure key={index} className={style.carouselItem}>
            <img
              src={img.url} 
              alt={img.altText || "Park Image"} 
              title={img.title}
              onError={(e) => {
              e.target.closest("figure").style.display = "none";
              }}
            />
            {img.title && <figcaption>{img.title}</figcaption>}
          </figure>
        ))}
      </div>
      
      <button className={style.arrow} onClick={scrollRight}>▶</button>
    </div>

      <p className={style.description}>{park.description}</p>

      <p className={style.parkUrl}>
        <a href={park.url} target="_blank" rel="noopener noreferrer">
          Visit Official Website
        </a>
      </p>
      
      <h3>Activities:</h3>
      <p className={style.activities}> 
        {park.activities && park.activities.length > 0 ? 
        park.activities.map(a => a.name).join(", ") : 
          "No activities available"}
      </p>

      <p className={style.parkUrl}>
        <Link 
          to={`/park/hiking/${park.parkCode}`} 
          state={{ parkName: park.fullName }}
        >
          See hiking trails in {park.fullName}
        </Link>
      </p>
      
      {park.addresses?.length > 0 && (
        <div>
          <h3>Address:</h3>
          <p className={style.address}>
            {park.addresses[0].line1}<br />
            {park.addresses[0].city}, {park.addresses[0].stateCode} {park.addresses[0].postalCode}
          </p>
        </div>
      )}

      <button className={style.parkBtn}>
        <Link 
          to={`/park/campgrounds/${park.parkCode}`} 
          className={style.linkBtn}
          state={{ parkName: park.fullName }}
        >
          Find Campgrounds
        </Link>
      </button>

      <ParkReview parkCode={park.parkCode} userId={userId}/>
      
    </div>
  );
}
