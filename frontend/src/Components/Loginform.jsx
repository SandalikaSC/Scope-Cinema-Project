import React from 'react';
import {Button, Form, Input} from "antd";
import {LockOutlined, UserOutlined} from "@ant-design/icons";
import {Link} from "react-router-dom";

const Loginform = () => {
    return (
        <>
            <div className="bg-white p-8 rounded-lg border border-gray-300 shadow-md w-96">

                <img src="../../public/logo.png" alt="Logo"  className="mb-6 mx-auto w-32" />
                <p className="text-gray-500 text-center mb-6"> S C O P E </p>
                <h1 className="text-3xl font-semibold mb-6 text-center">Login</h1>
                <Form
                    name="loginForm"
                    initialValues={{ remember: true }}
                    onFinish={onFinish}
                    className={""}
                >
                    <Form.Item
                        name="username"
                        rules={[{ required: true, message: 'Please input your username!' }]}
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


                        <a href="/forgot-password" >
                            Forget Password?
                        </a>
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
                        <Link to="/home">SignUp</Link>
                    </Form.Item>

                </Form>
            </div>

        </>
    );
};

export default Loginform;