import React, {useEffect, useState, } from 'react';
import { Route, Routes, useLocation, Link, useNavigate } from 'react-router-dom';
import './App.css'
import ParksList from './components/ParkList/ParksList';
import Login from './components/Login';
import Signup from './components/Signup';
import Dashboard from './components/Dashboard';
import ParkDetail from './components/ParkDetail/ParkDetail';
import FavoritesList from './components/FavoritesList/FavoritesList';
import CampgroundDetail from './components/CampgroundDetail/CampgroundDetail';
import CampgroundsList from './components/CampgroundsList/CampgroundsList';
import HikingTrails from './components/HikingTrails/HikingTrails';
import Trips from './components/Trips/Trips';
import TripDetails from './components/Trips/TripDetails';
import Header from './components/Header';
import ProfilePage from './components/ProfilePage/ProfilePage';
import ProtectedRoute from "./components/ProtectedRoute.jsx";

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const location = useLocation(); // Get current route
  const isHomePage = location.pathname === "/"; // Check if on home page
  const navigate = useNavigate();


    // Check user's authentication status
    useEffect(() => {
        const token = localStorage.getItem("authToken");
        setIsAuthenticated(!!token); // Set authentication based on token presence
    }, []);

    useEffect(() => {
      const token = localStorage.getItem("authToken");
      if (token && location.pathname === "/") {
        navigate("/dashboard");
      }
    }, [location, navigate]);

    // Log the user out when the token becomes invalid
  const handleLogout = () => {
    localStorage.removeItem("authToken");
    setIsAuthenticated(false); // Update auth state
    navigate("/");
  };

  return (
    <div className='App'>
        {/* Render Header dynamically */}
        <Header isAuthenticated={isAuthenticated} onLogout={handleLogout} />

        <div className={isHomePage ? 'first-page' : ''}>

        <Routes>
          <Route path="/" element={
            <div>
              <h1>Welcome to ParkQuest!</h1>
              <h2>Plan your trip to national parks with ease!</h2>
            </div>
          }
          />
          
          {/*Public Routes*/}
          <Route path="/signup" element={<Signup/>}/>
            <Route
                path="/login"
                element={<Login setIsAuthenticated={setIsAuthenticated} />}
            />

          {/* Protected Routes */}
          <Route
              path="/dashboard"
              element={
                <ProtectedRoute isAuthenticated={isAuthenticated}>
                  <Dashboard />
                </ProtectedRoute>
              }
          />
          <Route
              path="/parklist"
              element={
                <ProtectedRoute isAuthenticated={isAuthenticated}>
                  <ParksList userId={localStorage.getItem("userId")}/>
                </ProtectedRoute>
              }
          />
          <Route
              path="/parklist/:id"
              element={
                <ProtectedRoute isAuthenticated={isAuthenticated}>
                  <ParkDetail userId={localStorage.getItem("userId")} />
                </ProtectedRoute>
              }
          />
          <Route
              path="/favorites"
              element={
                <ProtectedRoute isAuthenticated={isAuthenticated}>
                    <FavoritesList userId={localStorage.getItem("userId")} />
                </ProtectedRoute>
              }
          />
          <Route
              path="/park/campgrounds/:id"
              element={
                <ProtectedRoute isAuthenticated={isAuthenticated}>
                  <CampgroundsList />
                </ProtectedRoute>
              }
          />
          <Route
              path="/campgrounds/:campgroundId"
              element={
                <ProtectedRoute isAuthenticated={isAuthenticated}>
                  <CampgroundDetail />
                </ProtectedRoute>
              }
          />
          <Route
              path="park/hiking/:id"
              element={
                <ProtectedRoute isAuthenticated={isAuthenticated}>
                  <HikingTrails />
                </ProtectedRoute>
              }
          />
            <Route
                path="/trips"
                element={
                    <ProtectedRoute isAuthenticated={isAuthenticated}>
                        <Trips userId={localStorage.getItem("userId")} />
                    </ProtectedRoute>
                }
            />
            <Route
                path="/tripdetails/:id"
                element={
                    <ProtectedRoute isAuthenticated={isAuthenticated}>
                        <TripDetails />
                    </ProtectedRoute>
                }
            />
            <Route
                path="/trips/:tripId"
                element={
                    <ProtectedRoute isAuthenticated={isAuthenticated}>
                        <TripDetails />
                    </ProtectedRoute>
                }
            />
            {/*<Route*/}
            {/*    path="/parkDetails/:parkCode"*/}
            {/*    element={*/}
            {/*        <ProtectedRoute isAuthenticated={isAuthenticated}>*/}
            {/*            <TripDetails />*/}
            {/*        </ProtectedRoute>*/}
            {/*    }*/}
            {/*/>*/}
            <Route
                path="/profile"
                element={
                    <ProtectedRoute isAuthenticated={isAuthenticated}>
                        <ProfilePage />
                    </ProtectedRoute>
                }
            />

        </Routes>
      </div>
    </div>
  )
}

export default App;
