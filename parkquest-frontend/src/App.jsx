import React from 'react';
import { Route, Routes, useLocation, Link } from 'react-router-dom';
import './App.css'
import ParksList from './components/ParkList';
import Login from './components/Login';
import Signup from './components/Signup';
import ParkDetail from './components/ParkDetail';
import FavoritesList from './components/FavoritesList';
import Campgrounds from './components/Campgrounds';
import CampgroundDetail from './components/CampgroundDetail';


function App() {

  const location = useLocation(); // Get current route
  const isHomePage = location.pathname === "/"; // Check if on home page

  return (
    <div className='App'>
      <header>
        <h2 id="website-name"><Link to="/" id="website-name">ParkQuest</Link></h2>
        <div>
          <button class="account-button"><Link to="/Login">Login</Link></button>
          <button class="account-button"><Link to="/Signup">Signup</Link></button>
        </div>
      </header>

      <div className={isHomePage ? 'first-page' : ''}>
        <Routes>
          <Route path="/" element={
            <div>
              <h1>Welcome to ParkQuest!</h1>
              <br />
              <h3>Plan your trip to national parks with ease!</h3>
              <br /><br />
            </div>
          }
          />
          <Route path="/App" element={<App/>}/>
          <Route path="/signup" element={<Signup/>}/>
          <Route path="/login" element={<Login/>}/>
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
