import React, { useState, useEffect } from "react";
import {Button, Grid, TextField, Link} from "@mui/material";
import {useNavigate} from 'react-router-dom'

import {api} from '../main/API'

import {useAuth} from '../component/authProvider'

export const Home = () => {

    useEffect(() => {
        api().get('/me', {
            headers: {
                'Authorization': `Bearer ${window.localStorage.getItem("userLoggedIn")}`, 
                'Access-Control-Allow-Origin': '*'
            }
        }).then(response => {
            auth.changeToAuth(() => navigate('/main'))
        }).catch(err => {
            auth.logout()
        })
    }, [])

    const [email, setEmail] = useState('')

    const [password, setPassword] = useState('')

    const [message, setMessage] = useState('')

    const navigate = useNavigate()

    const auth = useAuth()

    const onSubmit = (e) => {
        e.preventDefault()

        api().post('/login', { email, password })
            .then(response => {
                window.localStorage.setItem("userLoggedIn", response.data.token)
                auth.changeToAuth(() => navigate('/main'))
                
            }).catch(err => {
                window.localStorage.clear()
                setMessage("User not found")
            })
    }

    return(
        <form onSubmit={onSubmit}>
            <Grid container spacing={2}  direction="column" alignItems="center" justifyContent="center">
                <Grid item xs={3}>
                    <h3>Welcome to the jungle</h3> 
                    <p>{message}</p>
                </Grid>
                <Grid container spacing={1} direction={"column"}  direction="column" alignItems="center" justifyContent="center">
                    <Grid item xs={3}>
                        <TextField label={"Email"} type={"email"} value={email} onChange={e => {setEmail(e.target.value)}}></TextField>
                    </Grid>
                    <Grid item xs={3}>
                        <TextField label={"Password"} type={"password"}  value={password} onChange={e => {setPassword(e.target.value)}}></TextField>
                    </Grid>
                    <Grid item xs={3}>
                        <Button variant={"contained"} type={"submit"}>Login</Button>
                    </Grid>
                    <Grid item spacing={4}>
                        <Link href={"/register"} underline={"hover"}>Need an account</Link>
                    </Grid>
                </Grid>
            </Grid>
        </form>

    )
    
}