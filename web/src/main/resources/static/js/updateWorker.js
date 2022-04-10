const updateWorker = function (workerId) {
    const url = new URL(window.location.href);

    const firstName = $('#first-name').val();
    validateFirstName();

    const lastName = $('#last-name').val();
    validateLastName();

    const description = $('#description').val();
    validateDescription();


    const worker = {
        firstName,
        lastName,
        description
    }

    const restUrl = '/workers/' + workerId;
    if (validateFirstName() && validateLastName() && validateDescription()) {
        $.ajax({
            url: restUrl,
            type: 'PUT',
            data: JSON.stringify(worker),
            contentType: "application/json",
            success: function () {
                url.pathname = url.pathname.replace('/' + workerId + '/update', '');
                window.location.href = url.href;
            }
        });
    }
}

$('#first-name').on('change', function () {
    validateFirstName();
})

$('#last-name').on('change', function () {
    validateLastName();
})

$('#description').on('change', function () {
    validateDescription();
})

function validateFirstName() {
    const input = $('#first-name');
    const length = input.val().length;

    if (length < 3 || length > 45) {
        $('#incorrect-first-name').slideDown();
        return false;
    } else {
        input.parent().next().slideUp();
        return true;
    }
}

function validateLastName() {
    const input = $('#last-name');
    const length = input.val().length;

    if (length < 3 || length > 45) {
        $('#incorrect-last-name').slideDown();
        return false;
    } else {
        input.parent().next().slideUp();
        return true;
    }
}

function validateDescription() {
    const input = $('#description');
    const length = input.val().length;

    if (length < 15) {
        $('#incorrect-description').slideDown();
        return false;
    } else {
        input.parent().next().slideUp();
        return true;
    }
}

const deleteWorker = function (workerId) {
    const url = new URL(window.location.href);

    const restUrl = '/workers/' + workerId;

    $.ajax({
        url: restUrl,
        type: 'DELETE',
        success: function() {
            url.pathname = url.pathname.replace('/' + workerId + '/update', '');
            window.location.href = url.href;
        }
    });
}

const goBack = function () {
    const url = new URL(window.location.href);
    const organisationId = parseInt(url.pathname.split('/')[2]);

    window.location.href = '/organisation/' + organisationId + '/workers';
}