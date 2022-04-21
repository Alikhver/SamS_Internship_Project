const viewProfile = function (profileId) {
    const url = new URL(window.location.href);

    url.pathname = url.pathname + '/' + profileId + '/manage'

    window.location.href = url.href;
}


const goBack = function () {
    window.location.href = '/';
}