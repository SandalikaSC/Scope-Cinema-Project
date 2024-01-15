
import React, {useState} from 'react';

import { Form, Input, Button } from 'antd';

import { UserOutlined, LockOutlined } from '@ant-design/icons';
import {useNavigate} from "react-router-dom";
import { Link} from "react-router-dom";
import axios from "axios";
import {notification} from "antd";
import {useDispatch, useSelector} from "react-redux";
import {authActions} from "../store/auth-slice.js";

function LoginPage(){
    const navigate = useNavigate();
const dispatch=useDispatch();
    const onFinish = async (values) => {
        try {
            const response = await axios.post(import.meta.env.VITE_GATEWAY_URL+'/auth/authenticate',{
                email: values.username,
                password: values.password,
            });

            if (response.status === 200) {
                const accessToken = response.data['access_token'];
                const username = response.data['userName'];
                dispatch(authActions.login());
                console.error('login came in:', accessToken);
                notification.success({
                    message: 'Authentication Successful',
                    description: 'You have successfully logged in.',
                });
                localStorage.setItem("access_token",response.data['access_token'])
                localStorage.setItem("userName",response.data['userName'])
                localStorage.setItem("loginState","true");
                console.error('login storage:', localStorage.getItem("access_token"));

                navigate('/home');
            } else {
                notification.error({
                    message: 'Authentication Failed',
                    description: 'An error occurred during authentication.',
                });
            }

        } catch (error) {
            notification.error({
                message: 'Error',
                description: `Try again with your credentials`,
            });
            console.error('Error:', error);
        }
        console.log(login);
    };

    return (
        <>
            <div className="flex   items-center justify-center h-screen bg-gray-100 ">

                <div className="bg-white p-8 rounded-lg border border-gray-300 shadow-md w-96">

                    <img src="../../public/logo.png" alt="Logo"  className="mb-6 mx-auto w-32" />
                    <p className="text-gray-500 text-center mb-6"> S C O P E &nbsp; &nbsp;C I N E M A</p>
                    <h1 className="text-3xl font-semibold mb-6 text-center">Login</h1>
                    <Form
                        name="loginForm"
                        initialValues={{ remember: true }}
                        onFinish={onFinish}
                        className={""}
                    >
                        <Form.Item
                            name="username"
                            rules={[{ required: true, message: 'Please input your username!' },{type:"email"}]}
                        >
                            <Input placeholder="Username" prefix={<UserOutlined className="site-form-item-icon" />}/>

                        </Form.Item>

                        <Form.Item
                            name="password"
                            rules={[{ required: true, message: 'Please input your password!' }]}
                        >
                            <Input.Password placeholder="Password"  prefix={<LockOutlined className="site-form-item-icon" />}/>
                        </Form.Item>

                        <Form.Item className={"text-center"}>


                            <Link to="/forgetpw" >
                                Forget Password?
                            </Link>
                        </Form.Item>


                        <Form.Item>
                            <Button
                                type="primary"
                                htmlType="submit"
                                className="w-full  hover"
                                style={{  backgroundColor: '#fb2056',borderRadius: '6px', boxShadow: 'none' }}
                            >
                                Log in
                            </Button>
                        </Form.Item>
                        <Form.Item className="text-center">
                            <p>Don't have an account?</p>
                            <Link to="/signup">SignUp</Link>
                        </Form.Item>

                    </Form>
                </div>

            </div>

        </>

    );
};

export default LoginPage;
