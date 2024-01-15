import React, { useState, useEffect } from 'react';
import {
    Avatar,
    Breadcrumb,
    Button,
    Card,
    Col,
    Form,
    Input, List,
    message,
    Modal,
    notification,
    Row,
    TimePicker,
    Upload
} from 'antd';
import {
    DeleteFilled,
    DeleteOutlined,
    EditOutlined,
    EllipsisOutlined,
    PlusCircleOutlined,
    SearchOutlined,
    SettingOutlined, UploadOutlined
} from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';
import AddMovie from './AddMovie.jsx';
import Meta from "antd/es/card/Meta.js";
import axios from "axios";
import dayjs from "dayjs";
import {flushSync} from "react-dom";
import MovieEdit from "../Components/MovieEdit.jsx";

const Movie = () => {
    const navigate = useNavigate();
    const [searchQuery, setSearchQuery] = useState('');
    const [movies, setMovies] = useState([]);
    const [filteredMovies, setFilteredMovies] = useState([]);
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [selectedMovie, setSelectedMovie] = useState(null);



    const handleEdit = (movie) => {
        setSelectedMovie(movie);
    };

    const handleEditCancel = () => {
        setSelectedMovie(null);

        fetchMovies();
    };



    const fetchMovies = async () => {
        try {
            const response = await axios.get(import.meta.env.VITE_GATEWAY_URL + '/movie/', {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                },
            });
            if(response.status===200){

                setMovies( response.data);
                setFilteredMovies(response.data);
            }else{

                setMovies(null);
                setFilteredMovies(null);
            }
        } catch (error) {
            console.error('Error fetching movies:', error);
        }
    };

    useEffect(() => {
        fetchMovies();
    }, []);

    const showModal = () => {
        setIsModalVisible(true);
    };

    const handleCancel = () => {
        fetchMovies();
        setIsModalVisible(false);
    };

    // Function to handle search
    const handleSearch = (value) => {
        const filteredMovies = movies.filter((movie) =>
            movie.title.toLowerCase().includes(value.toLowerCase())
        );

        setSearchQuery(value);
        setFilteredMovies(filteredMovies);
    };

    const handleAddMovie = () => {
        console.log(localStorage.getItem('access_token'));
    };


    const moviesToRender = filteredMovies.length > 0 ? filteredMovies : movies;


    return (
        <div>
            <Breadcrumb
                style={{
                    margin: '16px 0',
                }}
            >
                <Breadcrumb.Item className={'text-lg '}>Movies /</Breadcrumb.Item>
            </Breadcrumb>

            <div className={'p-10'} style={{ display: 'flex', alignItems: 'center' }}>
                <Input
                    placeholder="Search for movies..."
                    value={searchQuery}
                    onChange={(e) => handleSearch(e.target.value)}
                />

                <Button
                    icon={<PlusCircleOutlined />}
                    className=" border w-60 rounded-lg  hover"
                    type="primary"
                    onClick={showModal}
                    style={{ marginLeft: 16, backgroundColor: '#fb2056' }}
                >
                    Add Movie
                </Button>
                <Modal
                    className={'w-full'}
                    title={<h1 className="text-3xl text-rose-600 font-sans font-medium mb-6 text-center"> New Movie</h1>}
                    visible={isModalVisible}
                    onCancel={handleCancel}
                    footer={null}
                >
                    <AddMovie onCancel={handleCancel}/>
                </Modal>
            </div>

            {selectedMovie && (
                <MovieEdit movie={selectedMovie} onCancel={handleEditCancel} />
            )}


                <List
                    grid={{ gutter: 16, column:5}}
                    dataSource={moviesToRender}
                    renderItem={(movie) => (
                        <List.Item>
                            <Card
                                hoverable
                                actions={[
                                    // <DeleteFilled key="delete" />,
                                    <EditOutlined key="edit" onClick={() => handleEdit(movie)} />,
                                ]}
                                cover={
                                    <img
                                        style={{ width: 350, height: 330 }}
                                        alt={movie.title}
                                        src={import.meta.env.VITE_IMAGES_URL + movie.banner}
                                    />
                                }
                            >
                                <Meta title={movie.title} description={movie.genre} />
                            </Card>
                        </List.Item>
                    )}
                />



        </div>
);
};

export default Movie;
