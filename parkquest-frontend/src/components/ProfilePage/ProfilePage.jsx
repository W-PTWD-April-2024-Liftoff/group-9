import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import style from "./Profile.module.css";

const ProfilePage = () => {
    const [email, setEmail] = useState("");
    const [currentPassword, setCurrentPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [error, setError] = useState("");
    const [isAdmin, setIsAdmin] = useState(false);
    const [success, setSuccess] = useState("");
    const navigate = useNavigate();

    const userId = localStorage.getItem("userId");

    // Handles the form submission for updating profile information
    const handleUpdateProfile = async (e) => {
        // Prevent page refresh on form submit
        e.preventDefault();
        try {
            if (!userId) throw new Error("User ID is missing. Please log in again.");

            // Log the payload being sent
            const payload = {
                userId,
                email,
                currentPassword,
                newPassword,
                isAdmin: isAdmin.toString()
            };
            console.log("Payload being sent to backend:", payload);

            // Send a PUT request to the backend to update user details
            const response = await axios.put("http://localhost:8081/api/users/update-profile", payload);

            setSuccess(response.data.message); // Display backend success message
            setError(""); // Clear errors

            // If a new password is provided, log the user out for security reasons
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

            {/* Display error message if an error occurs */}
            {error && <p className={style.error}>{error}</p>}
            {/* Display success message upon successful update */}
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

                <label>
                    <input
                        type="checkbox"
                        checked={isAdmin}
                        onChange={() => setIsAdmin(!isAdmin)}
                    />
                    Admin Role
                </label>


                <button type="submit" className={style.submitButton}>
                    Update Profile
                </button>
            </form>
        </div>
    );
};

export default ProfilePage;