function sendEmailAuthCode() {
    $.ajax({
        url: "/email/send",
        type: "post",
        dataType: 'text',
        success: function(res) {
            alert(res);
        }
    })
}

function checkEmailAuthCode() {
    $.ajax({
        url: "/email/check",
        type: "post",
        dataType: 'text',
        success: function(res) {
            alert(res);
        }
    })
}