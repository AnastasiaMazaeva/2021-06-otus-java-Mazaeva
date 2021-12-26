var stompClient = null;

function loadAll() {
    if (stompClient.connected) {
        stompClient.send("/app/all", {}, null);
    } else {
        console.log('NOT CONNECTED');
    }
}
function populateTable(data) {
    const table = document.getElementById("userTableBody");
    var items = JSON.parse(data.body);
    $("#userTableBody tr").remove()
    items.forEach( item => {
        let row = table.insertRow();
        let date = row.insertCell(0);
        date.innerHTML = item.id;
        let login = row.insertCell(1);
        login.innerHTML = item.login;
        let role = row.insertCell(2);
        role.innerHTML = item.role;
    });
}

function connect() {
    stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));

    stompClient.connect({}, (frame) => {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/users/page', (data) => populateTable(data));
    });
}

function init() {
    connect();
    setTimeout(() => loadAll(), 1000);
}

function edit() {
    stompClient.send("/app/edit", {}, JSON.stringify({
        'id': $("#userIdTextBox").val(),
        'login' : $("#userLoginTextBoxUpdate").val(),
        'role' : $("#userRoleSelectBoxUpdate").val()}));
}

function create() {
    stompClient.send("/app/create", {}, JSON.stringify({
        'login' : $("#userLoginTextBox").val(),
        'role' : $("#userRoleSelectBox").val()}));
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#edit" ).click(function() { edit(); });
    $( "#create" ).click(function() { create(); });
});

