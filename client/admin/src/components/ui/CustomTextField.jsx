import { TextField } from "@mui/material";
import React from "react";

const CustomTextField = (props) => {
    return (
        <TextField sx={{ minWidth: "300px" }}  {...props}/>
    )
}

export default CustomTextField;