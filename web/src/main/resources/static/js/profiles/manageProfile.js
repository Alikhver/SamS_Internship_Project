const deleteProfile = function (profileId) {
    const url = new URL(window.location.href);

    const restUrl = '/profiles/' + profileId;

    console.log(restUrl);
    $.ajax({
        url: restUrl,
        type: 'DELETE',
        success: function () {
            goBack();
        }, error: function (request, status, error) {
            alertUser(request, status, error);
        }
    });
}

const goBack = function () {
    const url = new URL(window.location.href);

    const profileId = parseInt(url.pathname.split('/')[2]);

    url.pathname = url.pathname.replace('/' + profileId + '/manage', '');

    window.location.href = url.href;
}