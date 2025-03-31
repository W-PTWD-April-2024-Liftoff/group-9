import React, {useState} from 'react';
import {Route, Routes, useLocation, Link, useNavigate} from 'react-router-dom';
import '../App.css'
import App from '../App';
import ParksList from './ParkList/ParksList';
import ParkDetail from './ParkDetail/ParkDetail';
import FavoritesList from './FavoritesList/FavoritesList';
import Campgrounds from './CampgroundsList/CampgroundsList';
import CampgroundDetail from './CampgroundDetail/CampgroundDetail';


function Dashboard() {
    const navigate = useNavigate();
    const location = useLocation(); // Get current route
    const [error, setError] = useState(null);

    const handleLogout = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/auth/logout', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            if (response.ok) {
                console.log('Logged out successfully');
                navigate('/App');
            } else {
                setError('Failed to log out');
            }
        } catch (error) {
            console.log('Error logging out', error);
        }
    };

    return (
        <div>
            <header>
                <h2 id="website-name"><Link to="/" id="website-name">ParkQuest</Link></h2>
                <div>
                    <button className="account-button" onClick={handleLogout}>Logoff</button>
                </div>
            </header>
            <div>
                <Routes>
                    <Route path="/" element={
                        <div>
                            <h1>Welcome to ParkQuest!</h1>
                            <br/>
                            <h3>Plan your trip to national parks with ease!</h3>
                            <br/><br/>
                            <div>
                                <button className="outline-button">
                                    <Link to="/parklist">Search Parks</Link>
                                </button>
                                <button className="outline-button">
                                    <Link to="/favorites">My Favorite Parks</Link>
                                </button>
                            </div>
                            </div>
                            }/>
                        </ Routes>
                        </div>
                        </div>
                        );
            }

export default Dashboard;