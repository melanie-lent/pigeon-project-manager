import axios from 'axios';

export default axios.create({
    baseUrl: 'http://127.0.0.1:3000/'
    // headers: {'ngrok-ski-browser-warning': 'true'}
})