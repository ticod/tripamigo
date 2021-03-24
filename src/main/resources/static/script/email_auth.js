const emailInput = document.getElementById("email");
const codeInput = document.getElementById("code");

function sendEmailAuthCode() {
    $.ajax({
        url: "/email/send",
        type: "POST",
        dataType: 'text',
        data: {
            "email": emailInput.value
        },
        success: function(res) {
            if (res === "true") {
                alert("인증번호를 발송했습니다");
            } else {
                alert("이메일을 확인해주세요.");
            }
        }
    })
}

function checkEmailAuthCode() {
    $.ajax({
        url: "/email/check",
        type: "POST",
        dataType: 'text',
        data: {
            "email": emailInput.value,
            "code": codeInput.value
        },
        success: function(res) {
            alert(res);
        }
    })
}