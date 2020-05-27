import React from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import { AppBar, Toolbar } from '@material-ui/core';

const TopBar = () => {
  // const { className, ...rest } = props;
  const classes = useStyles();
  return (
    <AppBar
      className={classes.root}
      color="primary"
      position="fixed"
    >
      <Toolbar>
          <img
            alt="Logo"
            src="/images/logos/logo--white.svg"
          />
      </Toolbar>
    </AppBar>
  )
}

const useStyles = makeStyles(() => ({
  root: {
    boxShadow: 'none'
  }
}))

TopBar.propTypes = {
  className: PropTypes.string
};

export default TopBar
