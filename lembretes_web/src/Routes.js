import React from 'react';
import { RouteWithLayout } from './app/routeWithLayout'
import { Minimal as MinimalLayout, Main as MainLayout } from './app/layouts/';
import { Switch, Redirect } from 'react-router-dom';
import AppHeader from './app/components/commons/AppHeader'

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
        authenticated={false}
      />
      <RouteWithLayout
        component={LoginView}
        exact
        layout={MinimalLayout}
        path="/sign-in"
        authenticated={false}
      />
      <RouteWithLayout
        component={AppHeader}
        exact
        layout={MainLayout}
        path="/home"
        authenticated={true}
      />
    </Switch>
  )
}

export default Routes
