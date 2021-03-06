const debug = true;
let socket,
    stompClient,
    isConnected = false;

$(() => {

    const self = this;

    let map;
    let heatmap;

    const arrayOfPoints = new google.maps.MVCArray();

    self.useLocation = () => {
        navigator.geolocation.getCurrentPosition(function (position) {
            lat = position.coords.latitude;
            lng = position.coords.longitude;
            setLocation(lat, lng);
        });
    };

    self.setLocation = (lat, lng) => {
        map.setCenter(new google.maps.LatLng(lat, lng));
    };

    self.useLocation();

    let tweetCount = 0;
    $("#stream-spinner").hide();

    self.setConnected = (connected) => {
        isConnected = connected;
        $("#connect").prop("disabled", connected);
        $("#disconnect").prop("disabled", !connected);
        if (connected) $("#tweet-table").show();
        else $("#conversation").hide();
    };

    self.connect = (socketPath) => {
        socket = new SockJS(socketPath);
        stompClient = Stomp.over(socket);
        stompClient.debug = debug || null;
        $("#stream-spinner").show();
        if(!isConnected) {
            return stompClient.connect({}, isConnected => {
                setConnected(true);
                stompClient.subscribe('/tweets/stream', json => {
                    const jsonString = JSON.parse(json.body);
                    addPoint(jsonString.lat, jsonString.lng);
                });

                stompClient.subscribe('/tweets/userinfo', json => {
                    postTweet(JSON.parse(json.body));
                });

                //send bounds once on connect
                let bounds = map.getBounds();
                let b = {};
                b.north = bounds.getNorthEast().lat();
                b.east = bounds.getNorthEast().lng();
                b.south = bounds.getSouthWest().lat();
                b.west = bounds.getSouthWest().lng();
                stompClient.send("/app/bounds", {}, JSON.stringify(b));
            });
        }
    };

    self.disconnect = () => {
        if (isConnected) {
            stompClient.send("/app/disconnect", {}, "ewd87yewdew87dewcew3");
            stompClient.unsubscribe();
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

    /* HeatMap */

    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 13,
        mapTypeId: 'roadmap'
    });

    //restrict zoom
    const opt = { minZoom: 6, maxZoom: 15 };
    map.setOptions(opt);

    // Heatmap data
    self.getPoints = () => {
        return arrayOfPoints;
    };

    //add a point
    self.addPoint = (lat, lng) => {
        self.getPoints().push(new google.maps.LatLng(lat, lng));
    };

    heatmap = new google.maps.visualization.HeatmapLayer({
        data: self.getPoints(),
        map: map
    });

    heatmap.setMap(map);

    function changeRadius() {
        heatmap.set('radius', heatmap.get('radius') ? null : 20);
    }

    /* End HeatMap */

});


