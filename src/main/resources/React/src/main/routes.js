import React from "react";

import {Route, Routes, BrowserRouter} from 'react-router-dom';

import {Home} from '../pages/home'
import {Main} from '../pages/Main'
import {Register} from '../pages/Register'

import {AuthProvider} from '../component/authProvider'

import {PrivateRoute} from '../component/privateRoute'

export default ()  => {

    return(
        <BrowserRouter>
            <AuthProvider>
                <Routes>
                    <Route exact path="/" element={<Home/>} />
                    <Route path={"/main"} element={<PrivateRoute><Main /></PrivateRoute>} />
                    <Route path={"/register"} element={<Register />} />
                </Routes>
            </AuthProvider>
        </BrowserRouter>
    )
    
}