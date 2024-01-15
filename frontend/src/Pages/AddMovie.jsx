import React, {useEffect, useState} from 'react';
import {
    Button,
    Checkbox,
    Form,
    Input,
    InputNumber,
    message,
    notification,
    Select,
    Spin,
    TimePicker,
    Upload
} from "antd";
import axios from "axios";
import dayjs from "dayjs";
import {UploadOutlined} from "@ant-design/icons";
const { Option } = Select;
const onChange = (time, timeString) => {
    console.log(time, timeString);
};
const AddMovie = ({onCancel}) => {
    const [timeSlots, setTimeSlots] = useState([]);
    const [selectedTimeSlots, setSelectedTimeSlots] = useState([]);

    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const fetchTimeSlots = async () => {
            try {
                const response = await axios.get(import.meta.env.VITE_GATEWAY_URL + '/time/', {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                    },
                });
                setTimeSlots(response.data);
            } catch (error) {
                console.error('Error fetching time slots:', error);
            }
        };

        fetchTimeSlots();
    }, []);


    const onFinish = async (values) => {
        setLoading(true);
        const formData = new FormData();
        formData.append("banner", values.banner.file.originFileObj);
        formData.append('title', values.title);
        formData.append('language', values.language);
        formData.append('genre', values.genre);
        formData.append('seats', values.seats);
        formData.append('timeSlots', selectedTimeSlots);
        formData.append('duration', values.duration.format('HH:mm:ss').toString());

        try {
            const response = await axios.post(  import.meta.env.VITE_GATEWAY_URL + '/movie/',
                formData,
                {headers: {
                        'Authorization': `Bearer ${localStorage.getItem('access_token')}`,
                        'Content-Type': 'multipart/form-data',
                    },
                });

            if (response.status === 200) {

                notification.success({
                    message: 'Successful',
                    description: 'You have successfully added the movie.',
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
                description: error.response.data,
            });
        }
        setLoading(false);
        }


        const handleTimeSlotChange = (checkedValues) => {

            setSelectedTimeSlots(checkedValues);
        };
        return (
            <Spin spinning={loading}>
                <Form  layout="vertical"
                      onFinish={onFinish} encType="multipart/form-data"> 
                        <Form.Item   label="Title"   name="title" rules={[{required: true}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item  label="Language"   name="language" rules={[{required: true}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item  label="Genre"   name="genre" rules={[{required: true}]}>
                            <Input/>
                        </Form.Item>
                        <Form.Item     label="Duration" name="duration" rules={[{required: true}]}>
                            <TimePicker onChange={onChange} defaultOpenValue={dayjs('00:00:00', 'HH:mm:ss')}/>
                        </Form.Item>


                        <Form.Item    label="Banner" name="banner" valuePropName="banner"
                                   rules={[{required: true}]}>
                            <Upload listType="picture" maxCount={1}>
                                <Button icon={<UploadOutlined/>}>Upload</Button>
                            </Upload>
                        </Form.Item>

                        <Form.Item   label="Seats" name="seats" rules={[{required: true}]}>
                            <InputNumber min={0}/>
                        </Form.Item>
                        <Form.Item   label="Time Slots" name="timeSlots" rules={[{required: true}]}>
                            <Checkbox.Group onChange={handleTimeSlotChange}>
                                {timeSlots.map((slot) => (
                                    <Checkbox key={slot.slotId} value={slot.slotId}>
                                        {`${slot.day} `}
                                        {`${slot.starting} - ${slot.ending} `}
                                    </Checkbox>
                                ))}
                            </Checkbox.Group>
                        </Form.Item>

                        <div className={'flex    justify-evenly'}>

                            <Form.Item>
                                <Button
                                    className="border w-40 rounded-lg hover flex flex-col items-center justify-center   max-w-md"
                                >
                                    Cancel
                                </Button>
                            </Form.Item>
                            <Form.Item>
                                <Button
                                    className="border w-40   rounded-lg hover flex flex-col items-center justify-center   max-w-md"
                                    style={{backgroundColor: '#fb2056'}}
                                    type="primary"
                                    htmlType="submit"
                                >
                                    Add Movie
                                </Button>
                            </Form.Item>

                        </div>


                </Form>
            </Spin>
);
};

export default AddMovie;