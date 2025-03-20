import React, { useState } from 'react';
import axios from 'axios';
import {Link, useNavigate} from 'react-router-dom';
import AuthService from "./AuthService.js";

function Signup ({closeModal, handleSuccess}) {
    const [email, setEmail] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const [message, setMessage] = useState('');

    const isValidEmail = (email) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);

    const handleSignup = async (e) => {
        e.preventDefault();

        try {
            const response = await AuthService.register({email, username, password});
            setMessage(response.data);
            if (response.data === 'Registered successfully.') {
                navigate('/login');
            }
        }  catch
            (error)
            {
                setMessage('Regsitration failed.');
            }
        };
//             if (response.ok){
//                 //Handle successful signup
//                 console.log(response.data);
//                 history('/App');
//             } else {
//                 //Handle error
//                 console.error('Signup failed:', response.data);
//                 setError(response.data);
//             }
//             })
//             //prevents blanks
//             if (!email || ! password || !confirmPassword){
//                 setError('Please fill in all fields.');
//                 return;
//                 }
//             //password matches
//             if (password !== confirmPassword){
//                 throw new Error("Passwords do not match.");
//                 }
//             //is an actual email
//              if (!isValidEmail(email)) {
//                     setError("Please enter a valid email address");
//                     return;
//                 }
//
// //             const response = await axios.post('//TOTO: Add in Database, {
// //                 email,
// //                 password
// //                 });
//                 //Handle successful signup
//                 console.log(response.data);
//                 history('/App');
//             } catch (error){
//                 //handles errors
//                 console.error('Signup failed:', error.response ? error.response.data : error.message);
//                 setError(error.response ? error.response.data : error.message);
//                 }
//             };

    return (
        <div className="modalBox">
            <h2>Sign up</h2>
            <br />
            <form onSubmit={handleSignup} className="modalForm">
                {/* renders error message */}
                {message && <div className="error">{message}</div>}
                <input
                    id="email"
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                    className="modalInput"
                /><br />
                <input
                id="username"
                type="text"
                placeholder="Username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                className="modalInput"
                required
            /><br />
                <input
                    id="password"
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="modalInput"
                    required
                /><br />

                <button type="submit" class="modalButton">Sign up</button>

            </form>
            <div>
                <br />
                <p>Already registered? <Link to="/Login" className="not-transparent-links">Login</Link></p>
            </div>
        </div>
        )
    };

export default Signup;