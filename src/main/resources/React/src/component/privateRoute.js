import React from 'react'
import {Navigate, useLocation} from 'react-router-dom'

import {useAuth} from '../component/authProvider'

export const PrivateRoute = ({children}) => {

    let auth = useAuth();
    let location = useLocation();
  
    if (!auth.isAuth) {
      return <Navigate to="/" state={{ from: location }} />;
    }
  
    return children;

}