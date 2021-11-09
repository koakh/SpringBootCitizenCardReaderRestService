export const envVariables: {[key: string]: string} = {
  webSocketServerUrl: process.env.REACT_APP_WEBSOCKET_SERVER_URL || 'http://192.168.122.160:8080/handler'
}
