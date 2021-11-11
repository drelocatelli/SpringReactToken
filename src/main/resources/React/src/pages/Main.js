import React, {useEffect, useState} from 'react'
import {Button, TableContainer, TableHead, Table, TableCell, TableBody, TableRow} from '@mui/material'
import axios from "axios";

import {useAuth} from '../component/authProvider'
import {api} from "../main/API";

export const Main = () => {

    const auth = useAuth()

    const [email, setEmail] = useState('')
    const [name, setName] = useState('')
    const [message, setMessage] = useState('')
    const [data, setData] = useState([])

    useEffect(() => {

        // verifica token
        api().get('/me', {
            headers: {
                'Authorization': `Bearer ${window.localStorage.getItem("userLoggedIn")}`,
                'Access-Control-Allow-Origin': '*'
            }
        }).then(response => {
            setEmail(response.data.email)
            setName(response.data.username)
        }).catch(err => {
            console.log(err)
        })

        // lista todos usuarios
        api().get('/perpage', {
            headers: {
                'Authorization': `Bearer ${window.localStorage.getItem("userLoggedIn")}`,
                'Access-Control-Allow-Origin': '*'
            }
        }).then(response => {
            setData(response.data.content)
        }).catch(err => {
            console.log(err)
        })

    }, [] );

    console.log(data)
    
    return(
        <div>
            Welcome, {name} <br/>
            <br/>
            <Button onClick={auth.logout} variant={"contained"} type={"button"}>logout</Button>
            <hr/>
            <h1>Listing all users</h1>
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableCell>Id</TableCell>
                        <TableCell>Name</TableCell>
                        <TableCell>Email</TableCell>
                        <TableCell>Password</TableCell>
                    </TableHead>

                    <TableBody>
                        {data.filter(i => {
                            return i.email !== email
                        }).map((user) => {
                            return (
                                <TableRow>
                                    <TableCell>{user.id}</TableCell>
                                    <TableCell>{user.username}</TableCell>
                                    <TableCell>{user.email}</TableCell>
                                    <TableCell>{user.password}</TableCell>
                                </TableRow>
                            )
                        })}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    )
}