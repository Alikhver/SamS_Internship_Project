$('#create').on('click', function () {
    const url = new URL(window.location.href);

    const organisationId = parseInt(url.pathname.split('/')[2]);
    const utilityId = parseInt(url.pathname.split('/')[4]);

    const name = $('#name').val();
    validateName();

    const price = parseFloat($('#price').val());
    validatePrice();

    const description = $('#description').val();
    validateDescription();

    if (validateName() && validatePrice() && validateDescription()) {
        const restUrl = '/utilities';
        const utility = {
            name,
            price,
            description,
            organisationId
        }

        $.ajax({
            url: restUrl,
            type: 'POST',
            data: JSON.stringify(utility),
            contentType: "application/json",
            success: function () {
                goBackCreate();
            }, error: function (request, status, error) {
                alertUser(request, status, error);
            }
        });
    }
})

const goBackCreate = function () {
    const url = new URL(window.location.href);
    url.pathname = url.pathname.replace('/create', '');

    window.location.href = url.href;
}