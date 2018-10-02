var socket;

var name = "unknown";
var room = "Global";
var rooms = [];


function openWebSocket() {
	socket = new WebSocket("ws://localhost:8070/websocket");

	socket.onopen = function () {
		setName();
		send("online");
	};

	socket.onclose = function () {
		send(name + " offline");
	};

	socket.onmessage = function (event) {
		identificateMessage(decodeURI(event.data));
	};
}

function addPerson() {
	let text = document.getElementById("writeNamePerson").value;

	if (text == "") {
		return;
	}

	document.getElementById("writeNamePerson").value = "";

	let reqData =
	{
		name: name,
		password: "",
		room: text,
		commandName: "AddPersonToDialog",
		commandContext: text
	}

	let reqString = JSON.stringify(reqData);
	socket.send(reqString);
}

function createPrivateDialog() {
	let text = document.getElementById("writeNamePrivateDialog").value;

	if (text == "") {
		return;
	}

	document.getElementById("writeNamePrivateDialog").value = "";

	let reqData =
	{
		name: name,
		password: "",
		room: text,
		commandName: "CreatePrivateDialog",
		commandContext: text
	}

	let reqString = JSON.stringify(reqData);
	socket.send(reqString);
}


function createDialog() {
	let text = document.getElementById("writeNamePublicDialog").value;

	if (text == "") {
		return;
	}

	document.getElementById("writeNamePublicDialog").value = "";

	let reqData =
	{
		name: name,
		password: "",
		room: text,
		commandName: "CreateDialog",
		commandContext: text
	}

	let reqString = JSON.stringify(reqData);
	socket.send(reqString);
}

function showAnswer(answer) {
	var p = document.createElement("p");

	p.innerHTML = answer;
	var container = document.getElementById("container");

	container.appendChild(p);
}

function sendMessage() {
	send(document.getElementById("writeMessage").value);
	document.getElementById("writeMessage").value = "";
}

function setName() {
	let reqData =
	{
		name: name,
		password: "",
		room: room,
		commandName: "setName",
		commandContext: ""
	}

	let reqString = JSON.stringify(reqData);
	socket.send(reqString);
}

function send(message) {
	let reqData =
	{
		name: name,
		password: "",
		room: room,
		commandName: "sendMessage",
		commandContext: name + ": " + message
	}

	let reqString = JSON.stringify(reqData);
	socket.send(reqString);
}

function CancelAutorize() {
	let reqData =
	{
		name: "",
		password: "",
		room: "",
		commandName: "CancelAutorize",
		commandContext: ""
	}
	let reqString = JSON.stringify(reqData);

	let answer = requestToServer("api." + reqString);


	identificateMessage(answer);
}

function createAccount() {
	name = document.getElementById("writeName").value;

	let reqData =
	{
		name: document.getElementById("writeName").value,
		password: document.getElementById("writePassword").value,
		room: room,
		commandName: "setNameAndPassword",
		commandContext: ""
	}
	let reqString = JSON.stringify(reqData);

	let answer = requestToServer("api." + reqString);


	identificateMessage(answer);
}

function signIn() {
	name = document.getElementById("writeName").value;

	let reqData =
	{
		name: document.getElementById("writeName").value,
		password: document.getElementById("writePassword").value,
		room: room,
		commandName: "SignIn",
		commandContext: ""
	}
	let reqString = JSON.stringify(reqData);

	let answer = requestToServer("api." + reqString);

	identificateMessage(answer);
}



function requestToServer(reqString) {
	let xhr = new XMLHttpRequest();
	xhr.open('GET', reqString, false);
	xhr.send();
	let answer = xhr.responseText;

	return answer;
}

function getPageData() {
	let name = document.getElementById("writeName").value;
	let password = document.getElementById("writePassword").value;

	let pageData =
	{
		name: name,
		password: password,
	}

	return pageData
}


function identificateMessage(message) {
	let context = JSON.parse(message);

	switch (context.commandName) {

		case "SendMessage":
			{
				switch (context.commandContext) {

					case "Account not authorized":
						{
							localStorage.setItem("name", name);
							localStorage.setItem("startMessage", "Account not authorized");
							document.location.href = "UserPage.html";
							break;
						}

					case "Account created":
						{
							localStorage.setItem("name", name);
							localStorage.setItem("startMessage", "Account created");
							document.location.href = "UserPage.html";
							break;
						}

					case "Use a different name":
						{
							document.getElementById("result").innerHTML = "Use a different name";
							break;
						}

					case "Account authorized":
						{
							localStorage.setItem("name", name);
							localStorage.setItem("startMessage", "Account authorized");

							if(name == "Admin")
							{
								localStorage.setItem("startMessage", "Hello Administrator!");
								document.location.href = "AdminPage.html";
								break;
							}
							document.location.href = "UserPage.html";
							break;
						}

					case "Wrong name or password":
						{
							document.getElementById("result").innerHTML = "Wrong name or password";
							break;
						}

					case "Room Created":
						{
							addChatToTable(context.room);
							changeChat(context.room);

							showAnswer("Room Created");
							send("online");

							break;
						}

					default:
						{
							if (context.room.localeCompare(room) != -1 && context.room.localeCompare(room) != 1);
							{
								showAnswer(context.commandContext);
							}
						}

				}
				break;
			}

		case "AddNewDialog":
			{
				addChatToTable(context.commandContext)
				break;
			}

		case "SendMessageInCloseRoom":
			{

				break;
			}
	}
}




function createTtableRow(nameRoom) {
	var table = document.getElementById("table");

	var row = table.insertRow(1);
	var roomName = row.insertCell(0);
	roomName.innerHTML = nameRoom;
}

function deleteTableRows() {
	let table = document.getElementById("table");
	while (table.rows.length > 1) {
		table.deleteRow(1);
	}
}

function addChatToTable(chat) {
	if (rooms.indexOf(chat) !== -1) {
		return;
	}

	rooms.push(chat);
	var tb = document.getElementById("table");
	var row = tb.insertRow(1);
	row.insertCell(0).innerHTML = "<button id = \"" + chat + "\" onclick=changeChat(\"" + chat + "\")>" + chat + "</button>";

	if(chat.localeCompare('Global') == 0 )
	{
        document.getElementById(chat).style.background='#FFFFFF';
        return;
	}
     document.getElementById(chat).style.background='#FF0000';
}

function changeChat(chat) {
	var roomHtml = document.getElementById("room");
	roomHtml.innerHTML = chat;
	room = chat;

	let reqData =
	{
		name: name,
		password: "",
		room: room,
		commandName: "getMessages",
		commandContext: ""
	}

	let reqString = JSON.stringify(reqData);
	let answer = requestToServer("api." + reqString);

	answer = JSON.parse(decodeURI(answer));

	var p = document.createElement("p");

	p.innerHTML = answer.commandContext.replace(/\n/g, '<br>');
	var container = document.getElementById("container");
	container.innerHTML = "";
	container.appendChild(p);
	document.getElementById(chat).style.background='#FFFFFF';
}
