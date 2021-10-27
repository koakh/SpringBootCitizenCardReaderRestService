import React, { useState } from 'react';
import { StompSessionProvider, useSubscription } from 'react-stomp-hooks';
import './App.css';

function SubscribingComponent() {
  const [lastMessage, setLastMessage] = useState("No message received yet");
  //Subscribe to /topic/test, and use handler for all received messages
  //Note that all subscriptions made through the library are automatically removed when their owning component gets unmounted.
  //If the STOMP connection itself is lost they are however restored on reconnect.
  //You can also supply an array as the first parameter, which will subscribe to all destinations in the array
  useSubscription("/topic/test", (message) => {
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
      url={'http://192.168.122.160:8080/handler'}
    //All options supported by @stomp/stompjs can be used here
    >
      <SubscribingComponent />
    </StompSessionProvider>
  );
  // return (
  //   <div className="App">
  //     <header className="App-header">
  //       <img src={logo} className="App-logo" alt="logo" />
  //       <p>
  //         Edit <code>src/App.tsx</code> and save to reload.
  //       </p>
  //       <a
  //         className="App-link"
  //         href="https://reactjs.org"
  //         target="_blank"
  //         rel="noopener noreferrer"
  //       >
  //         Learn React
  //       </a>
  //     </header>
  //   </div>
  // );
}

export default App;
