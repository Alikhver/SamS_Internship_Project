$('#register-link').on('click', function () {
    const url = new URL(window.location.href);
    url.pathname = "/register"
    console.log(url.href)
    window.location.href = url.href;
})

$('#login-btn').on('click', function () {
    const url = new URL(window.location.href);

    const login = $('#login-input').val();
    const password = $('#password-input').val();

    const restUrl = '/auth/login';

    const loginDTO = {
      login: login,
      password: password
    };

    $.ajax({
        url: restUrl,
        type: 'POST',
        data: JSON.stringify(loginDTO),
        contentType: "application/json",
        success: function (data) {
            console.log(data.jwt)
        }
    });
});