import React, { useState, useEffect } from 'react';
import {useNavigate, useParams} from 'react-router-dom';
import axios from 'axios';
import ResetPw from "./ResetPw.jsx";
import {notification} from "antd";

const PasswordResetPage = () => {
    const navigate = useNavigate();
    const { token } = useParams();
    const [tokenValidated, setTokenValidated] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            if (token) {
                try {
                    const response = await axios.get(import.meta.env.VITE_GATEWAY_URL+"/auth/reset-validate/"+token);
                    const isTokenValid = response.data;
                    setTokenValidated(isTokenValid);

                    if(!isTokenValid){
                        notification.error({
                            message: 'Validation Failed',
                            description: 'Try Again.',
                        });
                        navigate('/');
                    }
                } catch (error) {

                        notification.error({
                            message: 'Validation Failed',
                            description: 'Try Again.',
                        });
                        navigate('/');

                    console.error('Token validation failed:', error.message);
                }
            }
        };

        fetchData(); console.log(token);


    }, [token]);
    return (
        <div>
            {!tokenValidated ? (
                <div>
                    <p>Validating token...</p>
                </div>
            ) : (

                <ResetPw token={token} />
            )}
        </div>
    );
};

export default PasswordResetPage;
