import React, { useState, useEffect } from "react";
import LoginLight from "./Light/LoginLight.jsx";
import LoginDark from "./Dark/LoginDark.jsx";
const Login = () => {
    const [darkMode, setDarkMode] = useState(false);
    useEffect(() => {
        const prefersDarkScheme = window.matchMedia("(prefers-color-scheme: dark)");
        setDarkMode(prefersDarkScheme.matches);
        const handleChange = (e) => {
            setDarkMode(e.matches);
        };
        prefersDarkScheme.addEventListener("change", handleChange);
        return () => {
            prefersDarkScheme.removeEventListener("change", handleChange);
        };
    }, []);

    return (
        <div>
            {darkMode ? <LoginDark /> : <LoginLight />}
        </div>
    );
};
export default Login;