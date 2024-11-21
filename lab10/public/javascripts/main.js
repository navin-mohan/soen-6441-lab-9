// Documentation: https://developer.mozilla.org/en-US/docs/Web/API/WebSocket


// Create WebSocket connection.
const socket = new WebSocket("ws://localhost:9000/ws");

// Connection opened
socket.addEventListener("open", (event) => {
    document.getElementById("status").innerHTML = "Connected";
});

socket.addEventListener("close", (event) => {
    document.getElementById("status").innerHTML = "Disconnected";
});

// Listen for messages
socket.addEventListener("message", (event) => {
    var timeStr = document.createElement("li");
    timeStr.textContent = event.data;
    document.getElementById("time-tick").appendChild(timeStr);
});
