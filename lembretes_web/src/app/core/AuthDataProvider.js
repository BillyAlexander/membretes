import React, { createContext, useState, useEffect, useMemo, useContext } from 'react';
import { requestApi } from './CoreApi'


const pathMe = '/user/me'

const AuthDataProvider = props => {
  const [authData, setAuthData] = useState({});

  const getCurrentUser = async () => {
    console.log("llega");
    await requestApi(pathMe)
      .getCurrentUser({})
      .then(response => {
        console.log("okakiiii", response);
        return response;
      })
      .catch(error => {
        console.log("err", error);
        return error;
      });

  };

  /* The first time the component is rendered, it tries to
* fetch the auth data from a source, like a cookie or
* the localStorage.
*/
  useEffect(() => {
    const currentAuthData = getCurrentUser(); console.log("currentAuthData", currentAuthData);
    if (currentAuthData) {
      setAuthData(currentAuthData);
    }
  }, []);

  return authData;


}


export default AuthDataProvider


// export const AuthDataContext = createContext(null);

// const initialAuthData = {};
// const pathMe = '/user/me'

// const AuthDataProvider = props => {
//   const [authData, setAuthData] = useState(initialAuthData);

//   const getCurrentUser = async () => {
//     console.log("llega");
//     await requestApi(pathMe)
//       .getCurrentUser({})
//       .then(response => {
//         console.log("okakiiii", response);
//         return response;        
//       })
//       .catch(error => {
//         console.log("err", error);
//         return error;
//       });

//   }

//   /* The first time the component is rendered, it tries to
//  * fetch the auth data from a source, like a cookie or
//  * the localStorage.
//  */
//   useEffect(() => {
//     const currentAuthData = getCurrentUser();console.log("currentAuthData",currentAuthData);
//     if (currentAuthData) {
//       setAuthData(currentAuthData);
//     }
//   }, []);

//   const onLogout = () => setAuthData(initialAuthData);

//   const onLogin = newAuthData => setAuthData(newAuthData);



//   //const authDataValue = useMemo(()=>{ setAuthData(authData) }, [authData]);

//   return <AuthDataContext.Provider value={{ authData, onLogin, onLogout }} {...props} />;

// }


// export const useAuthDataContext = () => useContext(AuthDataContext);


// export default AuthDataProvider;
