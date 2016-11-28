const debug = true;
let socket,
    stompClient,
    isConnected = false;

$(() => {
    const self = this;
    let tweetCount = 0;
    $("#stream-spinner").hide();

    self.setConnected = (connected) => {
        isConnected = connected;
        $("#connect").prop("disabled", connected);
        $("#disconnect").prop("disabled", !connected);
        if (connected) $("#tweet-table").show();
        else $("#conversation").hide();
    };

    self.getLocation = (lat, lon) => stompClient.send("/app/searchLocation", {}, JSON.stringify({'location': $("#location").val()}));

    self.useLocation = () => {
        navigator.geolocation.getCurrentPosition(function (position) {
            lat = position.coords.latitude;
            lng = position.coords.longitude;
            $('#input-location').val(`Lat: ${lat} Lon: ${lng}`);
            getLocation(lat, lng)
        });
    };

    self.connect = (socketPath) => {
        socket = new SockJS(socketPath);
        stompClient = Stomp.over(socket);
        stompClient.debug = debug || null;
        $("#stream-spinner").show();
        setConnected(true);
        return stompClient.connect({}, isConnected => stompClient.subscribe('/tweets/stream', tweet => postTweet(JSON.parse(tweet.body))));
    };

    self.disconnect = () => {
        if (isConnected) {
            stompClient.disconnect();
            setConnected(false);
            $("#tweet-content > *").remove();
            $("#stream-spinner").hide();
        }
    };

    self.postTweet = (tweet) => {
        tweetCount++;
        return $("#tweet-content").append(
            `<li class="mdl-list__item mdl-list__item--three-line">
                <span class="mdl-list__item-primary-content">
                  <img src="${tweet.user.profileImageUrl}" class="img-circle" style="width:48px;height:48px;">
                  <span><a href="${tweet.user.profileUrl}">@${tweet.fromUser}</a></span>
                  <span class="mdl-list__item-text-body">${tweet.text} </span>
                </span>
            </li>`
        );
    };


    self.search = () => stompClient.send("/tweets/search", {}, $('#search').val());

});


