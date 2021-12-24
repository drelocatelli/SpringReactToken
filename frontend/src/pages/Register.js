import React, {useEffect, useState} from 'react'
import {Button, TextField, Grid, Alert, MenuItem} from "@mui/material";
import {useNavigate} from "react-router-dom";
import {api} from '../main/API'

export const Register = () => {

    const [name, setName] = useState('')

    const [email, setEmail] = useState('')

    const [password, setPassword] = useState('')

    const [message, setMessage] = useState('')

    const [messageType, setMessageType] = useState('')

    const [departments, setDepartments] = useState([])

    const [departmentSelection, setDepartmentSelection] = useState('')

    const navigate = useNavigate()

    function changeDepartment(e) {
        setDepartmentSelection(e.target.value)
    }

    // get departments
    useEffect(() => {
        api().get('department/all')
            .then(response => {
                setDepartments(response.data)
            }).catch(err => {
            setMessageType("error")
            setMessage(err.response.data)
            console.log(err.response.data)
        })
    }, [])


    // cadastra usuario
    function submitForm(e) {
        e.preventDefault()

        api().post('/user/register', {
            username: name,
            email,
            password,
            department: departmentSelection
        }).then(response => {
            console.log(response)
            setMessageType("success")
            setMessage("Now you can login")
        }).catch(err => {
            setMessageType("error")
            setMessage(err.response.data)
            console.log(err.response.data)
        })
    }

    return(
        <Grid container spacing={2}  direction="column" alignItems="center" justifyContent="center">
            <Grid item xs={3}>
                <h3>Register to the jungle</h3>
                    {(message) &&
                        <div>
                            <Alert severity={messageType}>
                                {message}
                            </Alert>
                            <br/>
                        </div>
                    }
            </Grid>
            <form onSubmit={submitForm}>
                <Grid container item spacing={1} direction={"column"} justifyContent="center">
                    <Grid item xs={3}>
                        <TextField label={"Name"} type={"text"} value={name} onChange={e => {setName(e.target.value)}}></TextField>
                    </Grid>
                    <Grid item xs={3}>
                        <TextField label={"Email"} type={"input"} value={email} onChange={e => {setEmail(e.target.value)}}></TextField>
                    </Grid>
                    <Grid item xs={3}>
                        <TextField label={"Password"} type={"password"} value={password} onChange={e => {setPassword(e.target.value)}}></TextField>
                    </Grid>
                    <Grid item xs={3}>
                        <TextField select fullWidth value={departmentSelection} label={"Department"} onChange={changeDepartment}>
                            {departments.map(department => {
                                return (
                                    <MenuItem value={department.id}>{department.title}</MenuItem>
                                )
                            })}
                        </TextField>
                    </Grid>
                    <Grid item xs={3} textAlign={"center"}>
                        <Button variant={"contained"} type={"submit"}>Confirm</Button>
                    </Grid>
                </Grid>
            </form>
        </Grid>
    )
}