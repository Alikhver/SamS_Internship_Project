$('.btn').on('click', function () {
    const url = new URL(window.location.href);

    const organisationId = parseInt(url.pathname.split('/')[2]);

    const firstName = $('#first-name').val();
    validateFirstName();

    const lastName = $('#last-name').val();
    validateLastName();

    const description = $('#description').val();
    validateDescription();

    const worker = {
        firstName,
        lastName,
        description,
        organisationId
    }

    const restUrl = '/workers/';

    $.ajax({
        url: restUrl,
        type: 'POST',
        data: JSON.stringify(worker),
        contentType: "application/json",
        success: function () {
            url.pathname = url.pathname.replace('/create', '');
            window.location.href = url.href;
        }
    });
});

