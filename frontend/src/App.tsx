import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';
import { Typography } from '@mui/material';
import Button from '@mui/material/Button';
import React, { useState } from 'react';
import { StompSessionProvider, useSubscription } from 'react-stomp-hooks';
import { envVariables } from './app';
import './App.css';
import { appConstants } from './app/appConstants';

const createClickHandler = () => {
  console.log('clicked createClickHandler....');
}

const updateClickHandler = () => {
  console.log('clicked updateClickHandler....');
}

const deleteClickHandler = () => {
  console.log('clicked deleteClickHandler....');
}

const SubscribingComponent = () => {
  const [lastMessage, setLastMessage] = useState('No message received yet');
  //Subscribe to /topic/test, and use handler for all received messages
  //Note that all subscriptions made through the library are automatically removed when their owning component gets unmounted.
  //If the STOMP connection itself is lost they are however restored on reconnect.
  //You can also supply an array as the first parameter, which will subscribe to all destinations in the array
  useSubscription(appConstants.topicTest, (message) => {
    // const payload = JSON.parse(message.body.replace(`'`, `"`));
    // console.log(`payload: [${JSON.stringify(payload, undefined, 2)}]`);
    // if (payload.givenName) {
    //   console.log(`givenName: ${payload.givenName}`);
    // } else if (payload.message){
    //   console.log(`message: ${payload.message}`);
    // };
    setLastMessage(message.body);
  });

  return (
    <div>Last Message: {lastMessage}</div>
  );
}

function App() {
  return (
    //Initialize Stomp connection, will use SockJS for http(s) and WebSocket for ws(s)
    //The Connection can be used by all child components via the hooks or hocs.
    <StompSessionProvider
      //All options supported by @stomp/stompjs can be used here
      url={envVariables.webSocketServerUrl}
      debug={(msg: string) => { console.log(msg) }}
    >
      <Typography>{`webSocketServerUrl: ${envVariables.webSocketServerUrl}`}</Typography>
      <SubscribingComponent />
      <Button variant='contained' onClick={createClickHandler}>Create</Button>
      <Button variant='contained' onClick={updateClickHandler}>Update</Button>
      <Button variant='contained' onClick={deleteClickHandler}>Delete</Button>
    </StompSessionProvider>
  );
}

export default App;
