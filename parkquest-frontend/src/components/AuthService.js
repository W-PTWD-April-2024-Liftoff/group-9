import axios from 'axios';

const API_BASE_URL = "http://localhost:8080/api/auth";

class AuthService {
    signup(user) {
        return axios.post(`${API_BASE_URL}/signup`, user);
    }
    login(user) {
        return axios.post(`${API_BASE_URL}/login`, user);
    }
}
export default new AuthService();