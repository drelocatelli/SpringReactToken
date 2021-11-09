import React from "react";
import {Button, Grid, TextField} from "@mui/material";

export const Home = () => {

    return(
        <table align={"center"}>
            <tr>
                <td><h3>Welcome to the jungle</h3></td>
            </tr>
            <tr>
                <td>
                    <TextField label={"Email"} type={"email"}></TextField>
                </td>
            </tr>
            <tr>
                <td><TextField label={"Password"} type={"password"}></TextField></td>
            </tr>
            <tr>
                <td>
                    <Button variant={"contained"} type={"submit"}>Login</Button>
                </td>
            </tr>
            <tr>
                <td><a href={"/register"}>Need new account</a></td>
            </tr>

        </table>
    )
    
}