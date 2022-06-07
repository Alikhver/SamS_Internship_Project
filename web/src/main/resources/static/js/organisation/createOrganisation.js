$('#createBtn').on('click', function () {

    const redactorLogin = validateLogin();
    const redactorPassword = validatePassword();
    const name = validateOrgName();
    const address = validateAddress();
    const description = validateDescription();

    if (redactorLogin && redactorPassword && name && address && description) {
        const restUrl = "/organisations"

        $.ajax({
            url: restUrl,
            type: 'post',
            data: JSON.stringify({
                name,
                description,
                address,
                redactorLogin,
                redactorPassword
            }),
            contentType: "application/json",
            success: function () {
                window.location.href = "/login";
            }, error: function (request, status, error) {
                alertUser(request, status, error);
            }
        });
    }
});

function goBack () {
    window.location.href = "/"
}


function validateOrgName () {
    const orgName = $('#name').val();
    const length = orgName.length;

    if (length < 2 || length > 30) {
        $('#incorrect-name').slideDown();
        return false;
    } else {
        $('#incorrect-name').slideUp();
    }
    return orgName;
}

function validateLogin() {
    const login = $('#login').val();
    const length = login.length;


    $.ajax({
        url: "/users/loginExists",
        type: 'get',
        data: {login},
        contentType: "application/json",
        success: function (data) {
            if (data) {
                $('#incorrect-login-exists').slideDown();
                return false;
            } else {
                $('#incorrect-login-exists').slideUp();
            }
        }
    });

    if (length > 30 || length < 7) {
        $('#incorrect-login').slideDown();
        return false;
    } else {
        $('#incorrect-login').slideUp();
        return login;
    }
}



function validatePassword() {
    const password = $('#password').val();
    const length = password.length;

    if (length > 30 || length < 6) {
        $('#incorrect-password').slideDown();
        return false;
    } else {
        $('#incorrect-password').slideUp();
        return password;
    }
}

function validateAddress() {

    const input = $('#address');
    const length = input.val().length;

    if (length < 2 || length > 30) {
        $('#incorrect-address').slideDown();
        return false;
    } else {
        input.parent().next().slideUp();

    }
    return input.val();
}

function validateDescription() {
    const input = $('#description');
    const length = input.val().length;

    if (length < 30 || length > 120) {
        $('#incorrect-description').slideDown();
        return false;
    } else {
        input.parent().next().slideUp();
    }
    return input.val();
}