import React from "react";

import {Route, Routes, BrowserRouter} from 'react-router-dom';

import {Home} from '../pages/home'
import {Main} from '../pages/Main'
import {Register} from '../pages/Register'

export default ()  => {

    return(
        <BrowserRouter>
            <Routes>
                <Route exact path="/" element={Home()} />
                <Route path={"/main"} element={Main()} />
                <Route path={"/register"} element={Register()} />
            </Routes>
        </BrowserRouter>
    )
    
}