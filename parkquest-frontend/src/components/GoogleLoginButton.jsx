import React from 'react';
import { GoogleLogin } from '@react-oauth/google';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const GoogleLoginButton = () => {
  const navigate = useNavigate();

  const handleLoginSuccess = async (response) => {
    console.log('Google Login Success:', response);

    try {
      // Send the Google token to your backend
      const res = await axios.post('http://localhost:8081/api/auth/google', {
        token: response.credential, // Google's JWT token
      });

      // Handle successful login
      console.log('Backend response:', res.data);
      navigate('/Dashboard'); // Redirect after successful login
    } catch (error) {
      console.error('Error logging in with Google:', error.response?.data || error.message);
    }
  };

  const handleLoginFailure = (error) => {
    console.error('Google Login Failed:', error);
  };

  return (
      <div>
        <GoogleLogin
            onSuccess={handleLoginSuccess}
            onError={handleLoginFailure}
        />
      </div>
  );
};

export default GoogleLoginButton;