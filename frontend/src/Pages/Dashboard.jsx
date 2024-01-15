import React, {useEffect, useState} from 'react';
import {Card, Row, Col, Statistic, Typography, List, Avatar, Breadcrumb} from 'antd';
import { Pie } from 'react-chartjs-2';
import 'tailwindcss/tailwind.css';
import axios from "axios";
import Loading from "../Components/Loading.jsx";

const { Title } = Typography;
const Dashboard = () => {
    const [movieStatistics, setMovieStatistics] = useState([]);
    // const [movieStatistics, setMovieStatistics] = useState([]);
    //
    // const trendingMovies = [
    //     { name: 'Movie 1', image: '../../public/cinemaLogo.png' },
    //     { name: 'Movie 2', image: '../../public/cinemaLogo.png' },
    //     { name: 'Movie 3', image: '../../public/cinemaLogo.png' },
    //     { name: 'Movie 4', image: '../../public/cinemaLogo.png' },
    //     { name: 'Movie 5', image: '../../public/cinemaLogo.png ' },
    // ];

    // const movieStatistics = {
    //     totalMovies: 150,
    //     ticketsReservedToday: 120,
    //     // totalSeatsAvailable: 5000,
    //     seatsBooked: 3500,
    // };
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(
                    import.meta.env.VITE_GATEWAY_URL + `/movie/dashboard`,
                    {
                        headers: {
                            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                        },
                    }
                );

                if (response.status === 200) {
                    setMovieStatistics(response.data);
                } else {
                    setMovieStatistics([]);
                }
            } catch (error) {
                console.error('Error fetching seat data:', error);
            }
        };

fetchData();
    }, []);
    if (!setMovieStatistics) {
        return <Loading />;
    }

    return (
        <div className="p-6">
            <Breadcrumb
                style={{
                    margin: '16px 0',
                }}
            >
                <Breadcrumb.Item   className={'text-lg '}>Dashboard </Breadcrumb.Item>

            </Breadcrumb>
            <Row gutter={16}  className="mb-8 justify-evenly">
                <Col span={6}>
                    <Card>
                        <Statistic title="Total Movies" value={movieStatistics.totalMovies} />
                    </Card>
                </Col>
                <Col span={6}>
                    <Card>
                        <Statistic title="Tickets Reserved Today" value={movieStatistics.ticketsReservedToday} />
                    </Card>
                </Col>

                <Col span={6}>
                    <Card>
                        <Statistic title="Seats Booked" value={movieStatistics.seatsBooked} />
                        {/*<Statistic title="Seats Booked" value={0} />*/}
                    </Card>
                </Col>
            </Row>
            <Row gutter={16} className="mb-8">
                <Col span={24}>
                    <h1 className="text-2xl text-rose-600 font-sans font-medium mb-6"> Movies</h1>

            </Col>
            <Col span={24}>
                <List
                        grid={{ gutter: 16, column: 5 }}
                        dataSource={movieStatistics.trendingMovieDtoList}
                        renderItem={(item) => (
                            <List.Item>
                                <Card className={'  '}
                                    hoverable
                                    cover={<img style={{ width: 350, height: 330 }} alt={item.title} src={import.meta.env.VITE_IMAGES_URL +item.banner} />}
                                >
                                    <Card.Meta title={item.title} />
                                </Card>
                            </List.Item>
                        )}
                    />
                </Col>
            </Row>

        </div>
    );
};

export default Dashboard;