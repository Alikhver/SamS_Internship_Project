$('#firstName').on('change', validateFirstName);
$('#lastName').on('change', validateLastName);
$('#login').on('change', validateLogin);
$('#password').on('change', validatePassword);
$('#email').on('change', validateEmail);
$('#phoneNumber').on('change', validatePhoneNumber);

function validateFirstName() {
    const firstName = $('#firstName').val();
    const length = firstName.length;

    if (length > 45 || length < 1) {
        $('#incorrect-firstName').slideDown();
        return false;
    } else {
        $('#incorrect-firstName').slideUp();
        return firstName;
    }
}

function validateLastName() {
    const lastName = $('#lastName').val();
    const length = lastName.length;

    if (length > 45 || length < 1) {
        $('#incorrect-lastName').slideDown();
        return false;
    } else {
        $('#incorrect-lastName').slideUp();
        return lastName;
    }
}

function validateLogin() {
    const login = $('#login').val();
    const length = login.length;

    if (length > 30 || length < 6) {
        $('#incorrect-login').slideDown();
        return false
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
        return false
    } else {
        $('#incorrect-password').slideUp();
        return password;
    }
}

function validateEmail() {
    const email = $('#email').val();

    if (!email.match("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
        $('#incorrect-email').slideDown();
        return false;
    } else {
        $('#incorrect-email').slideUp();
        return email;
    }
}


function validatePhoneNumber() {
    const phoneNumber = $('#phoneNumber').val();
    if (!phoneNumber.match("^\\s*\\+?375((33\\d{7})|(29\\d{7})|(44\\d{7}|)|(25\\d{7}))\\s*$")) {
        $('#incorrect-phoneNumber').slideDown();
        return false;
    } else {
        $('#incorrect-phoneNumber').slideUp();
        return phoneNumber;
    }
}

$("#agreedOnTerms").on('change', function() {
    if(this.checked) {
        $('#register').removeClass('disabled');
    } else {
        $('#register').addClass('disabled');
    }
});

$('#register').on('click', function () {
    const firstName = validateFirstName();
    const lastName = validateLastName();
    const login = validateLogin();
    const password = validatePassword();
    const email = validateEmail();
    const phoneNumber = validatePhoneNumber();

    if (firstName && lastName && login && password && email && phoneNumber) {

    }
})