const appendUtility = function (utilityId) {
    const url = new URL(window.location.href);

    const workerId = url.pathname.split('/')[4];

    $('#append' + utilityId).css('display', 'none');
    $('#remove' + utilityId).css('display', 'block');

    const restUrl = '/workers/' + workerId + '/addUtility/' + utilityId;

    $.ajax({
        url: restUrl,
        type: 'PATCH',
        success: function () {
            // goBack();
        }
    });

}

const removeUtility = function (utilityId) {
    const url = new URL(window.location.href);

    const workerId = url.pathname.split('/')[4];

    $('#remove' + utilityId).css('display', 'none');
    $('#append' + utilityId).css('display', 'block');

    const restUrl = '/workers/' + workerId + '/removeUtility/' + utilityId;

    $.ajax({
        url: restUrl,
        type: 'DELETE',
        success: function () {
        }, error: function (request, status, error) {
            alertUser(request, status, error);
        }
    });

}

const goBack = function () {
    const url = new URL(window.location.href);
    const workerId = url.pathname.split('/')[4];

    url.pathname = url.pathname.replace('/' + workerId + '/manageUtilities', '');
    console.log(url.href);
    window.location.href = url.href;
}