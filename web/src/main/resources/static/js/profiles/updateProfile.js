function goBack() {
    window.location.href = "/profile/current";
}

$('#phoneNumber').on('change', validatePhoneNumber);
$('#email').on('change', validateEmail);

function validateEmail() {
    const email = $('#email').val();

    $.ajax({
        url: "/profiles/emailExists",
        type: 'get',
        data: {email},
        contentType: "application/json",
        success: function (data) {
            if (data) {
                $('#incorrect-email-exists').slideDown();
                return false;
            } else {
                $('#incorrect-email-exists').slideUp();
            }
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

function validatePhoneNumber() {
    const phoneNumber = $('#phoneNumber').val();


    if (!phoneNumber.match("^\\s*\\+?375((33\\d{7})|(29\\d{7})|(44\\d{7}|)|(25\\d{7}))\\s*$")) {
        $('#incorrect-phoneNumber').slideDown();
        return false;
    } else {
        $('#incorrect-phoneNumber').slideUp();
    }

    // phoneNumber.replace('+', "%2B");

    $.ajax({
        url: "/profiles/phoneNumberExists",
        type: 'get',
        data: {phoneNumber},
        contentType: "application/json",
        success: function (data) {
            if (data) {
                $('#incorrect-phoneNumber-exists').slideDown();
                return false;
            } else {
                $('#incorrect-phoneNumber-exists').slideUp();
                return phoneNumber;
            }
        }
    });
}