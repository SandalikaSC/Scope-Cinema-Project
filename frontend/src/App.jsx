
import LoginPage from './Pages/LoginPage.jsx';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import SignupPage from "./Pages/SignupPage.jsx";
import Home from "./Pages/home.jsx";
import ForgetPw from "./Pages/forgetPw.jsx";
import PasswordResetPage from "./Pages/PasswordResetPage.jsx";
import ProtectedRoute from "./routes/ProtectedRoute.jsx";
import AddMovie from "./Pages/AddMovie.jsx";


function App() {

  return (
      <>
        <BrowserRouter>
          <Routes>
              <Route path="/" element={<LoginPage/>}/>

              <Route path="/signup" element={<SignupPage/>}/>
              <Route path="/forgetpw" element={<ForgetPw/>}/>
              <Route path="/reset-password/:token" element={<PasswordResetPage/>}/>
              <Route
                  path="/home"
                  element={<ProtectedRoute element={<Home />} />}
              />
              <Route
                  path="/AddMovie"
                  element={<ProtectedRoute element={<AddMovie />} />}
              />

          </Routes>
        </BrowserRouter>

      </>
  );
}

export default App;
