import React from 'react';
import { RouteWithLayout, PrivateRoute } from './app/routeWithLayout'
import { Minimal as MinimalLayout, Main as MainLayout } from './app/layouts/';
import { Switch, Redirect } from 'react-router-dom';
import AppHeader from './app/components/commons/AppHeader'
import { requestApi } from './app/core/index';
import {
  SignUp as SignUpView,
  Login as LoginView,
} from './app';
const authenticated = () => {
  return requestApi("nothing")
    .isAuthenticated({});
}

const Routes = () => {

  return (
    <Switch>
      <Redirect
        exact
        from="/"
        to={authenticated() ? "/home" : "/sign-in"}
      />

      <RouteWithLayout
        component={SignUpView}
        exact
        layout={MinimalLayout}
        authenticated={authenticated()}
        path="/sign-up"
      />
      <RouteWithLayout
        component={LoginView}
        exact
        layout={MinimalLayout}
        authenticated={authenticated()}
        path="/sign-in"
      />
      <PrivateRoute
        path="/home"
        exact
        layout={MainLayout}
        authenticated={authenticated()}
        component={AppHeader}
      />
      <PrivateRoute
        path="/user"
        exact
        layout={MainLayout}
        authenticated={authenticated()}
        component={AppHeader}
      />

    </Switch>
  )
};

export default Routes;
