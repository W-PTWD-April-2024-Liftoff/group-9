import { useLocation, useNavigate, Link } from "react-router-dom";
import style from "./ParkDetail.module.css";
import { useRef, useState } from "react";

export default function ParkDetail() {
  const location = useLocation();
  const navigate = useNavigate();
  const carouselRef = useRef(null);
  const [message, setMessage] = useState("");

  const park = location.state?.park; // Retrieve passed park data

  if (!park) {
    return <p>No park data available.</p>; // Handle case where data is missing
  }

  const saveToFavorites = () => {
    const existingFavorites = JSON.parse(localStorage.getItem("favorites")) || [];
    if (!existingFavorites.some(fav => fav.id === park.id)) {
      const updatedFavorites = [...existingFavorites, park];
      localStorage.setItem("favorites", JSON.stringify(updatedFavorites));
    }
  };

  const subscribeToPark = async () => {
    const userId = localStorage.getItem("userId");

    if (!userId) {
      setMessage("You must be logged in to subscribe.");
      return;
    }

try {
  const response = await fetch(
    `http://localhost:8081/api/subscriptions/subscribe?userId=${userId}&parkCode=${park.parkCode}`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
    }
  );
      if (!response.ok) {
        throw new Error("Subscription failed.");
      }

      setMessage("✅ Subscribed to park updates!");
    } catch (err) {
      setMessage("❌ " + err.message);
    }
  };

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
      <button className={style.parkBtn} onClick={saveToFavorites}>
        Save to My List
      </button>

      {/* 👇 Subscription Button */}
      <button className={style.parkBtn} onClick={subscribeToPark}>
        Subscribe to Updates
      </button>

      {/* 👇 Optional Message Display */}
      {message && <p className={style.message}>{message}</p>}

      <button className={style.parkBtn}>
        <Link to="/favorites" className={style.linkBtn}>
          My Favorite Parks
        </Link>
      </button>

      <button className={style.parkBtn} onClick={goBack}>
        Back to Search
      </button>

      <h1>{park.fullName}</h1>

      <div className={style.carouselWrapper}>
        <button className={style.arrow} onClick={scrollLeft}>
          ◀
        </button>

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

        <button className={style.arrow} onClick={scrollRight}>
          ▶
        </button>
      </div>

      <p className={style.description}>{park.description}</p>

      <p className={style.parkUrl}>
        <a href={park.url} target="_blank" rel="noopener noreferrer">
          Visit Official Website
        </a>
      </p>

      <h3>Activities:</h3>
      <p className={style.activities}>
        {park.activities && park.activities.length > 0
          ? park.activities.map((a) => a.name).join(", ")
          : "No activities available"}
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
            {park.addresses[0].line1}
            <br />
            {park.addresses[0].city}, {park.addresses[0].stateCode}{" "}
            {park.addresses[0].postalCode}
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
    </div>
  );
}
