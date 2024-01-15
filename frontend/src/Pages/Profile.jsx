import React, {useEffect, useState} from 'react';
import {
    Avatar,
    Breadcrumb,
    Button,
    Card,
    Flex,
    List, notification,
    Spin
} from "antd";
import {
    AlignCenterOutlined,  PhoneOutlined,
    UserOutlined,
    VideoCameraOutlined
} from "@ant-design/icons";
import axios from "axios";
import Loading from "../Components/Loading.jsx";
import ChangePassword from "../Components/ChangePassword.jsx";
import EditProfile from "./EditProfile.jsx";

const Profile = () => {
    const [editProfileVisible, setEditProfileVisible] = useState(false);

    const [userData, setUserData] = useState(null);
    const [changePasswordVisible, setChangePasswordVisible] = useState(false);

    const handleShowChangePassword = () => {
        setChangePasswordVisible(true);
    };

    const handleCancelChangePassword = () => {
        setChangePasswordVisible(false);
    };
    const handleShowEditProfile = () => {
        setEditProfileVisible(true);
    };

    const handleCancelEditProfile = () => {
        setEditProfileVisible(false);
    };

    const handleSaveEditProfile = async (values) => {

        try {
            const response = await axios.patch(
                import.meta.env.VITE_GATEWAY_URL + '/cinema/',
                {
                     name:values.name,
                    address:values.address,
                    contact:values.contact
                }, {
                    headers: {
                        Authorization: 'Bearer '+localStorage.getItem('access_token'),
                    },
                }
            );

            if (response.status === 200) {
                notification.success({
                    message: ' Successful',
                    description: 'You have successfully Updated Profile.',
                });
                localStorage.setItem("access_token",response.data['access_token'])
                setEditProfileVisible(false);
                setUserData((prevUserData) => ({
                    ...prevUserData,
                    name: values.name,
                    address:values.address,
                    contact:values.contact
                }));
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

            console.error('Error:', error);
        }




    };
    const handleSaveChangePassword = async (values) => {
        // Handle saving the new password,

        try {
          const response = await axios.patch(
                import.meta.env.VITE_GATEWAY_URL + '/cinema/password',
            {
                oldPassword: values.password,
                newPassword: values.newPassword,
                confirmPassword: values.confirmPassword
            }, {
                    headers: {
                        Authorization: 'Bearer '+localStorage.getItem('access_token'),
                    },
                }
            );

            if (response.status === 200) {
                notification.success({
                    message: ' Successful',
                    description: 'You have successfully change password.',
                });
                localStorage.setItem("access_token",response.data['access_token'])
                setChangePasswordVisible(false);
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

            console.error('Error:', error);
        }


    };

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const response = await axios.get(import.meta.env.VITE_GATEWAY_URL+'/cinema/',
                    {
                    headers: {
                        Authorization: 'Bearer '+localStorage.getItem('access_token'),
                    },
                }
                );

                setUserData(response.data);
            } catch (error) {
                console.error('Error fetching user details:', error);
            }
        };
        fetchUserData();
    }, []);
    const data = [
        { title: 'Cinema Name', description: userData?.name, icon: <VideoCameraOutlined /> },
        { title: 'Email', description: userData?.email, icon: <UserOutlined /> },
        { title: 'Address', description: userData?.address, icon: <AlignCenterOutlined /> },
        { title: 'Contact', description: userData?.contact, icon: <PhoneOutlined /> },

    ];

    if (!userData) {
        return <Loading/>;
    }


    return (
        <>
            <Breadcrumb
                style={{
                    margin: '16px 0',
                }}
            >
                <Breadcrumb.Item  className={'text-lg '}>Profile /</Breadcrumb.Item>
            </Breadcrumb>

            <Flex gap="middle" vertical >
                <Flex className={'  justify-center'}>
                     <div  style={{
                               width:'45%',
                            }}
                     >
                         <Card>
                             <div className="text-center text-lg m-2">
                                 <h1 className="text-3xl text-rose-600 font-sans font-medium mb-6 text-center"> My Profile</h1>
                             </div>
                             <div className="flex items-center justify-center mb-4">
                                 <img
                                     style={
                                         {borderRadius: "100%",
                                         borderColor:"#fb2056"
                                         }
                                     }
                                     src="../../public/cinemaLogo.png" alt="Logo"
                                     className="mx-auto rounded  border w-48"/>
                             </div>
                             <div className=" items-center justify-center ">
                                 <List
                                     itemLayout="vertical"
                                     dataSource={data}
                                     renderItem={(item) => (
                                         <List.Item>
                                             <List.Item.Meta

                                                 avatar={<Avatar style={{backgroundColor: 'transparent'}} size={32}
                                                                 icon={React.cloneElement(item.icon, {style: {color: '#fb2056'}})}/>}
                                                 title={<b>{item.title}</b>}
                                                 description={item.description}
                                             />
                                         </List.Item>
                                     )}
                                 />
                             </div>
                         </Card>

                     </div>
                        <Flex gap="middle" vertical  className="p-10 flex-col items-center justify-center"
                              style={{
                                  width: '35%',
                                  backgroundColor: "white",
                              }}>
                            <Button
                                type="primary"

                                className=" border w-60 rounded-lg  hover"
                                style={{backgroundColor: '#fb2056'}}
                                onClick={handleShowEditProfile}
                            >
                                Edit Profile
                            </Button>
                            <EditProfile
                                visible={editProfileVisible}
                                onCancel={handleCancelEditProfile}
                                onSave={handleSaveEditProfile}
                                userData={userData}
                            />
                            <Button
                                type="primary"
                                className="border w-60 rounded-lg hover"
                                style={{ backgroundColor: '#fb2056' }}
                                onClick={handleShowChangePassword}
                            >
                                Change Password
                            </Button>

                            <ChangePassword
                                visible={changePasswordVisible}
                                onCancel={handleCancelChangePassword}
                                onSave={handleSaveChangePassword}
                            />
                            <img src="../../public/profile.svg" alt="Logo" className="p-16"/>
                        </Flex>
                </Flex>
            </Flex>
        </>


    );
};

export default Profile;