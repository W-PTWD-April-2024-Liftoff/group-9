import React, { useState, useEffect } from 'react';
import './ParkReview.module.css'; // Import the CSS module

const ParkReview = ({ parkId }) => {
  const [reviews, setReviews] = useState([]);
  const [reviewText, setReviewText] = useState('');
  const [rating, setRating] = useState(0);
  const [error, setError] = useState('');

  // Fetch reviews for a specific park when the component loads
  useEffect(() => {
    fetchReviews();
  }, [parkId]);

  // Fetch all reviews for the specified park
  const fetchReviews = async () => {
    try {
      const response = await fetch(`http://localhost:8081/parks/reviews/${parkId}`);
      if (!response.ok) {
        throw new Error('Failed to fetch reviews');
      }
      const data = await response.json();
      setReviews(data);
    } catch (err) {
      setError('Error fetching reviews');
    }
  };

  // Submit a new review
  const handleSubmitReview = async () => {
    const userReview = {
      parkId,
      reviewText,
      rating,
    };

    try {
      const response = await fetch('http://localhost:8080/parks/reviews/', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(userReview),
      });
      if (!response.ok) {
        throw new Error('Failed to submit review');
      }
      fetchReviews(); // Refresh the list of reviews
      setReviewText('');
      setRating(0);
    } catch (err) {
      setError('Error submitting review');
    }
  };

  // Delete a review
  const handleDeleteReview = async (reviewId) => {
    try {
      const response = await fetch(`http://localhost:8080/parks/reviews/${reviewId}`, {
        method: 'DELETE',
      });
      if (!response.ok) {
        throw new Error('Failed to delete review');
      }
      fetchReviews(); // Refresh the list of reviews after deletion
    } catch (err) {
      setError('Error deleting review');
    }
  };

  // Render each review item
  const renderReviews = () => {
    return reviews.map((review) => (
      <div key={review.id} className="review">
        <p><strong>User:</strong> {review.user.username}</p>
        <p><strong>Rating:</strong> {review.rating}</p>
        <p><strong>Review:</strong> {review.reviewText}</p>
        <button onClick={() => handleDeleteReview(review.id)} className="delete-btn">Delete</button>
      </div>
    ));
  };

  return (
    <div className="park-review-container">
      <h1>Park Reviews</h1>

      <div className="review-form">
        <textarea
          value={reviewText}
          onChange={(e) => setReviewText(e.target.value)}
          placeholder="Write your review..."
          className="review-textarea"
        />
        <input
          type="number"
          value={rating}
          onChange={(e) => setRating(Number(e.target.value))}
          min="1"
          max="5"
          placeholder="Rating (1-5)"
          className="rating-input"
        />
        <button onClick={handleSubmitReview} className="submit-btn">Submit Review</button>
      </div>

      {error && <p className="error">{error}</p>}

      <div className="reviews">
        {reviews.length > 0 ? renderReviews() : <p>No reviews yet.</p>}
      </div>
    </div>
  );
};

export default ParkReview;

