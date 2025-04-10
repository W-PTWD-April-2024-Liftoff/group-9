import React, { useState, useEffect } from 'react';
import axios from 'axios';
// import style from './SubscriptionButton.module.css'; // Import your styles

const SubscriptionButton = ({ userId, parkCode, parkName, parkDescription }) => {
    const [isSubscribed, setIsSubscribed] = useState(false);

    // Check if the user is already subscribed to the park on component mount
    useEffect(() => {
        const checkIfSubscribed = async () => {
            if (!userId) {
                console.error('User ID is missing. Please log in again.');
                return;
            }

            try {
                const response = await axios.get(`http://localhost:8081/subscriptions/${userId}`);
                const subscriptions = response.data;

                // Check if the park is already in the user's subscriptions
                if (subscriptions && subscriptions.some((sub) => sub.parkCode === parkCode)) {
                    setIsSubscribed(true);
                }
            } catch (err) {
                console.error('Error fetching subscriptions:', err);
            }
        };

        checkIfSubscribed();
    }, [userId, parkCode]);

    // Handle subscription logic
    const handleSubscribe = async () => {
        try {
            // Call the backend to subscribe the user to the park
            await axios.post('http://localhost:8081/subscriptions', null, {
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

    // Handle unsubscribe logic
    const handleUnsubscribe = async () => {
        try {
            // Call the backend to unsubscribe the user from the park
            await axios.delete('http://localhost:8081/subscriptions', {
                params: { userId, parkCode },
            });

            setIsSubscribed(false);
        } catch (err) {
            console.error('Error unsubscribing:', err);
        }
    };

    return (
        <button
            onClick={isSubscribed ? handleUnsubscribe : handleSubscribe}
            className={`${style.subscriptionButton} ${isSubscribed ? style.subscribed : ''}`}
        >
            {isSubscribed ? 'Subscribed' : 'Subscribe to this Park'}
        </button>
    );
};

export default SubscriptionButton;
