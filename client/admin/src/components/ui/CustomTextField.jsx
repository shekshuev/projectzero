import { TextField } from "@mui/material";
import React from "react";

const CustomTextField = (props) => {
    return (
        <TextField sx={{ width: "300px" }}  {...props}/>
    )
}

export default CustomTextField;