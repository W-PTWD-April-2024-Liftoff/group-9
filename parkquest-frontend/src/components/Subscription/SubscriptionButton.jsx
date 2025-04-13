import React, { useState, useEffect } from "react";
import axios from "axios";
import style from "../ParkDetail/ParkDetail.module.css";

const SubscriptionButton = ({ userId, parkCode }) => {
  const [subscribed, setSubscribed] = useState(false);
  const [error, setError] = useState("");

  // Fallback to localStorage if userId is null
  const storedUserId = localStorage.getItem("userId");
  if (userId === null) userId = storedUserId;

  useEffect(() => {
    const checkSubscription = async () => {
      if (!userId) {
        console.error("❌ User ID is missing.");
        return;
      }

      try {
        const response = await axios.get(`http://localhost:8081/subscriptions/${userId}`);
        const subscriptions = response.data;

        if (subscriptions && subscriptions.some((sub) => sub.parkCode === parkCode)) {
          setSubscribed(true);
        }
      } catch (err) {
        console.error("Error fetching subscriptions:", err);
      }
    };

    checkSubscription();
  }, [parkCode]);

  const handleSubscribe = async () => {
    if (!userId || !parkCode) {
      console.error("❌ Invalid userId or parkCode:", { userId, parkCode });
      setError("User ID or Park Code is invalid.");
      return;
    }

    try {
      console.log("📩 Subscribing with:", { userId, parkCode });

      await axios.post("http://localhost:8081/subscriptions/subscribe", {
        userId,
        parkCode,
      });

      setSubscribed(true);
      setError("");
    } catch (err) {
      console.error("❌ Error during subscription:", err);
      setError("Subscription failed.");
    }
  };

  const handleUnsubscribe = async () => {
    try {
      await axios.delete("http://localhost:8081/subscriptions/unsubscribe", {
        params: { userId, parkCode },
      });
      setSubscribed(false);
    } catch (err) {
      console.error("Error unsubscribing:", err);
    }
  };

  return (
    <button
      onClick={subscribed ? handleUnsubscribe : handleSubscribe}
      className={`subscribe-button ${subscribed ? "subscribed" : ""}`}
      id={style.parkBtn}
    >
      {subscribed ? "Subscribed" : "Subscribe"}
    </button>
  );
};

export default SubscriptionButton;