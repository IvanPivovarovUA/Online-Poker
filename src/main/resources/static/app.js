const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/greetings', (greeting) => {
        showGreeting1(JSON.parse(greeting.body));
    });

    stompClient.subscribe('/queue/getuser', (greeting) => {
        showGreeting2(JSON.parse(greeting.body));
    });

    sendName();
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};
////////////////////


function connect() {
    stompClient.activate();
}

function sendName() {
    stompClient.publish({
        destination: "/app/hello",
        body: JSON.stringify({'name': "test sending messege123"})
    });

}
function getInfo() {
    stompClient.publish({
        destination: "/app/getinfo"
    });
}

function showGreeting1(message) {
    getInfo();
    // $("#greetings").append("<tr><td>" + message + "</td></tr>");
    console.log(message);
    $("#ontable").empty();
    $("#onhall").empty();
    $("#bank").empty();
    $("#grop2").empty();
    $("#userinterface").empty();

    for (let i = 0; i < message.PlayersOnTable.length; i++) {
        $("#ontable").append(
            '<li><div id = "grop"><p>' + 
            message.PlayersOnTable[i].Name +
            message.PlayersOnTable[i].Balance +
            '</p></div">'
            
            // + '<div id = "grop"><p>dg' + message.Bank + '</p></div"></li>'
        );
    }
    for (let i = 0; i < message.PlayersOnHall.length; i++) {
        $("#onhall").append(
            '<li><div id = "grop"><p>' + 
            message.PlayersOnHall[i].Name +
            message.PlayersOnHall[i].Balance +
            '</p></div">'
            
        ); 
    }



    $("#bank").append(
        "Bank: " + message.Bank
    );
    for (let i = 0; i < message.CardsOnTable.length; i++) {
        $("#grop2").append(
            "<h1>" + message.CardsOnTable.Number + message.CardsOnTable.Suit + "</h1>"
        );  
    }

    

    // $("#userinterface").append(
    //     "<h3>" + message.Bank +"</h3>"
    // );

    // $("#userinterface").append(
    //     '<input id = "send" type="submit" value="Raise"></form>'
    // );

    // $( "#send" ).click(() => getInfo());
}
function showGreeting2(message) {
    console.log(message);
    $("#userinterface").append(
        "<h3>" + message.Name + message.Balance +"</h3>"
    );
    $("#userinterface").append(
        '<input id = "send" type="submit" value="Raise"></form>'
    );
    $( "#send" ).click(() => getInfo());
} 

$(function () {
    connect();

    $("form").on('submit', (e) => e.preventDefault());///фигня шобы не обновлять страницу
    // $( "#send" ).click(() => sendName());
    
});

