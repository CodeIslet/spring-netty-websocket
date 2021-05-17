# SPRINGBOOT NETTY WEBSOCKET

## Quick start
From the command line do:
```
git clone https://github.com/warchang/spring-boot-netty-websocket.git
cd spring-boot-netty-websocket
mvn clean package
java -jar service/target/*.jar
```

## WebSocket Request & Response

### Request
```
{
    "mapper": "MAPPER_NAME",
    "body": {
        ... Request body
    }
}
```

### Response
```
{
    "stauts": "response status", // (OK, ERROR, BAD_REQUEST)
    "identifier": "target identifier",
    "message": "message",
    "body": "response body",
    "time": "response time"
}
```


## Annotations

### WebSocketController
Controller class (&efDot; @RestController of SpringMVC)
```
@WebSocketController
public class Groupcontroller() {
    ...
}
```

### WebSocketRequestMapping
Websocket request handler mapping (&efDot; @RequestMapping of SpringMVC)
```
@WebSocketController
public class GroupController() {

    /**
     * Websocket request object
     * {
     *   "mapper": "join",
     *   "body": {
     *     "name": "Joe"
     *   }
     * }
     */
     @WebSocketRequestMapping(value = "join")
     public void join(JoinRequest req, ChannelHandlerContext ctx, CompletableFuture<ResponseEntity> future) {
        ...
     }
     
    /**
     * Websocket request object
     * {
     *   "mapper": "leave",
     *   "body": null (or pass)
     * } 
     @WebSocketRequestMapping(value = "leave")
     public void leave(ChannelHandlerContext ctx, CompletableFuture<ResponseEntity> future) {
        ...
     }
}     
```


### WebSocketControllerAdvice, WebSocketExceptionHandler
&efDot; ControllerAdvice of SpringMVC

```
@WebSocketControllerAdvice
public class WebSocketExceptionAdvice {
    
    @WebSocketExceptionHandler(throwables = { GroupProcessingException.class })
    public ResponseEntity groupProcessingExceptionHandler(GroupProcessingException e) {
        return new ResponseEntity(ResponseEntity.ResponseStatus.ERROR, e.getMessage());
    }
}
```

## WebSocket template
Provides helper methods for websocket control.

MessageFrame object
```
channelType: GROUP(Channel group), SINGLE(Channel)
destination: group name or channel id
message: websocket message string
data: websocket data body object
```

### Single channel helper
- send
- join
- leave


### Channel group helper
- sendGroup
- joinGroup
- leaveGroup


## PubSub for multiple websocket server instances
You can implement and provide a Publisher or Subscriber interface.
this example uses the redis pub/sub provider by default.

(client) <--> [LB] <---> server instances <---> pub/sub channel (default: redis)
