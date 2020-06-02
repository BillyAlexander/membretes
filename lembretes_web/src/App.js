import React from 'react';
import { Router } from 'react-router-dom';
import { createBrowserHistory } from 'history';
import Routes from './Routes';
import { ToastContainer } from 'react-toastify';
// import { AuthDataProvider } from './app/core/index'

const browserHistory = createBrowserHistory();

function App() {
  return (
    /* // <AuthDataProvider> */
      <Router history={browserHistory}>
        <Routes />
        <ToastContainer limit={5} />
      </Router>
    /* // </AuthDataProvider> */
  );
}

export default App;
