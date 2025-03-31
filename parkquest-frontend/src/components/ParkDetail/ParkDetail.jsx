import { useLocation, useNavigate, Link } from "react-router-dom";
import style from "./ParkDetail.module.css";

export default function ParkDetail() {
  const location = useLocation();
  const navigate = useNavigate();

  const park = location.state?.park; // Retrieve passed park data
  console.log("Park detail received:", park);
  
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

  const goBack = () => {
    navigate(-1);
  };

  return (
    <div className={style.parkDetails}>

      <button className={style.parkBtn} onClick={saveToFavorites}>Save to My List</button>
      
      <button className={style.parkBtn}>
        <Link to="/favorites" className={style.linkBtn}>My Favorite Parks</Link>
      </button>

      <button className={style.parkBtn} onClick={goBack}>Back to Search</button>

      <h1>{park.fullName}</h1>

      {/* {park.images?.length > 0 && (
        <img src={park.images[0].url} alt={park.images[0].altText || "Park Image"} />
      )} */}

      {park.images?.length > 0 && (
        <figure>
          <img 
            src={park.images[0].url} 
            alt={park.images[0].altText || "Park Image"} 
            title={park.images[0].title} // Tooltip on hover
          />
          {park.images[0].title && <figcaption>{park.images[0].title}</figcaption>} 
        </figure>
      )}
      <p className={style.description}>{park.description}</p>

      {/* <p><strong>Location:</strong> {park.states}</p> */}
      
      <p className={style.activities}><strong>Activities:</strong> 
        {park.activities && park.activities.length > 0 ? 
        park.activities.map(a => a.name).join(", ") : 
          "No activities available"}
      </p>

      <p className={style.parkUrl}>
        <a href={park.url} target="_blank" rel="noopener noreferrer">
          Visit Official Website
        </a>
      </p>

      <button className={style.parkBtn}>
        <Link 
          to={`/park/campgrounds/${park.parkCode}`} 
          className={style.linkBtn}
          state={{ parkName: park.fullName }}
        >
          Find Campgrounds
        </Link>
      </button>
      
      {park.operatingHours?.length > 0 && (
        <div className={style.hours}>
          <h3>Operating Hours:</h3>
          <ul>
            {park.operatingHours[0].standardHours && (
              <li>
                <strong>Standard Hours:</strong>
                <ul>
                  {Object.entries(park.operatingHours[0].standardHours).map(([day, hours]) => (
                    <li key={day}>
                      {day}: {hours}
                    </li>
                  ))}
                </ul>
              </li>
            )}
          </ul>
        </div>
      )}
    </div>
  );
}
