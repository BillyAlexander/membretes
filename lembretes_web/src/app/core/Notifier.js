import React from 'react';
import { toast, Zoom } from 'react-toastify';
import {
  Grid,
  Typography,
} from '@material-ui/core';

const defaultOptions = { position: toast.POSITION.TOP_RIGHT };
const createOptions = { autoClose: false, transition: Zoom, position: toast.POSITION.TOP_RIGHT };

const viewMessage = (txt) => {
  let txts = txt.split("_");
  let font = "subtitle1"
  return (
    <div>
      {txts.map((item, index) => {

        if (index > 0) {
          font = "body2";
        }
        return (
          <Grid key={index}>
            <Typography
              variant={font}
            >
              {item}
            </Typography>
          </Grid>
        )
      })
      }
    </div>
  );
}


export const Notifier = (toastId) => {

  return {
    loading: function (params) {
      return toastId.current = toast(params.component, createOptions);
    },
    update: function (params) {
      let typeNotifier = toast.TYPE.DEFAULT;
      switch (params.type) {
        case "success":
          typeNotifier = toast.TYPE.SUCCESS;
          break;
        case "error":
          typeNotifier = toast.TYPE.ERROR;
          break;
        case "warning":
          typeNotifier = toast.TYPE.WARNING;
          break;
        default:
          typeNotifier = toast.TYPE.INFO;
          break;
      }

      return toast.update(toastId.current, { render: viewMessage(params.component), type: typeNotifier, autoClose: 5000, transition: Zoom })
    },
    default: function (params) {
      return toast(params.component, defaultOptions)
    },
  };
};
