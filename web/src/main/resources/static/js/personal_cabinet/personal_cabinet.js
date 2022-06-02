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
    if (referer == null) {
        history.back();
    }
    const url = new URL(window.location.href);
    url.href = referer;
    window.location.href = url.href;
}