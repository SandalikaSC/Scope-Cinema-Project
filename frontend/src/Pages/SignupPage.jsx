// Signup.js
import React from 'react';
import {Form, Input, Button, notification} from 'antd';
import {LockOutlined, UserOutlined,VideoCameraOutlined, AlignCenterOutlined ,PhoneOutlined} from "@ant-design/icons";
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";

const Signup = () => {
    const navigate = useNavigate();
    const onFinish = async (values) => {
        try {
            const response = await axios.post(import.meta.env.VITE_GATEWAY_URL+'/auth/signup',{
                name: values.name,
                password : values.password,
                email: values.email,
                address: values.address,
                contact: values.contact,
            });

            if (response.status === 200) {
                notification.success({
                    message: 'Registration Successful',
                    description: 'You have successfully registered.',
                });
                navigate('/');
            } else {
                notification.error({
                    message: 'Registration Failed',
                    description: `An error occurred during registration`,
                });
            }

        } catch (error) {
            notification.error({
                message: 'Error',
                description: 'You have already signup.Please Login',
            });

            console.error('Error:', error);
        }
    };

    return (
         <div className="h-screen w-screen flex items-center justify-center " >
             <div className={"flex bg-white p-8 rounded-xl  border border-rose-500 shadow-md w-9/12  grid grid-cols-2"}>

                 <div className="p-10 flex-col">
                     <h1 className="text-3xl text-rose-600 font-sans font-medium mb-6 text-center">Hello !</h1>
                     <h1 className="text-2xl text-rose-600 font-sans font-medium mb-6 text-center">Welcome to Scope Cinema</h1>
                     <img src="../../public/movie.svg" alt="Logo"  className="mb-6 mx-auto " />
                 </div>

                 <div className="p-10">
                     <img src="../../public/logo.png" alt="Logo"  className="mb-6 mx-auto w-20" />
                     <p className="text-gray-500 text-lg text-center mb-6"> S C O P E &nbsp; &nbsp;C I N E M A</p>
                     <h1 className="text-3xl font-semibold mb-6 text-center">SignUp</h1>
                     <Form
                         name="signupForm"
                         initialValues={{ remember: true }}
                         onFinish={onFinish}
                         className={""}
                     >
                         <Form.Item
                             name="name"
                             rules={[{ required: true, message: 'Please input Cinema Name!' }]}
                         >
                             <Input placeholder="Cinema Name" prefix={<VideoCameraOutlined className="site-form-item-icon" />}/>

                         </Form.Item>
                         <Form.Item
                             name="address"
                             rules={[{ required: true, message: 'Please input Cinema Address!' }]}
                         >
                             <Input placeholder="Address" prefix={<AlignCenterOutlined className="site-form-item-icon" />}/>

                         </Form.Item>
                         <Form.Item
                             name="contact"
                             rules={[{ required: true, message: 'Please input Contact!' },{
                                 pattern: /^0[0-9]{9}$/,
                                 message: 'Invalid Number!',

                             },]}
                         >
                             <Input placeholder="Contact" prefix={<PhoneOutlined className="site-form-item-icon" />}/>

                         </Form.Item>
                         <Form.Item
                             name="email"
                             rules={[{ required: true, message: 'Please input your Email!' },{type:"email"}]}
                         >
                             <Input placeholder="Email" prefix={<UserOutlined className="site-form-item-icon" />}/>

                         </Form.Item>
                         <Form.Item
                             name="password"
                             rules={[{ required: true, message: 'Please input your password!' },
                                 {
                                 pattern: /^(?=.*[A-Z])(?=.*\d).{8,}$/,
                                 message: 'Password must contain at least 8 characters with at least one number and one capital letter!',
                             },]}
                         >
                             <Input.Password placeholder="Password"  prefix={<LockOutlined className="site-form-item-icon" />}/>
                         </Form.Item>



                         <Form.Item>
                             <Button
                                 type="primary"
                                 htmlType="submit"
                                 className="w-full border rounded-lg  hover"
                                 style={{  backgroundColor: '#fb2056' }}
                             >
                                 SignUp
                             </Button>
                         </Form.Item>
                         <Form.Item className="text-center">
                             <p>Already have an account?</p>
                             <Link to="/">Login</Link>
                         </Form.Item>

                     </Form>
                 </div>

             </div>


         </div>
    );
};

export default Signup;
