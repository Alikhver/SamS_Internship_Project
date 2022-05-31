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

    const loginInfo = {
        login: login,
        password: password
    };

    $.ajax({
        url: restUrl,
        type: 'POST',
        data: JSON.stringify(loginInfo),
        contentType: "application/json",
        success: function (data) {
            console.log(data)
            localStorage.setItem("Authorization", data.jwt);
        }
    });

    console.log('data')
});

const validateLogin = function () {
    const input = $('#login-input');
    const length = input.val().length;

    if (length < 7 || length > 30) {
        $('#incorrect-login').slideDown();
        return false;
    } else {
        $('#incorrect-login').slideUp();
        return true;
    }
}


const validatePassword = function () {
    const input = $('#password-input');
    const length = input.val().length;

    if (length < 7 || length > 30) {
        $('#incorrect-password').slideDown();
        return false;
    } else {
        $('#incorrect-password').slideUp();
        return true;
    }
}

$('#login-input').on('change', validateLogin)
$('#password-input').on('change', validatePassword)
