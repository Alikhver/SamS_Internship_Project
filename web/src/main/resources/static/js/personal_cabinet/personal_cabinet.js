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

$('select').on('change', function () {
    const url = new URL(window.location.href);

    url.searchParams.set("lang", this.value);

    window.location.href = url.href;

});

function setLocale(lang) {
    $('option')
        .removeAttr('selected')
        .filter(`[value=${lang}]`)
        .attr('selected', true)
}

function goBack (referer) {
    if (referer === "null" || referer === null) {
        history.back();
    }

    window.location.href = referer;
}

$('#view-profile').on('click', function () {
    const url = new URL(window.location.href);

    url.pathname = '/profile/update'

    window.location.href = url.href;
});

$('#view-records').on('click', function () {
    const url = new URL(window.location.href);

    url.pathname = '/profile/records';

    window.location.href = url.href;
});