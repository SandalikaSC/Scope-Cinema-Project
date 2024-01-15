import React, { useState } from 'react';
import {
    MenuFoldOutlined,
    MenuUnfoldOutlined,
    UploadOutlined,
    UserOutlined,
    VideoCameraOutlined,
} from '@ant-design/icons';
import { Layout, Menu, Button, theme } from 'antd';
const { Header, Sider, Content } = Layout;
const SideBar = () => {
    const [collapsed, setCollapsed] = useState(false);
    return (

            <Sider trigger={null} collapsible collapsed={collapsed}>
                <div className="demo-logo-vertical" />
                <Menu
                    theme="dark"
                    mode="inline"
                    defaultSelectedKeys={['1']}
                    items={[
                        {
                            key: '1',
                            icon: <UserOutlined />,
                            label: 'Home',
                        },
                        {
                            key: '2',
                            icon: <VideoCameraOutlined />,
                            label: 'Movies',
                        },
                        {
                            key: '3',
                            icon: <UploadOutlined />,
                            label: 'Reservations',
                        },
                    ]}
                />
            </Sider>


    );
};
export default SideBar;
