$('#update').on('click', function () {
    const url = new URL(window.location.href);

    const organisationId = parseInt(url.pathname.split('/')[2]);

    const name = $('#name').val();

    const address = $('#address').val();

    const description = $('#description').val();
    
    if (validateName() && validateAddress() && validateDescription()) {
        const restUrl = '/organisations/' + organisationId;
        const organisation = {
            name,
            address,
            description
        }

        $.ajax({
            url: restUrl,
            type: 'PUT',
            data: JSON.stringify(organisation),
            contentType: "application/json",
            success: function () {
                goBack();
            }
        });
    }
});

$('#delete').on('click', function () {
    const url = new URL(window.location.href);
    const organisationId = parseInt(url.pathname.split('/')[2]);


    const restUrl = '/organisations/' + organisationId;

    $.ajax({
        url: restUrl,
        type: 'DELETE',
        success: function () {
            window.location.href = '/#';
        //    TODO insert correct link
        }
    });
});


$('#name').on('change', validateName)

$('#address').on('change', validateAddress)

$('#description').on('change', validateDescription)

function validateName() {
    const input = $('#name');
    const length = input.val().length;

    if (length < 2 || length > 30) {
        $('#incorrect-name').slideDown();
        return false;
    } else {
        input.parent().next().slideUp();
        return true;
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
        return true;
    }
}

function validateDescription() {
    const input = $('#description');
    const length = input.val().length;

    if (length < 30 || length > 120) {
        $('#incorrect-description').slideDown();
        return false;
    } else {
        input.parent().next().slideUp();
        return true;
    }
}

const goBack = function () {
    const url = new URL(window.location.href);
    url.pathname = url.pathname.replace('/update', '');

    window.location.href = url.href;
}