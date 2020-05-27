import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import PropTypes from 'prop-types';
import TopBar from './TopBar';


const Minimal = props => {
  const { children } = props;
  const classes = useStyles();
  return (
    <div className={classes.root}>
      <TopBar />
      <main className={classes.content}>{ children } </main>
    </div>
  );
}

const useStyles = makeStyles(() => ({
  root: {
    paddingTop: 64,
    height: '100%'
  },
  content: {
    height: '100%'
  }
}));

Minimal.propTypes = {
  children: PropTypes.node,
  className: PropTypes.string
};

export default Minimal
