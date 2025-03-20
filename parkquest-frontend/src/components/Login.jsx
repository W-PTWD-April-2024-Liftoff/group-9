import React, { useState } from "react";
import axios from 'axios';
import {Link, useNavigate} from 'react-router-dom';
import { GoogleOAuthProvider } from '@react-oauth/google';
import GoogleLoginButton from "./GoogleLoginButton.jsx";
import AuthService from "./AuthService.js";

function Login({handleSuccess, closeModal}) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [message, setMessage] = useState("");

    const isValidEmail = (email) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);

    const handleLogin = async (e) => {
        e.preventDefault();
        setError("");

        try {
            const response = await AuthService.login({email, password});
            if (response.data === 'Login successful.') {
                navigate('/dashboard');
            } else {
                setMessage('Login failed.');
            }
        } catch (error) {
            setMessage('Login failed.');
        }
    };

        //  //prevents blanks
        //     if (!email || ! password){
        //         setError('Please fill in all fields.');
        //         return;
        //         }
        //     //is an actual email
        //      if (!isValidEmail(email)) {
        //             setError("Please enter a valid email address");
        //             return;
        //         }
        //     const response = await axios.post('http://localhost:8080/auth/login', {
        //         username,
        //         password
        //         });
        //         // Handle successful login
        //         console.log('Login successful:', response.data);
        //         history('/App');
        //
        // } catch (error){
        //         //handles errors
        //         console.error('Login failed:', error.response ? error.response.data : error.message);
        //         setError(error.response ? error.response.data : error.message);
        //         }
        //     };

    return(
        <div className="modalBox">
            <script src="https://apis.google.com/js/platform.js" async defer></script>
            <h2>Login</h2>
            <form onSubmit={handleLogin} className="modalForm">
                <input
                    id="email"
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    placeholder="Email"
                    className="modalInput"
                    required
                />
                <input
                    id="password"
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="Password"
                    className="modalInput"
                    required
                />
                {/* renders error message */}
                {message && <div className="error">{message}</div>}
                <button className="modalButton" type="submit">Login</button>
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