import { useState } from "react";

const SubscriptionButton = ({ parkCode, userId }) => {
  const [subscribed, setSubscribed] = useState(false);
  const [error, setError] = useState("");
  const storedUserId = localStorage.getItem("userId");

  const id = userId || storedUserId;

  const handleSubscribe = async () => {
    if (!id) {
      setError("User ID is missing. Please log in again.");
      return;
    }

    try {
      const response = await fetch(
        `http://localhost:8081/api/subscriptions/subscribe?userId=${id}&parkCode=${parkCode}`,
        { method: "POST" }
      );

      if (!response.ok) throw new Error("Subscription failed.");
      setSubscribed(true);
      setError("");
    } catch (err) {
      setError(err.message);
    }
  };

  const handleUnsubscribe = async () => {
    try {
      const response = await fetch(
        `http://localhost:8081/api/subscriptions?userId=${id}&parkCode=${parkCode}`,
        { method: "DELETE" }
      );

      if (!response.ok) throw new Error("Unsubscription failed.");
      setSubscribed(false);
      setError("");
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div>
      {error && <p className="text-red-500">{error}</p>}
      <button onClick={subscribed ? handleUnsubscribe : handleSubscribe}>
        {subscribed ? "Unsubscribe" : "Subscribe"}
      </button>
    </div>
  );
};

export default SubscriptionButton;