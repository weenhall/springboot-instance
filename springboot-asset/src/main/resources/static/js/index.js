$(document).ready(function () {
    $("#form_container").fadeIn(1500);

    $("input[name='usercode']").bind("blur", function () {
        if ($(this).val() == "")
            $(".hrname").css("background-color", "#FF0000");
    }).bind("focus", function () {
        $(".hrname").css("background-color", "#019EFF");
    });

    $("input[name='password']").bind("blur", function () {
        if ($(this).val() == "")
            $(".hrpwd").css("background-color", "#FF0000");
    }).bind("focus", function () {
        $(".hrpwd").css("background-color", "#019EFF");
    });

    $("input[name='captcha']").bind("blur", function () {
        if ($(this).val() == "")
            $(".hrcap").css("background-color", "#FF0000");
    }).bind("focus", function () {
        $(".hrcap").css("background-color", "#019EFF");
    });

    //基本表单验证
    $("#login_form").on("submit", function (e) {
        $(this).find('input[type="text"], input[type="password"] textarea').each(function () {
            if ($(this).val() == "") {
                e.preventDefault();
                $(this).css("background-color", "#FF0000");
            }
        });
    });
});