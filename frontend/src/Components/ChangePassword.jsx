import React from 'react';
import {Button, Form, Input, Modal} from "antd";
import {LockOutlined} from "@ant-design/icons";

const ChangePassword = ({ visible, onCancel, onSave }) => {
    const [form] = Form.useForm();

    const handleSave = () => {
        form.validateFields().then((values) => {
            onSave(values);
            form.resetFields();
        });
    };

    return (
        <Modal
            title={<h1 className="text-3xl text-rose-600 font-sans font-medium mb-6 text-center"> Change Password</h1>}
            visible={visible}
            onCancel={onCancel}
            footer={[
                <Button key="cancel" className="border w-30  rounded-lg " onClick={onCancel}>
                    Cancel
                </Button>,

                <Button key="save"  style={{ backgroundColor: '#fb2056' }}  className="border w-30 rounded-lg " type="primary"  onClick={handleSave}>
                    Save
                </Button>,
            ]}
        >
            <Form form={form}>
                <h3>Current Password</h3>
                <Form.Item
                    name="password"

                    rules={[{required: true, message: 'Please enter your current password'}]}
                >
                   <Input.Password placeholder="Current Password"
                                                            prefix={<LockOutlined className="site-form-item-icon"/>}/>
                </Form.Item>
                <h3>New Password</h3>
                <Form.Item
                    name="newPassword"

                    rules={[{required: true, message: 'Please enter your new password'}]}
                >
                    <Input.Password placeholder="New Password"
                                                            prefix={<LockOutlined className="site-form-item-icon"/>}/>
                </Form.Item>
                <h3>Confirm Password</h3>
                <Form.Item
                    name="confirmPassword"

                    dependencies={['newPassword']}
                    hasFeedback
                    rules={[
                        {required: true, message: 'Please confirm your new password'},
                        ({getFieldValue}) => ({
                            validator(_, value) {
                                if (!value || getFieldValue('newPassword') === value) {
                                    return Promise.resolve();
                                }
                                return Promise.reject(new Error('The two passwords do not match'));
                            },
                        }),
                    ]}
                >
                   <Input.Password placeholder="Confirm Password"
                                   prefix={<LockOutlined className="site-form-item-icon"/>}/>
                </Form.Item>
            </Form>
        </Modal>
    );
};
export default ChangePassword;