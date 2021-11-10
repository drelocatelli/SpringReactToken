import React from 'react'
import {Button, TextField, Grid} from "@mui/material";

export const Register = () => {
    return(
        <Grid container spacing={2}  direction="column" alignItems="center" justifyContent="center">
            <Grid item xs={3}>
                <h3>Register to the jungle</h3> 
            </Grid>
            <Grid container spacing={1} direction={"column"}  direction="column" alignItems="center" justifyContent="center">
                <Grid item xs={3}>
                    <TextField label={"Name"} type={"text"}></TextField>
                </Grid>
                <Grid item xs={3}>
                    <TextField label={"Email"} type={"email"}></TextField>
                </Grid>
                <Grid item xs={3}>
                    <TextField label={"Password"} type={"password"}></TextField>
                </Grid>
                <Grid item xs={3}>
                    <Button variant={"contained"} type={"submit"}>Confirm</Button>
                </Grid>
            </Grid>
        </Grid>
    )
}