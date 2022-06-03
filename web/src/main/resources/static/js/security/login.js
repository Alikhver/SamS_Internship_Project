$('#register-link').on('click', function () {
    const url = new URL(window.location.href);
    url.pathname = "/register"
    console.log(url.href)
    window.location.href = url.href;
})

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
