import axios from 'axios';
import React from 'react'

import {api} from '../main/API'

let AuthContext = React.createContext();

export const AuthProvider = ({children}) => {

    let [isAuth, setAuth] = React.useState(false);

    

    const changeToAuth = (callback) => {
        setAuth(true)

        callback()
    }

    const logout = () => {
        setAuth(false)
        window.localStorage.clear()
    }

    let value = { isAuth, changeToAuth, logout };

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
    
}

export const useAuth = () => {
    return React.useContext(AuthContext);
}