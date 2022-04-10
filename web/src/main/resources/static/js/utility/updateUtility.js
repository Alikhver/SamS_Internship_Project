$('#update').on('click', function () {
    const url = new URL(window.location.href);

    const utilityId = parseInt(url.pathname.split('/')[4]);
    const name = $('#name').val();

    const price = parseFloat($('#price').val());

    const description = $('#description').val();

    if (validateName() && validatePrice() && validateDescription()) {
        const restUrl = '/utilities/' + utilityId;
        const utility = {
            name,
            price,
            description
        }

        $.ajax({
            url: restUrl,
            type: 'PUT',
            data: JSON.stringify(utility),
            contentType: "application/json",
            success: function () {
                goBack();
            }
        });
    }
});

$('#delete').on('click', function () {
    const url = new URL(window.location.href);
    const utilityId = parseInt(url.pathname.split('/')[4]);

    const restUrl = '/utilities/' + utilityId;

    $.ajax({
        url: restUrl,
        type: 'DELETE',
        success: function () {
            goBack()
        }
    });
});


$('#name').on('change', validateName)

$('#price').on('change', validatePrice)

$('#description').on('change', validateDescription)

function validateName() {
    const input = $('#name');
    const length = input.val().length;

    if (length < 2 || length > 30) {
        $('#incorrect-name').slideDown();
        return false;
    } else {
        $('#incorrect-name').slideUp();
        return true;
    }
}

function validatePrice() {

    const price = parseFloat($('#price').val());
    console.log(price);
    console.log(typeof price);
    console.log(isNaN(price));

    if (price < 0 || isNaN(price) ) {
        $('#incorrect-price').slideDown();
        return false;
    } else {
        $('#incorrect-price').slideUp();
        return true;
    }
}

function validateDescription() {
    const input = $('#description');
    const length = input.val().length;

    if (length < 15 || length > 40) {
        $('#incorrect-description').slideDown();
        return false;
    } else {
        $('#incorrect-description').slideUp();
        return true;
    }
}

const goBack = function () {
    const url = new URL(window.location.href);
    const utilityId = parseInt(url.pathname.split('/')[4]);
    url.pathname = url.pathname.replace('/' + utilityId + '/update', '');

    window.location.href = url.href;
}