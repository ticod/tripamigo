function loginWithKaKao() {
    $.ajax({
        url: "/login/getKakaoAuthCode",
        type: "get",
        success: function(res) {
            location.href = res;
        }
    })
}