import axios from 'axios';
import { domainConfig } from './Config';

const ApiHost = domainConfig.api.url;

/* const method_map = {
  get: 'get',
  post: 'create',
  put: 'update',
  delete: 'remove'
}; */

const http_axios = axios.create({
  baseURL: ApiHost,
  timeout: 8000,
});

function request(method, resource, params, token = sessionStorage.getItem("token")) {
  console.log('despues', token);
  let options = {
    url: resource,
    method: method,
    data: params,

    headers: {
      Authorization: "Bearer " + token,
      'Content-Type': 'application/json',
    }
  };
  return http_axios(options).then(handle_response()).catch(handle_errors());

};
function login(method, resource, params) {
  let options = {
    url: resource,
    method: method,
    data: params,

    headers: {
      'Content-Type': 'application/json',
    }
  };
  return http_axios(options).then(setHeaders()).catch(handle_errors());

};

export const requestApi = (resource) => {

  return {

    get: function (params) {
      var _resource = params ? resource + '/' + params : resource;
      return request('get', _resource);
    },

    create: function (params) {
      return request('post', resource, params);
    },

    update: function (params) {
      return request('put', resource + '/' + params._id, params);
    },

    remove: function (params) {
      return request('delete', resource + '/' + params._id);
    },
    login: (params) => {
      return login('post', resource, params);
    }
    // get_one: function (params) {
    //     return this.get(params).then(function (elements) {
    //         if (!_(elements).isEmpty()) {
    //             return elements[0];
    //         }

    //         return [];
    //     });
    // }
  };
};


/* Handlers */
const handle_response = () => {
  return response => {
    if (!response.data.error) {
      return response.data.response;
    }
    return response.data.error;
  };
};
const setHeaders = () => {
  return response => {
    if (!response.data.error) {
      sessionStorage.setItem("accessToken", response.data.accessToken);
      sessionStorage.setItem("tokenType", response.data.tokenType);
      return response.data;
    }
    return response.data;
  };
};
const formatError = (err) => {
  if (err.response) {

    let { status, error, message, path } = err.response.data;

    let fields = "";
    if (err.response.data.isCustom && err.response.data.fieldErrors) {
      err.response.data.fieldErrors.map((item, index) => {
        fields = fields +(index+1)+") "+ item.field + " : " + item.defaultMessage + " ";
        return fields;
      });
    }
    if (!message && fields !=="")
      message = fields;

    return { status: status, error: err.response.data, message: error + " _ " + message, element: path }
  } else if (err.request) {
    const { status, responseText, statusText, responseType, responseURL, response } = err.request;
    return { status: status, error: response, message: "Something went wrong. Please try again! " + statusText + " " + responseType + " " + responseText, element: responseURL }
  } else {
    const { responseURL } = err.request;
    return { status: -1, message: "Something went wrong. Please  the Admin ", element: responseURL }
  }
};

const handle_errors = () => {
  return error => {

    // if (error.response) {
    //   // client received an error response (5xx, 4xx)
    //   custom_error = formatError(error);
    //   return Promise.reject(custom_error)
    // } else if (error.request) {
    //   // client never received a response, or request never left
    //   custom_error = formatError(error);
    //   return Promise.reject(custom_error);

    // } else {
    //   // anything else
    //   custom_error = formatError(error);
    //   return Promise.reject(custom_error);
    // }
    let custom_error = formatError(error);
    return Promise.reject(custom_error);
  };

};
