const onclickUtility = function (utilityId) {
    const url = new URL(window.location.href);

    if ($('#' + utilityId).hasClass('utility')) {
        url.searchParams.set('utility', utilityId);
        console.log(url.href.replace('/utilities', ''));
        window.location.href = url.href.replace('/utilities', '');
    }
};

const goBack = function () {
    const url = new URL(window.location.href);

    url.pathname = url.pathname.replace('/utilities', '');

    window.location.href = url.href;
}