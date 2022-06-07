function goBack() {
    const url = new URL(window.location.href);

    url.pathname = "/profile/current";
    window.location.href = url.href;
}

function validateEmail(currentEmail) {
    const email = $('#email').val();

    $.ajax({
        url: "/profiles/emailExists",
        type: 'get',
        data: {email},
        contentType: "application/json",
        success: function (exists) {
            if (exists && currentEmail !== email) {
                $('#incorrect-email-exists').slideDown();
                return false;
            } else {
                $('#incorrect-email-exists').slideUp();
            }
        }, error: function (request, status, error) {
            alertUser(request, status, error);
        }
    });

    if (!email.match("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
        $('#incorrect-email').slideDown();
        return false;
    } else {
        $('#incorrect-email').slideUp();
        return email;
    }
}

function validatePhoneNumber(currentNumber) {
    const phoneNumber = $('#phoneNumber').val();


    if (!phoneNumber.match("^\\s*\\+?375((33\\d{7})|(29\\d{7})|(44\\d{7}|)|(25\\d{7}))\\s*$")) {
        $('#incorrect-phoneNumber').slideDown();
        return false;
    } else {
        $('#incorrect-phoneNumber').slideUp();
    }

    $.ajax({
        url: "/profiles/phoneNumberExists",
        type: 'get',
        data: {phoneNumber},
        contentType: "application/json",
        success: function (exists) {
            if (exists && currentNumber !== phoneNumber) {
                $('#incorrect-phoneNumber-exists').slideDown();
                return false;
            } else {
                $('#incorrect-phoneNumber-exists').slideUp();
            }
        }, error: function (request, status, error) {
            alertUser(request, status, error);
        }
    });
    return phoneNumber;
}

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

$('#updatePasswordCheckbox').on('click', enablePasswordChange);

function enablePasswordChange() {
    const updatePasswordCheckbox = $('#updatePasswordCheckbox').is(':checked');

    if (updatePasswordCheckbox) {
        $('#password').attr('enabled', 'enabled');
        $('#password').removeAttr('disabled');
    } else {
        $('#password').attr('disabled', 'disabled');
        $('#password').removeAttr('enabled');

    }

    return updatePasswordCheckbox;
}

function validatePassword() {
    const password = $('#password').val();
    const length = password.length;
    const updatePasswordCheckbox = enablePasswordChange();

    if (updatePasswordCheckbox) {
        if (length > 30 || length < 6) {
            $('#incorrect-password').slideDown();
            return false;
        } else {
            $('#incorrect-password').slideUp();
            return password;
        }
    } else {
        return "not include";
    }
}

function updateProfile(profile) {
    const url = new URL(window.location.href);

    const firstName = validateFirstName(profile.firstName);
    const lastName = validateLastName(profile.lastName);
    let password = validatePassword();
    const email = validateEmail(profile.email);
    const phoneNumber = validatePhoneNumber(profile.phoneNumber);


    if (firstName && lastName && email && phoneNumber && password) {
        const restUrl = `/profiles/${profile.id}`;
        if (password === "not include") {
            password = null;
        }

        $.ajax({
            url: restUrl,
            type: 'put',
            data: JSON.stringify({
                firstName,
                lastName,
                password,
                email,
                phoneNumber
            }),
            contentType: "application/json",
            success: function () {
                goBack()
            }, error: function (request, status, error) {
                alertUser(request, status, error);
            }
        });
    }

    console.log(password)
}

const deleteProfile = function (profileId) {
    const url = new URL(window.location.href);

    const restUrl = '/profiles/' + profileId;

    console.log(restUrl);
    $.ajax({
        url: restUrl,
        type: 'DELETE',
        success: function () {
            url.pathname = "/logout";
            window.location.href = url.href;
        }, error: function (request, status, error) {
            alertUser(request, status, error);
        }
    });
}


