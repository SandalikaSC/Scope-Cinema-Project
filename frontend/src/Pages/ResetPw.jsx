import {Button, Form, Input, notification} from "antd";
import {LockOutlined, UserOutlined} from "@ant-design/icons";
import axios from "axios";
import {authActions} from "../store/auth-slice.js";
import {useNavigate, useParams} from "react-router-dom";

const ResetPw = ({token}) => {
    const navigate = useNavigate();
    const onFinish = async (values) => {
        try {
           const response= await axios.post(import.meta.env.VITE_GATEWAY_URL+'/auth/reset', { token:token,password: values.password,confirmPassword: values.password });

           if (response.status === 200) {

                notification.success({
                    message: 'Successfully updated',
                    description: 'You have successfully reset the password.',
                });
                navigate('/');
            } else {
                notification.error({
                    message: 'Reset Failed',
                    description: `Try Again Later`,
                });
               navigate('/');
            }
        } catch (error) {
            notification.error({
                message: 'Reset Failed',
                description: `Something Went Wrong`,
            });
            navigate('/');
        }
    };
console.log("Token :   "+token)

    return (
        <div className="flex   items-center justify-center h-screen bg-gray-100 ">
            <div className="bg-white p-8 rounded-lg border border-gray-300 shadow-md w-96">

                <img src="../../public/logo.png" alt="Logo"  className="mb-6 mx-auto w-32" />
                <p className="text-gray-500 text-center mb-6"> S C O P E &nbsp; &nbsp;C I N E M A</p>
                <h1 className="text-3xl font-semibold mb-6 text-center">Reset Password</h1>
                <Form
                    name="resetForm"
                    initialValues={{ remember: true }}
                    onFinish={onFinish}
                    className={""}
                >

                    <Form.Item
                        name="password"
                        rules={[{ required: true, message: 'Please input your password!' },
                            {
                                pattern: /^(?=.*[A-Z])(?=.*\d).{8,}$/,
                                message: 'Password must contain at least 8 characters with at least one number and one capital letter!',
                            }]}
                    >
                        <Input.Password placeholder="Password"  prefix={<LockOutlined className="site-form-item-icon" />}/>
                    </Form.Item>

                    <Form.Item
                        name="repassword"
                        dependencies={['password']}
                        rules={[
                            { required: true, message: 'Please confirm your password!' },
                            ({ getFieldValue }) => ({
                                validator(_, value) {
                                    if (!value || getFieldValue('password') === value) {
                                        return Promise.resolve();
                                    }
                                    return Promise.reject(new Error('The passwords do not match!'));
                                },
                            }),
                        ]}
                    >
                        <Input.Password placeholder="Confirm Password" prefix={<LockOutlined className="site-form-item-icon" />} />
                    </Form.Item>





                    <Form.Item>
                        <Button
                            type="primary"
                            htmlType="submit"
                            className="w-full  hover"
                            style={{  backgroundColor: '#fb2056',borderRadius: '6px', boxShadow: 'none' }}
                        >
                            Confirm
                        </Button>
                    </Form.Item>


                </Form>
            </div>

        </div>
    );
};

export default ResetPw;