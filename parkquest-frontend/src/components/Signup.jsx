import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Signup () {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [error, setError] = useState('');


    const isValidEmail = (email) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);

    const handleSignup = async (e) => {
        e.preventDefault();
        setError("");

        try{
            //prevents blanks
            if (!email || ! password || !confirmPassword){
                setError('Please fill in all fields.');
                return;
                }
            //password matches
            if (password !== confirmPassword){
                throw new Error("Passwords do not match.");
                }
            //is an actual email
             if (!isValidEmail(email)) {
                    setError("Please enter a valid email address");
                    return;
                }

//             const response = await axios.post('//TOTO: Add in Database, {
//                 email,
//                 password
//                 });
                //Handle successful signup
                console.log(response.data);
                history('/App');
            } catch (error){
                //handles errors
                console.error('Signup failed:', error.response ? error.response.data : error.message);
                setError(error.response ? error.response.data : error.message);
                }
            };

    return (
        <div>
            <h2>Sign up</h2>
            <form onSubmit={handleSignup}>
                {/* renders error message */}
                {error && <div className="error">{error}</div>}
                <input
                    id="email"
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
                <input
                    id="password"
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <input
                    id="confirmPassword"
                    type="password"
                    placeholder="Confirm Password"
                    value={confirmPassword}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <button type="submit">Sign up</button>
            </form>
            <div>
                <p>Already registered? <a href="/Login">Login</a></p>
            </div>
        </div>
        )
    };

export default Signup;