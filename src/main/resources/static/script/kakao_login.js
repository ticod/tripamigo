function loginWithKaKao() {
    $.ajax({
        url: "/oauth/kakao_auth_code",
        type: "get",
        dataType: 'text',
        success: function(res) {
            location.href = res;
        }
    })
}