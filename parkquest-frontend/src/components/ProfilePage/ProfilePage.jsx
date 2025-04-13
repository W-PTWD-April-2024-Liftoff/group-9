import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import style from "./Profile.module.css";
import ParkReview from "../ParkReview/ParkReview"; // Adjusted to correct relative path



const ProfilePage = () => {
    const [email, setEmail] = useState("");
    const [currentPassword, setCurrentPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const navigate = useNavigate();

    const userId = localStorage.getItem("userId");
    const isAdmin = localStorage.getItem("isAdmin") === "true";  // Fetching admin status from localStorage

    const handleUpdateProfile = async (e) => {
        e.preventDefault();
        try {
            if (!userId) throw new Error("User ID is missing. Please log in again.");

            // Prepare the payload with isAdmin flag
            const payload = {
                userId,
                email,
                currentPassword,
                newPassword,
                isAdmin // Passing isAdmin status
            };
            console.log("Payload being sent to backend:", payload);

            // Send a PUT request to the backend to update user details
            const response = await axios.put("http://localhost:8081/api/users/update-profile", payload);

            setSuccess(response.data.message); // Display backend success message
            setError(""); // Clear errors

            // Optionally log the user out if the password is changed
            if (newPassword) {
                localStorage.removeItem("authToken");
                localStorage.removeItem("userId");
                navigate("/login");
            }
        } catch (err) {
            console.error("Error response from backend:", err.response?.data || err.message);
            setError(err.response?.data?.message || "An error occurred. Please try again.");
            setSuccess("");
        }
    };

    return (
        <div className={style.profilePage}>
            <h1>Edit Profile</h1>

            {error && <p className={style.error}>{error}</p>}
            {success && <p className={style.success}>{success}</p>}

            <form onSubmit={handleUpdateProfile} className={style.form}>
                <label>
                    Email:
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        placeholder="Update email"
                    />
                </label>

                <label>
                    Current Password:
                    <input
                        type="password"
                        value={currentPassword}
                        onChange={(e) => setCurrentPassword(e.target.value)}
                        placeholder="Current password"
                    />
                </label>

                <label>
                    New Password:
                    <input
                        type="password"
                        value={newPassword}
                        onChange={(e) => setNewPassword(e.target.value)}
                        placeholder="New password (optional)"
                    />
                </label>

                <button type="submit" className={style.submitButton}>
                    Update Profile
                </button>
            </form>

            {/* Render ParkReview component with admin status */}
            <ParkReview parkCode="someParkCode" userId={userId} isAdmin={isAdmin} />
        </div>
    );
};

export default ProfilePage;
