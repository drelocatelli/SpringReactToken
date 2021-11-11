import axios from 'axios'

export const api = () => {

    const instance = axios.create({baseURL: 'http://localhost:8080/api'})
    
    return instance

}