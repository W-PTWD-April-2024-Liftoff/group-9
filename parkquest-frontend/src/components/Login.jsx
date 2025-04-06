import React, {useState} from "react";
import axios from 'axios';
import {Link, useNavigate} from 'react-router-dom';
import {GoogleOAuthProvider} from '@react-oauth/google';
import GoogleLoginButton from "./GoogleLoginButton.jsx";
import {useUserContext} from "./UserContext.jsx";

function Login({ setIsAuthenticated }) { // Accept setIsAuthenticated as a prop
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const { loginUser } = useUserContext(); // Get loginUser from UserContext
    const navigate = useNavigate();

    //email validation helper function
    const isValidEmail = (email) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);

    //handle form submission
    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");

        try {
            //prevents blanks
            if (!email || !password) {
                setError('Please fill in all fields.');
                return;
            }
            //is an actual email
            if (!isValidEmail(email)) {
                setError("Please enter a valid email address");
                return;
            }
            //make login request to the backend
            const response = await axios.post("http://localhost:8081/api/auth/login", {
                email,
                password
            });
            // Assuming the backend responds with a token
            const {token, userId} = response.data;

            if (token) {
                console.log("Token received from backend:", token);
                localStorage.setItem("authToken", token);

                // Update userId globally using UserContext
                loginUser(userId);

                setIsAuthenticated(true); // Optional
                navigate("/Dashboard"); // Redirect to Dashboard
            } else {
                setError("Token missing in backend response.");
            }
        } catch (error) {
            console.error("Login request failed:", error.response?.data || error.message);
            setError(error.response?.data?.message || "An error occurred while logging in. Please try again.");
        }
    };



    // Save authentication token in localStorage
    //         localStorage.setItem("authToken", token);
    //
    //         // Redirect user to the Dashboard
    //         navigate("/Dashboard");
    //     } catch (error) {
    //         // Handle error responses from the backend
    //         console.error("Login failed:", error.response ? error.response.data : error.message);
    //         setError(
    //             error.response?.data?.message || "An error occurred while logging in. Please try again."
    //         );
    //     }
    // };


    return (
        <div className="account-forms">
            <script src="https://apis.google.com/js/platform.js" async defer></script>
            <h2>Login</h2>
            <form onSubmit={handleSubmit}>
                <input
                    id="email"
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    placeholder="Email"
                    required
                />
                <input
                    id="password"
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="Password"
                    required
                />
                {/* renders error message */}
                {error && <div className="error">{error}</div>}
                <button className="outline-button" type="submit">Login</button>
            </form>
            <div>
                <GoogleOAuthProvider
                    clientId="431740330929-ojmhr2kpqa7ocfbfcte5s396mrr0l6hu.apps.googleusercontent.com">
                    <GoogleLoginButton/>
                </GoogleOAuthProvider>
            </div>
            <div>
                <br/>
                <p>Not Registered? <Link to="/Signup" className="not-transparent-links">Register</Link></p>
            </div>
        </div>
    )
}

export default Login;