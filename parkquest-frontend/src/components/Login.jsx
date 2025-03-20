import React, { useState } from "react";
import axios from 'axios';
import {Link, useNavigate} from 'react-router-dom';
import { GoogleOAuthProvider } from '@react-oauth/google';
import GoogleLoginButton from "./GoogleLoginButton.jsx";

function Login() {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");


    const isValidEmail = (email) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);

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
         const response = await axios.post('http://localhost:8080/login', {
             username,
             email,
             password
         }, {
             headers: {
                 'Content-Type': 'application/json'
             }
         });
         if (response.status === 200) {
             history.push('/App');
         }
     } catch (error){
                //handles errors
                console.error('Login failed:', error.response ? error.response.data : error.message);
                setError(error.response ? error.response.data : error.message);
                }
            };

    return(
        <div className="account-forms">
            <script src="https://apis.google.com/js/platform.js" async defer></script>
            <h2>Login</h2>
            <form onSubmit={handleSubmit}>
                <input
                    id="username"
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    placeholder="Username"
                    required
                />
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
                <button class="outline-button" type="submit">Login</button>
            </form>
        <div>
                <GoogleOAuthProvider clientId="431740330929-ojmhr2kpqa7ocfbfcte5s396mrr0l6hu.apps.googleusercontent.com">
                    <GoogleLoginButton />
                </GoogleOAuthProvider>
        </div>
            <div>
                <br/>
                <p>Not Registered? <Link to="/Signup" className="not-transparent-links">Register</Link></p>
            </div>
        </div>
    )
};

export default Login;