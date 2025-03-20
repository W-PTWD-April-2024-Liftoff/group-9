import React, {useEffect, useState} from 'react';
import {Route, Routes, useLocation, Link, useNavigate, NavLink} from 'react-router-dom';
import './App.module.css'
import ParksList from './components/ParkList';
import Login from './components/Login';
import Signup from './components/Signup';
import ParkDetail from './components/ParkDetail';
import FavoritesList from './components/FavoritesList';
import Campgrounds from './components/Campgrounds';
import CampgroundDetail from './components/CampgroundDetail';


function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalType, setModalType] = useState(null);
  const [username, setUsername] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
      // const openModal = (type) => {
      //     setModalType(type);
      //     setIsModalOpen(true);
      // };
      //
      // const closeModal = () => {
      //     setIsModalOpen(false);
      // };

      const handleSuccessSignup = () => {
          // closeModal();
          // setTimeout(() => {
          //     openModal('login');
          // }, 500);
          setIsAuthenticated(true);
          navigate('/dashboard');
      };

      const handleSuccessLogin = (data) => {
          // closeModal();
          setIsAuthenticated(true);
          // if (data.user && data.user.name) {
          //     localStorage.setItem('username', data.user.name);
          //     setUsername(data.user.name);
          // }
          navigate('/dashboard');
      }

      return (
          <div>
              <nav>

                      ParkQuest

                  <div>
                      {<button onClick={() => openModal('login')}>Login</button>}
                      {<button onClick={() => openModal('signup')}>Signup</button>}
                  </div>
              </nav>
              <div>
                  <Routes>
                      <Route path="/" element={
                          <div>
                              <div>
                                  <h1>Welcome to ParkQuest!</h1>
                                  <br/>
                                  <h3>Plan your trip to national parks with ease!</h3>
                              </div>
                          </div>
                      }
                      />
                      <Route path="/signup"
                             element={<Signup handleSuccessSignup={handleSuccessSignup} closeModal={closeModal}/>}
                      />
                      <Route path="/login"
                             element={<Login handleSuccessLogin={handleSuccessLogin} closeModal={closeModal}/>}
                      />
                      <Route path="/dashboard" element={<Dashboard/>}/>
                  </Routes>
              </div>

              <Modal isOpen={isModalOpen}
                     onRequestClose={closeModal}
                     ariaHideApp={false}
                     className={styles.appModal}
                     overlayClassName={styles.appModalOverlay}
              >
                  {modalType === 'login' && (<Login handleSuccessLogin={handleSuccessLogin} closeModal={closeModal}/>
                  )}
                  {modalType === 'signup' && (
                      <Signup handleSuccessSignup={handleSuccessSignup} closeModal={closeModal}/>
                  )}
              </Modal>
          </div>
      );
  });
}

export default App;