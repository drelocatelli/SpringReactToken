import React from 'react'
import {Button, TextField} from "@mui/material";

export const Register = () => {
    return(
        <div>
            <table align={"center"}>
                <tr>
                    <td><h3>Register to the jungle</h3></td>
                </tr>
                <tr>
                    <td>
                        <TextField label={"Name"} type={"text"}></TextField>
                    </td>
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
                        <Button variant={"contained"} type={"submit"}>Confirm</Button>
                    </td>
                </tr>

            </table>
        </div>
    )
}