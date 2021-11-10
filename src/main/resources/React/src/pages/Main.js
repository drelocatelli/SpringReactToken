import React from 'react'
import { Button } from '@mui/material'

import {useAuth} from '../component/authProvider'

export const Main = () => {

    const auth = useAuth() 
    
    return(
        <div>
            Ola mundo

            <br />
            <Button onClick={auth.logout} variant={"contained"} type={"button"}>logout</Button>
        </div>
    )
}