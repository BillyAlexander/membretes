import React from 'react';
import PropTypes from 'prop-types';
import {
  Route,
  Redirect
} from "react-router-dom";

const PrivateRoute = ({ layout: Layout, component: Component,authenticated,  ...rest }) => {
  return (
    <Route
      {...rest}
      render={props =>
        authenticated ? (
          <Layout><Component {...rest} {...props} /></Layout>
        ) : (
            <Redirect
              to={{
                pathname: '/sign-in',
                state: { from: props.location }
              }}
            />
          )
      }
    />
  )
};

PrivateRoute.propTypes = {
  component: PropTypes.any.isRequired,
  layout: PropTypes.any.isRequired,
  authenticated: PropTypes.any.isRequired,
}

export default PrivateRoute
