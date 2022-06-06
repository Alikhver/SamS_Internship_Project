const viewOrganisation = function (orgId) {
    const url = new URL(window.location.href);

    url.pathname = url.pathname + '/' + orgId;

    window.location.href = url.href;
}

const switchPage = function (page) {
    const url = new URL(window.location.href);

    url.searchParams.set('page', page);

    window.location.href = url.href;
}

const onPrevious = function () {
    const url = new URL(window.location.href);

    if (!$('#previous').hasClass('disabled')) {
        const active = $('.active');
        const currentPage = parseInt(active.children("a")[0].text);

        const page = (currentPage - 1).toString();
        url.searchParams.set('page', page);

        window.location.href = url.href;
    }
}

const onNext = function () {
    const url = new URL(window.location.href);

    if (!$('#next').hasClass('disabled')) {
        const active = $('.active');
        const currentPage = parseInt(active.children("a")[0].text);

        const page = (currentPage + 1).toString();
        url.searchParams.set('page', page);

        window.location.href = url.href;
    }
}

const goBack = function () {
    window.location.href = '/adminHome';
}

