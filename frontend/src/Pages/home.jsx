import React, { useState } from 'react';
import {
    DesktopOutlined,
    FileOutlined, InsertRowAboveOutlined,
    PieChartOutlined,
    TeamOutlined,
    UserOutlined, VideoCameraOutlined,
} from '@ant-design/icons';
import { Breadcrumb, Layout, Menu, theme } from 'antd';
import SideBar from "../Components/SideBar.jsx";
import TopNavBar from "../Components/TopNavBar.jsx";
import Dashboard from "./Dashboard.jsx";
import Movie from "./Movie.jsx";
import SeatInventory from "./SeatInventory.jsx";
import Profile from "./Profile.jsx";
import {Router} from "react-router-dom";
const { Header, Content, Footer, Sider } = Layout;
function getItem(label, key, icon, children) {
    return {
        key,
        icon,
        children,
        label,
    };
}
const items = [
    getItem('DashBoard', '1', <PieChartOutlined />),
    getItem('Movies', '2', <VideoCameraOutlined />),
    getItem('Seat Inventory', '3',<InsertRowAboveOutlined />),
    getItem('Profile', '4', <TeamOutlined />),
];
const Home = () => {
    const [collapsed, setCollapsed] = useState(false);
    const {
        token: {borderRadiusLG },
    } = theme.useToken();
    const [activePage, setActivePage] = useState('1');
    const renderActivePage = () => {
        switch (activePage) {
            case '1':
                return <Dashboard />;
            case '2':
                return <Movie />;
            case '3':
                return <SeatInventory />;
            case '4':
                return <Profile />;
            default:
                return null;
        }
    };
    return (
        <>

        <TopNavBar/>
        <Layout
            style={{
                minHeight: '100vh',

            }}
        >
            <Sider  collapsible collapsed={collapsed} onCollapse={(value) => setCollapsed(value)}>
                <div
                    style={
                        {
                            minHeight: '5vh',
                            padding:'5vh'

                        }
                    }
                    className="demo-logo-vertical">
                    <img
                        style={
                        {
                            borderRadius: "100%",
                        }
                        }
                        src="../../public/cinemaLogo.png" alt="Logo" className="mx-auto rounded "/>


                </div>
                <Menu
                    theme="dark"
                    defaultSelectedKeys={['1']}
                    mode="inline"
                    items={items}
                    onSelect={(item) => setActivePage(item.key)}
                />
            </Sider>
            <Layout style={{ minHeight: '100vh' }}>
                <Content className="p-4">
                    <div>
                        {renderActivePage()}
                    </div>
                </Content>
                <Footer
                    style={{
                        textAlign: 'center',
                    }}
                >
                    Scope Cinema ©2023
                </Footer>
            </Layout>
        </Layout>
        </>
    );
};
export default Home;
