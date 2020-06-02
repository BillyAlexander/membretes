import axios from 'axios';
import { domainConfig } from './Config';

const ApiHost = domainConfig.api.url;
const accessToken = 'accessToken';
const tokenType = 'tokenType';

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

function request(method, resource, params, token = localStorage.getItem("token")) {
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

function signUp(method, resource, params) {
  let options = {
    url: resource,
    method: method,
    data: params,

    headers: {
      'Content-Type': 'application/json',
    }
  };
  return http_axios(options).then(handle_response()).catch(handle_errors());
};

const getCurrentUser = (method, resource, params) => {
  console.log(localStorage.getItem(accessToken), localStorage.getItem(tokenType));
  if (!localStorage.getItem(accessToken) || !localStorage.getItem(tokenType)) {
    return Promise.reject("Access Denied");
  }

  let options = {
    url: domainConfig.api.pathAuth + resource,
    method: method,
    data: params,

    headers: {
      'Content-Type': 'application/json',
      'Authorization': localStorage.getItem(tokenType) + ' ' + localStorage.getItem(accessToken),
    }
  };

  return http_axios(options).then(handle_response()).catch(handle_errors());
}

const isAuthenticated = () => {
  if (!localStorage.getItem(accessToken) || !localStorage.getItem(tokenType)) {
    return false
  }
  return true;
}


const logout = () => {
  try {
    //localStorage.clear();
    localStorage.removeItem(accessToken);
    localStorage.removeItem(tokenType);
    return true;
  } catch (error) {
    return false;
  }
}

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
    },
    signUp: (params) => {
      return signUp('post', resource, params);
    },
    getCurrentUser: (params) => {
      return getCurrentUser('get', resource, params);
    },
    isAuthenticated: () => {
      return isAuthenticated();
    },
    logout: () => {
      return logout();
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
      return formatMessage(response.data);
    }
    return response.data;
  };
};
const setHeaders = () => {
  return response => {
    if (!response.data.error) {
      localStorage.setItem(accessToken, response.data.accessToken);
      localStorage.setItem(tokenType, response.data.tokenType);
      return response.data;
    }
    return response.data.error;
  };
};

const formatMessage = (data) => {
  if (data.isCustom) {
    return { status: data.statusCode, message: data.message }
  } else {
    return { data: data }
  }
};

const formatError = (err) => {
  if (err.response) {
    let { status, error, message, path } = err.response.data;

    let fields = "";
    if (err.response.data.isCustom && err.response.data.fieldErrors) {
      err.response.data.fieldErrors.map((item, index) => {
        fields = fields + (index + 1) + ") " + item.field + " : " + item.defaultMessage + " ";
        return fields;
      });
    }
    if (!message && fields !== "")
      message = fields;

    if (!message)
      message = "";

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
    let custom_error = formatError(error);
    return Promise.reject(custom_error);
  };
};
