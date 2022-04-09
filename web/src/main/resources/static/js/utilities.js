const onclickUtility = function (utilityId) {
    const url = new URL(window.location.href);

    if ($('#' + utilityId).hasClass('utility')) {
        url.searchParams.set('utility', utilityId);
        console.log(url.href.replace('/utilities', ''));
        window.location.href = url.href.replace('/utilities', '');
    }
};