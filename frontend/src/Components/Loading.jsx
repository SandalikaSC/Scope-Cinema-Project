import React from 'react';
import {Flex, Spin} from "antd";

const Loading = () => {
    return (
        <Flex
            gap="middle"
            vertical
            className="flex items-center justify-center"
            style={{ height: '80vh' }}
        >
            <Spin size="large" />
            <div>Loading...</div>
        </Flex>
    );
};

export default Loading;