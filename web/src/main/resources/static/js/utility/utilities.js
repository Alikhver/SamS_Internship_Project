const onclickUtility = function (utilityId) {
    const url = new URL(window.location.href);

    if ($('#' + utilityId).hasClass('utility')) {
        url.searchParams.set('utility', utilityId);
        console.log(url.href.replace('/utilities', ''));
        window.location.href = url.href.replace('/utilities', '');
    }
};

const updateUtility = function (utilityId) {
    const url = new URL(window.location.href);

    url.pathname = url.pathname + '/' + utilityId + '/update'

    window.location.href = url.href;
}

$('#createUtility').on('click', function () {
    const url = new URL(window.location.href);

    url.pathname = url.pathname + '/create';

    window.location.href = url.href;
})


const goBack = function () {
    const url = new URL(window.location.href);

    url.pathname = url.pathname.replace('/utilities', '');

    window.location.href = url.href;
}