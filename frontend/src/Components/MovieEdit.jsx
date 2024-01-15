// EditMovie.jsx
import React, { useState, useEffect } from 'react';
import {Modal, Form, Input, TimePicker, Upload, Button, notification} from 'antd';
import {EditOutlined, DeleteFilled, UploadOutlined} from '@ant-design/icons';
import axios from 'axios';
import moment from 'moment';

const MovieEdit = ({ movie, onCancel, onEdit }) => {
    const [form] = Form.useForm();
    const [bannerName, setbannerName] = useState('');


    useEffect(() => {
        form.setFieldsValue({
            title: movie.title,
            language: movie.language,
            genre: movie.genre,
            duration: moment(movie.duration, 'HH:mm:ss'),
            banner:import.meta.env.VITE_IMAGES_URL +movie.banner
            // Add other fields as needed
        });
setbannerName(movie.banner);
    }, [form, movie]);

    const handleEdit = async () => {
        try {
            const values = await form.validateFields();
            //
            //
            const formData = new FormData();
            console.log("banner: "+values.banner.file.originFileObj===undefined)
            formData.append("banner", values.banner.file.originFileObj || new File([]));



            formData.append('title', values.title);
            formData.append('language', values.language);
            formData.append('genre', values.genre);
            formData.append('movieId', movie.movieId);
            formData.append('duration', values.duration.format('HH:mm:ss').toString());

            try {
                const response = await axios.patch(  import.meta.env.VITE_GATEWAY_URL + '/movie/',
                    formData,
                    {headers: {
                            'Authorization': `Bearer ${localStorage.getItem('access_token')}`,
                            'Content-Type': 'multipart/form-data',
                        },
                    });

                if (response.status === 200) {
                    notification.success({
                        message: 'Successful',
                        description: 'You have successfully updated the movie.',
                    });
                    onCancel();
                } else {
                    notification.error({
                        message: response.statusText,
                        description: response.data || `An error occurred `,
                    });
                }
            } catch (error) {
                notification.error({
                    message: 'Error',
                    description: error.data,
                });
            }

            console.log('Edited Movie:', values);

        } catch (errorInfo) {
            console.log('Failed:', errorInfo);
        }
    };

    const handleBannerChange = () => {
setbannerName('');
    };
    return (
        <Modal
            title={<h1 className="text-3xl text-rose-600 font-sans font-medium mb-6 text-center">Edit Movie</h1>}
            visible={true}
            onCancel={onCancel}
            footer={[
                <Button key="cancel" onClick={onCancel}>
                    Cancel
                </Button>,
                <Button style={{backgroundColor: '#fb2056'}} className="border w-30 rounded-lg " key="edit" type="primary" onClick={handleEdit}>
                    Edit
                </Button>,
            ]}
        >
            <Form form={form} layout="vertical" name="editMovieForm" encType="multipart/form-data">
                <Form.Item
                    name="title"
                    label="Title"
                    rules={[{required: true, message: 'Title is required'}]}
                >
                    <Input/>
                </Form.Item>
                <Form.Item
                    name="language"
                    label="Language"
                    rules={[{required: true, message: 'Language is required'}]}
                >
                    <Input/>
                </Form.Item>
                <Form.Item
                    name="genre"
                    label="Genre"
                    rules={[{required: true, message: 'Genre is required'}]}
                >
                    <Input/>
                </Form.Item>
                <Form.Item
                    name="duration"
                    label="Duration"
                    rules={[{required: true, message: 'Duration is required'}]}
                >
                    <TimePicker format="HH:mm:ss"/>
                </Form.Item>
                <Form.Item className={'p-2'} label="Banner" name="banner" valuePropName="banner"
                           rules={[{required: true}]}>
                    <Upload listType="picture" maxCount={1} onChange={handleBannerChange}>
                        <Button icon={<UploadOutlined/>}>Upload</Button>

                    </Upload>

                </Form.Item>
                <div style={{marginTop: 8}}>
                    <strong>{bannerName}</strong>
                </div>

            </Form>
        </Modal>
    );
};

export default MovieEdit;
