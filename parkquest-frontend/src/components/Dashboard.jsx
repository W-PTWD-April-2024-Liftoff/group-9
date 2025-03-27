import React, {useState} from 'react';
import {Route, Routes, useLocation, Link, useNavigate} from 'react-router-dom';
import './App.css'
import ParksList from './components/ParkList';
import Login from './components/Login';
import Signup from './components/Signup';
import ParkDetail from './components/ParkDetail';
import FavoritesList from './components/FavoritesList';
import Campgrounds from './components/Campgrounds';
import CampgroundDetail from './components/CampgroundDetail';


function App() {
    const navigate = useNavigate();
    const [error, setError] = useState(null);
    const location = useLocation(); // Get current route
    const isHomePage = location.pathname === "/"; // Check if on home page

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
            } else{
                setError('Failed to log out');
            }
        } catch(error){
            console.log('Error logging out', error);
        }
    };

    return (
        <div>
            <header>
                <h2 id="website-name"><Link to="/" id="website-name">ParkQuest</Link></h2>
                <div>
                    <button class="account-button" onclick ={handleLogout}>Logoff</button>
                </div>
            </header>

            <div >
                <Routes>
                    <Route path="/" element={
                        <div>
                            <h1>Welcome to ParkQuest!</h1>
                            <br />
                            <h3>Plan your trip to national parks with ease!</h3>
                            <br /><br />
                            <button class="outline-button">
                                <Link to="/parklist">Search Parks/Campgrounds</Link>
                            </button>
                            <button class="outline-button">
                                <Link to="/favorites">My Favorite Parks</Link>
                            </button>
                        </div>
                    }
                    />
                    <Route path="/App" element={<App/>}/>
                    <Route path="/parklist" element={<ParksList/>}/>
                    <Route path="/parks/:id" element={<ParkDetail/>}/>
                    <Route path="/park/campgrounds/:id" element={<Campgrounds/>}/>
                    <Route path="/campgrounds/:campgroundId" element={<CampgroundDetail/>}/>
                    <Route path="/favorites" element={<FavoritesList/>}/>
                </Routes>
            </div>
        </div>
    )
};

export default App;
