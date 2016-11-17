

let stompClient = null;
let lat, lng;
$(() => {
    connect();
});

setConnected = (connected) => {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) $("#tweet-table").show();
    else $("#conversation").hide();
};

getLocation = (lat, lon) => stompClient.send("/app/searchLocation", {}, JSON.stringify({'location': $("#location").val()}));

useLocation = () => {
    navigator.geolocation.getCurrentPosition(function (position) {
         lat = position.coords.latitude;
         lng = position.coords.longitude;
        $('#input-location').val(`Lat: ${lat} Lon: ${lng}`);
        getLocation(lat, lng)
    });
};
connect = () => {
    const socket = new SockJS('/tweet-socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, frame => {
        setConnected(true);
        stompClient.subscribe('/topic/tweets', tweet => postTweet(JSON.parse(tweet.body)));
    });
};

disconnect = () => {
    if (stompClient != null) stompClient.disconnect();
    setConnected(false);
    $("#tweet-content > *").remove();
};


postTweet = (tweet) => $("#tweet-content").append(`<tr><td><a href="${tweet.user.profileUrl}">@${tweet.fromUser}</a></td><td>${tweet.text}</td><td>${tweet.user.location}</td></tr>`);



