import React, { createContext, useContext, useState } from "react";

// Create a context
const UserContext = createContext(null);

// Custom hook to access `UserContext`
export function useUserContext() {
    return useContext(UserContext);
}

// UserContext provider to wrap around the app
export function UserProvider({ children }) {
    const [userId, setUserId] = useState(localStorage.getItem("userId") || null);

    // Persist userId in localStorage whenever it changes
    const loginUser = (id) => {
        localStorage.setItem("userId", id);
        setUserId(id);
    };

    const logoutUser = () => {
        localStorage.removeItem("userId");
        setUserId(null);
    };

    return (
        <UserContext.Provider value={{ userId, loginUser, logoutUser }}>
            {children}
        </UserContext.Provider>
    );
}