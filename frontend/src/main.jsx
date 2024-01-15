import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import {ConfigProvider} from "antd";
import { Provider } from "react-redux";
import store from "./store/store.js";


ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
      <ConfigProvider theme={
          {
              token:{
                  colorPrimary:"#fb2056",
                  fontFamily:"Roboto"
              }
          }
      }>
          <Provider store={store}>
              <App/>
          </Provider>


      </ConfigProvider>

  </React.StrictMode>,
)
