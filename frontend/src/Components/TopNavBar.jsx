import React from 'react';
import {Layout, Menu, Button, notification} from 'antd';
import { LogoutOutlined } from '@ant-design/icons';
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {useDispatch} from "react-redux";
import {authActions} from "../store/auth-slice.js";

const { Header, Content } = Layout;

function HomePage() {
    const navigate = useNavigate();
    const dispatch=useDispatch();
    const handleLogout = async() => {
        try {
            console.error('logout:', localStorage.getItem("access_token"));
            const response = await axios.post(import.meta.env.VITE_GATEWAY_URL+'/auth/logout',
                {headers:{
                    "Authorization" : `Bearer ${localStorage.getItem("access_token")}`}
                });

            if (response.status === 200) {

                notification.success({
                    message: 'Logout',
                    description: 'You have successfully logged out.',
                });
                localStorage.setItem("access_token",null)
                localStorage.setItem("loginState",null)

                dispatch(authActions.logout());
                navigate('/');
                console.error('logout after storage:', localStorage.getItem("access_token"));
            }

        } catch (error) {
            notification.error({
                message: 'Error',
                description: 'An error occurred during Log out.',
            });
            console.error('Error:', error);
        }
    };

    return (

            <Header style={
                { minHeight: '10vh',}
            } className=" p-4 flex justify-between items-center">
                <div className="flex  ml-2 items-center space-x-4">
                    <img src="../../public/logo.png" alt="Logo"  className="mx-auto w-14  " />


                    <span className="text-white text-xl font-sans font-semibold"> S C O P E</span>
                </div>

                    <Button
                        type="primary"
                        htmlType="submit"
                        className="hover rounded-full"
                        onClick={handleLogout}
                        style={{  backgroundColor: '#fb2056' }}
                    >
                        Logout
                    </Button>




            </Header>

    );
}

export default HomePage;
