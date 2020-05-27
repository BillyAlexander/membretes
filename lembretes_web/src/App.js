import React from 'react';
import { Router } from 'react-router-dom';
import { createBrowserHistory } from 'history';
import Routes from './Routes';
import { ToastContainer } from 'react-toastify';

const browserHistory = createBrowserHistory();

function App() {
  return (

    <Router history={browserHistory}>
      <Routes />
      <ToastContainer limit={5}/>
    </Router>
  );
}

export default App;
