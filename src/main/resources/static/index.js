$(document).ready(function () {
    $("#login").click(function () {
        $.post("/login", {username: "tester", password: "123456"}, function (data) {
            console.log(data);
            $("#display").text(JSON.stringify(data));
        });
    });

    $("#getImage").click(function () {
        $("#photo").attr("src", "/getPhoto");
    });
    $("#testBtn").click(function () {
        $.post("/test",{},function (data) {
            console.log(data)
        });
    });

});