import {Route, Routes, Link} from 'react-router-dom';
import '../App.css'
import FavoritesList from './FavoritesList/FavoritesList';

function Dashboard() {
  const userId = localStorage.getItem("userId");

  return (
    <div className='dashboard'>
        <Routes>
          <Route path="/" element={
            <div>
              <h1>Welcome to ParkQuest!</h1>
                <h2>Plan your trip to national parks with ease!</h2>
                  <div className='buttons'>
                    <button className="outline-button dashboard-btn">
                      <Link to="/parklist">Search Parks</Link>
                    </button>
                    
                    <button className="outline-button dashboard-btn">
                      <Link to="/favorites">My Favorite Parks</Link>
                    </button>

                    <button className="outline-button dashboard-btn">
                      <Link to="/trips">My Trips</Link>
                    </button>

                  </div>
              </div>
          }/>
          <Route
            path="/favorites"
            element={<FavoritesList userId={userId} />}
          />
        </ Routes>
    </div>
  );
}

export default Dashboard;
