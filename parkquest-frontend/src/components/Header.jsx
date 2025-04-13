import React from 'react';
import { Link } from 'react-router-dom';

const Header = ({ isAuthenticated, onLogout }) => {
    return (
        <header>
            <h2 id="website-name">
                <Link to={isAuthenticated ? "/dashboard" : "/"} id="website-name">ParkQuest</Link>
            </h2>
            <div>
                {isAuthenticated ? (
                    <>
                        <button className="account-button">
                            <Link to="/profile">Profile</Link>
                        </button>
                        <button className="account-button" onClick={onLogout}>Logoff</button>
                    </>
                ) : (
                    <>
                        <button className="account-button"><Link to="/login">Login</Link></button>
                        <button className="account-button"><Link to="/signup">Signup</Link></button>
                    </>
                )}
            </div>
        </header>
    );
};

export default Header;