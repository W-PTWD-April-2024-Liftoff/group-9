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
import Header from './components/Header';
import ProtectedRoute from "./components/ProtectedRoute.jsx";
import {UserProvider, useUserContext} from "./components/UserContext";

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

    // Log the user out when the token becomes invalid
  const handleLogout = () => {
    localStorage.removeItem("authToken");
    setIsAuthenticated(false); // Update auth state
    navigate("/App");
  };

  return (
      <UserProvider>
    <div className='App'>
        {/* Render Header dynamically */}
        <Header isAuthenticated={isAuthenticated} onLogout={handleLogout} />


        <div className={isHomePage ? 'first-page' : ''}>

        <Routes>
          <Route path="/" element={
            <div>
              <h1>Welcome to ParkQuest!</h1>
              <br />
              <h3>Plan your trip to national parks with ease!</h3>
              <br /><br />
              <div>
                {/*<button className="outline-button">*/}
                {/*  <Link to="/Dashboard">Dashboard</Link>*/}
                {/*</button>*/}
              </div>
            </div>
          }
          />
          {/*Public Routes*/}
          <Route path="/signup" element={<Signup/>}/>
            <Route
                path="/login"
                element={<Login setIsAuthenticated={setIsAuthenticated} />}
            />
            <Route path="/App" element={<App/>}/>

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
                  <ParksList />
                </ProtectedRoute>
              }
          />
          <Route
              path="/parklist/:id"
              element={
                <ProtectedRoute isAuthenticated={isAuthenticated}>
                  <ParkDetail />
                </ProtectedRoute>
              }
          />
          <Route
              path="/favorites"
              element={
                <ProtectedRoute isAuthenticated={isAuthenticated}>
                  <FavoritesList />
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
              path="/hiking/:id"
              element={
                <ProtectedRoute isAuthenticated={isAuthenticated}>
                  <HikingTrails />
                </ProtectedRoute>
              }
          />
        </Routes>
      </div>
    </div>
      </UserProvider>
  )
}

export default App;
