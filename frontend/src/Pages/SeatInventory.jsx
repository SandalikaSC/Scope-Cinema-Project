import React, {useEffect, useState} from 'react';
import {Breadcrumb, Button, Input, Modal, Table} from "antd";
import axios from "axios";
import EditSeatInventory from "../Components/EditSeatInventory.jsx";


const SeatInventory = () => {
    const [movieList, setMovieList] = useState([]);
    const [modalVisible, setModalVisible] = useState(false);
    const [selectedMovieId, setSelectedMovieId] = useState(null);


    const columns = [
        { title: 'Movie ID', dataIndex: 'movieId', key: 'movieId' },
        { title: 'Movie Name', dataIndex: 'title', key: 'title' },
        { title: 'Duration', dataIndex: 'duration', key: 'duration' },
        { title: 'Genre', dataIndex: 'genre', key: 'genre' },
        {
            title: 'Action',
            dataIndex: 'movieId',
            key: 'action',
            render: (id) => <Button onClick={() => handleEditSeat(id)}>Edit Seat</Button>,
        },
    ];
    useEffect( () => {
        const fetchData = async () => {
            try {
                const response = await axios.get(import.meta.env.VITE_GATEWAY_URL + '/movie/', {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                    },
                });
                if (response.status === 200) {
                    setMovieList(response.data);
                } else {
                    setMovieList(null);
                }
            } catch (error) {
                console.error('Error fetching movies:', error);
            }
        };

        fetchData();

    }, []);

    const handleEditSeat = (movieId) => {
        setSelectedMovieId(movieId);

        setModalVisible(true);

    };
    const modelVisibleCancel = () => {
        setModalVisible(false)
    }

    return (<>

            <Breadcrumb
                style={{
                    margin: '16px 0',
                }}
            >
                <Breadcrumb.Item className={'text-lg '}>Seat Inventory /</Breadcrumb.Item>
            </Breadcrumb>
            <Input
                placeholder="Search for movies..."

            />
        <div className="">
            <Table dataSource={movieList} columns={columns} />
            <Modal
                title={<h3 className="text-3xl text-rose-600 font-sans font-medium mb-6 text-center"> Edit Seat Inventory</h3>}
                visible={modalVisible}
                onCancel={() => setModalVisible(false)}
                footer={null}
            > <EditSeatInventory movieId={selectedMovieId} onCancel={modelVisibleCancel}/>

            </Modal>
        </div>
        </>
    );
};

export default SeatInventory;