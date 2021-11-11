import React, {useEffect, useState} from 'react'
import {
    Button,
    TableContainer,
    TableHead,
    Table,
    TableCell,
    TableBody,
    TableRow,
    Accordion,
    AccordionSummary,
    Typography,
    AccordionDetails,
    Card, Grid, TextField, Link
} from '@mui/material'
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

import {useAuth} from '../component/authProvider'
import {api} from "../main/API";

export const Main = () => {

    const auth = useAuth()

    const [email, setEmail] = useState('')
    const [name, setName] = useState('')
    const [message, setMessage] = useState('')
    const [data, setData] = useState([])
    const [department, setDepartment] = useState([])

    const[depTitle, setDeptitle] = useState('')

    useEffect(() => {

        // verifica token
        api().get('/user/me', {
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
        api().get('/user/perpage', {
            headers: {
                'Authorization': `Bearer ${window.localStorage.getItem("userLoggedIn")}`,
                'Access-Control-Allow-Origin': '*'
            }
        }).then(response => {
            setData(response.data.content)
        }).catch(err => {
            console.log(err)
        })

        // list all departments
        departmentList()

    }, [] );

    function departmentList() {
        // list all departments
        api().get('/department/all')
            .then(response => {
                setDepartment(response.data)
            }).catch(err => {
            console.log(err)
        })
    }

    function newDepartment(e) {
        e.preventDefault()
        // add new department
        api().post('/department/register', {
            title: depTitle
        }, {
                headers: {
                    'Authorization': `Bearer ${window.localStorage.getItem("userLoggedIn")}`,
                    'Access-Control-Allow-Origin': '*'
                }
        }).then(response => {
            setDeptitle('')
            departmentList()
            console.log(response)
        }).catch(err => {
            console.error(err)
        })
    }
    
    return(
        <div>
            <Typography>Welcome, {name} <br/></Typography>
            <br/>
            <Button onClick={auth.logout} variant={"contained"} type={"button"}>logout</Button>
            <hr/>
            <Accordion expandIcon={ExpandMoreIcon} sx={{bgcolor:"info.light", color:"#fff"}}>
                <AccordionSummary>
                    <Typography>Add department</Typography>
                </AccordionSummary>
                <AccordionDetails>
                    <Card sx={{padding:"15px"}}>
                        <form onSubmit={newDepartment}>
                            <Grid container item spacing={1} direction={"column"}>
                                <Grid item xs={3}>
                                    <TextField label={"Title"} type={"text"} value={depTitle} onChange={e => {setDeptitle(e.target.value)}}></TextField>
                                </Grid>
                                <Grid item xs={3}>
                                    <Button variant={"contained"} type={"submit"}>Add</Button>
                                </Grid>
                            </Grid>
                        </form>
                    </Card>
                </AccordionDetails>
            </Accordion>

            <br/>

            <Typography><h1>Listing all users</h1></Typography>
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableCell>Id</TableCell>
                        <TableCell>Name</TableCell>
                        <TableCell>Email</TableCell>
                        <TableCell>Password</TableCell>
                        <TableCell>Department</TableCell>
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
                                    <TableCell>{user.department.title}</TableCell>
                                </TableRow>
                            )
                        })}
                    </TableBody>
                </Table>
            </TableContainer>

            <br/>
            <Typography><h1>Listing all departments</h1></Typography>
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableCell>Id</TableCell>
                        <TableCell>Title</TableCell>
                    </TableHead>

                    <TableBody>
                        {department.map(dep => {
                            return(
                                <TableRow>
                                    <TableCell>{dep.id}</TableCell>
                                    <TableCell>{dep.title}</TableCell>
                                </TableRow>
                            )
                        })}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    )
}