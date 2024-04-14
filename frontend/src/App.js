import React, { useState, useEffect } from 'react';
import './App.css';
import LoginRusLight from './pages/Light/LoginLight.jsx'
import LoginRusDark from './pages/Dark/LoginDark.jsx'
import { AuthContext } from './context/index.js';
import Login from './pages/Login.jsx';
import AppRouter from './components/AppRouter.jsx';

function App() {
  const [isAuth, setIsAuth] = useState(false);
  useEffect(() => {
    if (localStorage.getItem('auth')) {
      setIsAuth(true)
    }
  }, [])
  return (
    <AuthContext.Provider value={{
      isAuth,
      setIsAuth,
    }}>
      <AppRouter />
    </AuthContext.Provider>
  );
}

export default App;
