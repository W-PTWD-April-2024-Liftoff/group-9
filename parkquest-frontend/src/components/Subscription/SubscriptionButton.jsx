import React, { useState, useEffect } from 'react';
import axios from 'axios';
// import style from './SubscriptionButton.module.css'; // Uncomment if using custom styles

const SubscriptionButton = ({ userId, parkCode, parkName, parkDescription }) => {
  const [isSubscribed, setIsSubscribed] = useState(false);

  useEffect(() => {
    const checkIfSubscribed = async () => {
      if (!userId) {
        console.error('User ID is missing. Please log in again.');
        return;
      }

      try {
        const response = await axios.get(`http://localhost:8081/api/subscriptions/${userId}`);
        const subscriptions = response.data;

        // Check if this park is already subscribed
        if (subscriptions && subscriptions.some((sub) => sub.park.parkCode === parkCode)) {
          setIsSubscribed(true);
        }
      } catch (err) {
        console.error('Error fetching subscriptions:', err);
      }
    };

    checkIfSubscribed();
  }, [userId, parkCode]);

  const handleSubscribe = async () => {
    try {
      await axios.post('http://localhost:8081/api/subscriptions/subscribe', null, {
        params: {
          userId,
          parkCode,
          parkName,
          parkDescription,
        },
      });

      setIsSubscribed(true);
    } catch (err) {
      console.error('Error subscribing:', err);
    }
  };

  const handleUnsubscribe = async () => {
    try {
      await axios.delete('http://localhost:8081/api/subscriptions', {
        params: {
          userId,
          parkCode,
        },
      });

      setIsSubscribed(false);
    } catch (err) {
      console.error('Error unsubscribing:', err);
    }
  };

  return (
    <button
      onClick={isSubscribed ? handleUnsubscribe : handleSubscribe}
      className={`subscription-button ${isSubscribed ? 'subscribed' : ''}`} // Adjust as needed if using CSS
    >
      {isSubscribed ? 'Subscribed' : 'Subscribe to this Park'}
    </button>
  );
};

export default SubscriptionButton;
