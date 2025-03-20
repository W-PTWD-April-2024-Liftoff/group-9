import {useState} from "react";
import {renderToReadableStream} from "react-dom/server.js";
import {Link, Route, Routes} from "react-router-dom";
import ParksList from "./ParkList.jsx";
import ParkDetail from "./ParkDetail.jsx";
import FavoritesList from "./FavoritesList.jsx";

function Dashboard({username}){
    const [isModalOpen, setIsModalOpen] = useState(false);
    const navigate = useNavigate();
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [username, setUsername] = useState(null);

    const handleLogout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('username');
        setIsAuthenticated(false);
        setUsername("");
        navigate('/App');
    };

    const openModal = (type) => {
        setModalType(type);
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
    };

    return(
           <div className={styles.appContainer}>
                <nav className={styles.nav}>
                    <NavLink to="/"
                             className={isAuthenticated ? styles.disabledLink : ""}
                             tabIndex={isAuthenticated ? -1 : 0}
                    >
                        ParkQuest
                    </NavLink>
                    {username && <span className={styles.username}>Welcome, {username}</span>}
                    <div className={styles.authbuttons}>
                        {isAuthenticated && (<button onClick={handleLogout}>Logout</button>
                        )}
                    </div>
                </nav>
                <div className={styles.pageContent}>
                    <Routes>
                        <Route path="/Dashboard" element={
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
                        <Route path="/favorites" element={<FavoritesList/>}/>
                    </Routes>
                            </div>
        </div>
    );


}



export default Dashboard;