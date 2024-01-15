// EditProfile.jsx

import React from 'react';
import { Modal, Form, Input, Button } from 'antd';

const EditProfile = ({ visible, onCancel, onSave, userData }) => {
    const [form] = Form.useForm();

    const handleSave = async () => {
        try {
            const values = await form.validateFields();
            onSave(values);
        } catch (errorInfo) {
            console.log('Failed:', errorInfo);
        }
    };

    return (
        <Modal
            title={<h1 className="text-3xl text-rose-600 font-sans font-medium mb-6 text-center"> Edit Profile</h1>}
            visible={visible}
            onCancel={onCancel}
            footer={[
                <Button key="cancel" onClick={onCancel}>
                    Cancel
                </Button>,

                <Button key="save" style={{backgroundColor: '#fb2056'}} className="border w-30 rounded-lg "
                        type="primary" onClick={handleSave}>
                    Save
                </Button>,
            ]}
        >
            <div className="flex items-center justify-center mb-4">
                <img
                    style={
                        {
                            borderRadius: "100%",
                            borderColor: "#fb2056"
                        }
                    }
                    src="../../public/cinemaLogo.png" alt="Logo"
                    className="mx-auto rounded  border w-48"/>
            </div>
            <Form form={form} initialValues={userData}>

                <Form.Item
                    rules={[{required: true, message: 'Please Enter Name'}]}
                    label="Cinema Name " name="name">
                    <Input/>
                </Form.Item>
                <Form.Item
                    rules={[{required: true, message: 'Please enter Address'}]}
                    label="Cinema Address" name="address">
                    <Input/>
                </Form.Item>
                <Form.Item
                    rules={[{required: true, message: 'Please enter contact number'}]}
                    label="Cinema Contact   " name="contact">
                    <Input/>
                </Form.Item>

            </Form>
        </Modal>
    );
};

export default EditProfile;
