import React, {useState} from 'react';
import {Button, Form, Input, notification} from "antd";
import { UserOutlined} from "@ant-design/icons";
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";

const ForgetPw = () => {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const onFinish =async  (values) => {
        setLoading(true);
        try {
            const response = await axios.post(import.meta.env.VITE_GATEWAY_URL+'/auth/forget-password',{
                email: values.email
            });
            setLoading(false);
            if (response.status === 200) {
                notification.success({
                    message: 'Email Sent',
                    description: 'Check your inbox for verification.',
                });
                navigate('/');
            } else {
                notification.error({
                    message: 'Verification Failed',
                    description: ``,
                });
                navigate('/');
            }

        } catch (error) {
            notification.error({
                message: 'Error',
                description: 'An error occurred during verification.',
            });
            console.error('Error:', error);
        }
    };

    return (
        <div className="flex   items-center justify-center   h-screen bg-gray-100 ">
            <div className="bg-white p-8 rounded-lg border border-gray-300 shadow-md w-96">

                <img src="../../public/logo.png" alt="Logo"  className="mb-6 mx-auto w-32" />
                <p className="text-gray-500 text-center mb-6"> S C O P E &nbsp; &nbsp;C I N E M A </p>
                <h1 className="text-3xl font-semibold mb-6 text-center">Forget Password</h1>
                <Form
                    name="forgetpwForm"
                    initialValues={{ remember: true }}
                    onFinish={onFinish}
                    className={""}
                >
                    <Form.Item
                        name="email"
                        rules={[{ required: true, message: 'Please input your Email!' },{type:"email"}]}
                    >
                        <Input placeholder="Email" prefix={<UserOutlined className="site-form-item-icon" />}/>

                    </Form.Item>





                    <Form.Item>
                        <Button
                            type="primary"
                            htmlType="submit"
                            className="w-full  hover"
                            style={{  backgroundColor: '#fb2056',borderRadius: '6px', boxShadow: 'none' }}
                        >{loading ? <>Loading......</> : <>Submit</>}

                        </Button>
                    </Form.Item>
                    <Form.Item className="text-center">
                        <Link to="/">Go Back</Link>
                    </Form.Item>

                </Form>
            </div>

        </div>
    );
};

export default ForgetPw;