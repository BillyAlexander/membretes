import React from 'react';
import { Route, Redirect } from 'react-router-dom';
import PropTypes from 'prop-types';

const RouteWithLayout = ({ layout: Layout, component: Component, authenticated,...rest }) => {
  return (
    <Route
      {...rest}
      render={matchProps =>
        !authenticated ?
          (
            <Layout>
              <Component {...matchProps} />
            </Layout>
          ) : (
            <Redirect
              to={{
                pathname: '/home',
                state: { from: matchProps.location }
              }}
            />
          )
      }
    />
  );

};

RouteWithLayout.propTypes = {
  component: PropTypes.any.isRequired,
  layout: PropTypes.any.isRequired,
  path: PropTypes.string,
  authenticated: PropTypes.any.isRequired,
};

export default RouteWithLayout;
