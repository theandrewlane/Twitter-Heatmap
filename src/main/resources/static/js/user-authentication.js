/**
 * Created by andrewlane on 12/14/16.
 */

//Onload function block
$(() => {
    const self = this;

    $('.message a').click(() => self.fadeOut($('form')));

    //Fade everything back in
    $('login-button').click(() => self.fadeOut());
    $('#create-button').click(() => self.fadeOut());

    self.fadeOut = (elm) => {
        return elm ? elm.animate({
            opacity: "toggle",
            height: "toggle"
        }, "slow") : $("*[class^='twitter-']").animate({opacity: "toggle", height: "toggle"}, "slow");
    };

    //Fade out by default... this JS is only present if user isn't auth-ed
    self.fadeOut();

});
