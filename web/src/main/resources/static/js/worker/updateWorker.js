const updateWorker = function (workerId) {
    const url = new URL(window.location.href);

    const firstName = $('#first-name').val();

    const lastName = $('#last-name').val();

    const description = $('#description').val();

    if (validateFirstName() && validateLastName() && validateDescription()) {
        const restUrl = '/workers/' + workerId;
        const worker = {
            firstName,
            lastName,
            description
        }

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

$('#first-name').on('change', validateFirstName)

$('#last-name').on('change', validateLastName)

$('#description').on('change', validateDescription)

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
        success: function () {
            url.pathname = url.pathname.replace('/' + workerId + '/update', '');
            window.location.href = url.href;
        }
    });
}

function manageUtilities(workerId) {
    const url = new URL(window.location.href);

    url.pathname = url.pathname.replace('/update', '/manageUtilities');

    window.location.href = url.href;
}

const goBack = function () {
    const url = new URL(window.location.href);
    const organisationId = parseInt(url.pathname.split('/')[2]);

    window.location.href = '/organisation/' + organisationId + '/workers';
}