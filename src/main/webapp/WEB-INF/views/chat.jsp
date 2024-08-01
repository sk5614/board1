<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>WebSocket Chat</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.5.0/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script>
        var stompClient = null;

        function connect() {
            var socket = new SockJS('${pageContext.request.contextPath}/ws');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/public', function (message) {
                    showMessage(JSON.parse(message.body));
                });
            });
        }

        function sendMessage() {
            var messageContent = document.getElementById("message").value;
            var chatMessage = {
                sender: "User",
                content: messageContent,
                type: "CHAT"
            };
            stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
            document.getElementById("message").value = "";
        }

        function showMessage(message) {
            var messageElement = document.createElement('li');
            messageElement.appendChild(document.createTextNode(message.sender + ": " + message.content));
            document.getElementById("messages").appendChild(messageElement);
        }

        window.onload = function() {
            connect();
        }
    </script>
</head>
<body>
    <ul id="messages"></ul>
    <input type="text" id="message" />
    <button onclick="sendMessage()">Send</button>
</body>
</html>
