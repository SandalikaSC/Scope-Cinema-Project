import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Loading from './Loading.jsx';
import {Form, InputNumber, notification} from 'antd';

const EditSeatInventory = ({ movieId,onCancel }) => {
    const [seatData, setSeatData] = useState([]);
    const [form] = Form.useForm();

    const handleSubmit = async () => {


        try {
            const values = await form.validateFields();



            const seatCountData = [];

            // Extract movie time ID and seat count for each time slot
            seatData.forEach((time) => {
                const movieTimeId=time.movieTimeId;
                const slotId = time.slotId;
                const seats = values[slotId];
                seatCountData.push({ movieTimeId, seats });
            });

            console.log(seatCountData);

            const response = await axios.patch(
                import.meta.env.VITE_GATEWAY_URL + '/inventory/',
                seatCountData,
                {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                        'Content-Type': 'application/json',
                    },
                }
            );
            if (response.status === 200) {
                notification.success({
                    message: ' Successful',
                    description: 'You have successfully Updated Seat Inventory.',
                });

                onCancel();
            } else {
                notification.error({
                    message: response.statusText,
                    description: response.data || `An error occurred `,
                });
            }
            // notification.success({
            //     message: ' Successful',
            //     description: 'You have successfully Updated Seat Inventory.',
            // });
            console.log(response.data);



        } catch (error) {
            console.error('Validation failed:', error);
        }
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(
                    import.meta.env.VITE_GATEWAY_URL + `/inventory/${movieId}`,
                    {
                        headers: {
                            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                        },
                    }
                );
                if (response.status === 200) {
                    setSeatData(response.data);
                } else {
                    setSeatData([]);
                }
            } catch (error) {
                console.error('Error fetching seat data:', error);
            }
        };

        fetchData();
    }, [movieId]);

    if (!seatData) {
        return <Loading />;
    }
    // const validateSeatCount = (_, value) => {
    //     const initialValue = time.noOfSeats;
    //
    //     if (value !== initialValue) {
    //         return Promise.reject();
    //     }
    //
    //     return Promise.resolve();
    // };
    return (
        <>
            <Form form={form} className={' flex flex-col items-center justify-center'}>
                {seatData.map((time) => (
                    <div key={time.slotId}>
                        <h3 className="text-l text-black-400 font-sans font-medium pb-3">
                            Time Slot Number {time.slotId}
                        </h3>
                        <p className={'pb-5'}>{time.starting} - {time.ending} on {time.day}</p>

                        <Form.Item label="Seat Count" name={time.slotId}
                                   initialValue={time.noOfSeats}
                                   rules={[
                                       // { validator: validateSeatCount ,message:'Enter the changing seats'},
                                       { required: true, message: 'Please enter seat count' }
                                       ]}>
                            <InputNumber min={1} placeholder="Enter seat count" />
                        </Form.Item>
                    </div>
                ))}

                <Form.Item>
                    <button
                        type="button" // Changed to 'button' to prevent form submission
                        className="border w-60 rounded-lg hover"
                        style={{ backgroundColor: '#fb2056' }}
                        onClick={handleSubmit}
                    >
                        Edit
                    </button>
                </Form.Item>
            </Form>
        </>
    );
};

export default EditSeatInventory;
