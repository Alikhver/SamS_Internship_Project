$('#login').on('click', function () {
    const url = new URL(window.location.href);
    url.pathname = "/login";
    window.location.href = url.href;
});

$('#logout').on('click', function () {
    const url = new URL(window.location.href);
    url.pathname = "/logout";
    window.location.href = url.href;
});