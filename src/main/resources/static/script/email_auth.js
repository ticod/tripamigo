const emailInput = document.getElementById("email");
const codeInput = document.getElementById("code");
const emailCheck = document.getElementById("email_check");

emailInput.addEventListener("change", changeEmailInput);
changeEmailInput();

function checkEmailAuth() {
    return emailCheck.value === "true";
}

function setEmailCheck() {
    emailCheck.value = "true";
}

function changeEmailInput() {
    emailCheck.value = "false";
}

function sendEmailAuthCode() {

    if(emailInput === null || emailInput.value.trim() === ""){
        alert("이메일을 입력하세요")
        emailInput.focus();
        return;
    }

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
            if (res === "true") {
                alert("이메일 인증이 완료되었습니다.");
                setEmailCheck();
            } else {
                alert("인증번호를 확인해주세요.");
            }
        }
    })
}