import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import styles from './Review.module.css';

export default function Review() {
  const { parkId } = useParams(); // Get the parkId from the URL
  const [reviews, setReviews] = useState([]);
  const [newReview, setNewReview] = useState("");
  const [error, setError] = useState(null);

  // Fetch reviews when the component mounts or parkId changes
  useEffect(() => {
    fetchReviews();
  }, [parkId]);

  // Fetch the reviews from the backend API for the specific park
  const fetchReviews = async () => {
    try {
      const response = await fetch(`http://localhost:8081/park-reviews/park/${parkId}`);
      if (!response.ok) throw new Error("Failed to fetch reviews");
      const data = await response.json();
      setReviews(data);
    } catch (err) {
      setError(err.message);
    }
  };

  // Submit a new review for the park
  const handleReviewSubmit = async () => {
    const userId = localStorage.getItem("userId");
    if (!userId || !newReview.trim()) return; // Ensure there's a user and review text

    try {
      const response = await fetch(`http://localhost:8081/park-reviews/${userId}/park/${parkId}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ reviewText: newReview })
      });
      if (!response.ok) throw new Error("Failed to submit review");
      setNewReview(""); // Clear the input after successful submission
      fetchReviews(); // Refresh the reviews list
    } catch (err) {
      setError(err.message); // Show error message if something goes wrong
    }
  };

  return (
    <div className={styles.reviewContainer}>
      <h2>Reviews for Park {parkId}</h2>

      {error && <p className={styles.errorMessage}>{error}</p>}

      {/* Textarea for submitting a new review */}
      <textarea
        className={styles.reviewTextarea}
        value={newReview}
        onChange={(e) => setNewReview(e.target.value)}
        placeholder="Write your review here..."
      />

      <button className={styles.submitReviewBtn} onClick={handleReviewSubmit}>
        Submit Review
      </button>

      {/* Display the list of reviews */}
      <div className={styles.reviewList}>
        <h3>Existing Reviews:</h3>
        {reviews.length > 0 ? (
          reviews.map((review) => (
            <div key={review.id} className={styles.reviewItem}>
              <p>{review.reviewText}</p>
            </div>
          ))
        ) : (
          <p>No reviews yet for this park.</p>
        )}
      </div>
    </div>
  );
}
