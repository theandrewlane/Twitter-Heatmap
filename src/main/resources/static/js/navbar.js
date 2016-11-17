/**
 * Created by andrewlane on 11/15/16.
 */

$(() => {
    $('.navbar-nav > li').removeClass('active');
    if (window.location.href.indexOf("connect") > -1) $('#home').addClass('active');
    if (window.location.href.indexOf("twitter-stream") > -1) $('#view-stream').addClass('active');
    if (window.location.href.indexOf("twitter-search") > -1)$('#search').addClass('active');

});