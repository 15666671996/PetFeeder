$(document).ready(function () {
    $("#login").click(function () {
        $.post("/login", {username: "tester", password: "123456"}, function (data) {
            console.log(data);
            $("#display").text(JSON.stringify(data));
        });
    });

    $("#testBtn").click(function () {
        $("#photo").attr("src", "/getPhoto");
    });


});