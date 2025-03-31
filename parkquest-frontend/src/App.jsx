import React from 'react';
import { Route, Routes, useLocation, Link } from 'react-router-dom';
import './App.css'
import ParksList from './components/ParkList/ParksList';
import Login from './components/Login';
import Signup from './components/Signup';
import Dashboard from './components/Dashboard';
import ParkDetail from './components/ParkDetail/ParkDetail';
import FavoritesList from './components/FavoritesList/FavoritesList';
import CampgroundDetail from './components/CampgroundDetail/CampgroundDetail';
import CampgroundsList from './components/CampgroundsList/CampgroundsList';


function App() {

  const location = useLocation(); // Get current route
  const isHomePage = location.pathname === "/"; // Check if on home page

  return (
    <div className='App'>
      <header>
        <h2 id="website-name"><Link to="/" id="website-name">ParkQuest</Link></h2>
        <div>
          <button className="account-button"><Link to="/Login">Login</Link></button>
          <button className="account-button"><Link to="/Signup">Signup</Link></button>
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
              <div>
                <button className="outline-button">
                  <Link to="/Dashboard">Dashboard</Link>
                </button>
              </div>
            </div>
          }
          />
          <Route path="/Dashboard" element={<Dashboard/>}/>
          <Route path="/App" element={<App/>}/>
          <Route path="/signup" element={<Signup/>}/>
          <Route path="/login" element={<Login/>}/>
          <Route path="/parklist" element={<ParksList/>}/>
          <Route path="/parklist/:id" element={<ParkDetail/>}/>
          <Route path="/park/campgrounds/:id" element={<CampgroundsList/>}/>
          <Route path="/campgrounds/:campgroundId" element={<CampgroundDetail/>}/>
          <Route path="/favorites" element={<FavoritesList/>}/>
        </Routes>
      </div>
    </div>
  )
}

export default App;
