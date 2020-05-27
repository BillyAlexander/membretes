import React from 'react';
import { RouteWithLayout } from './app/routeWithLayout'
import { Minimal as MinimalLayout } from './app/layouts/';
import { Switch, Redirect } from 'react-router-dom';

import {
  SignUp as SignUpView,
  Login as LoginView,
} from './app';

const Routes = () => {
  return (
    <Switch>
      <Redirect
        exact
        from="/"
        to="/sign-up"
      />
      <RouteWithLayout
        component={SignUpView}
        exact
        layout={MinimalLayout}
        path="/sign-up"
      />
      <RouteWithLayout
        component={LoginView}
        exact
        layout={MinimalLayout}
        path="/sign-in"
      />
    </Switch>
  )
}

export default Routes
