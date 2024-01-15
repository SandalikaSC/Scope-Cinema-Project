import React from 'react';
import { useSelector } from 'react-redux';
import { Navigate } from 'react-router-dom';

const ProtectedRoutes = ({ element }) =>
{
    const isLoggedIn = useSelector((state) => state.isLoggedIn);
    const state=localStorage.getItem("loginState")
    return state==="true" ? element : <Navigate to="/" replace />;
};

export default ProtectedRoutes;