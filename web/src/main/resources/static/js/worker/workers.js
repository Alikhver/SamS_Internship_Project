const onclickUser = function (workerId) {
    const url = new URL(window.location.href);

    if ($('#' + workerId).hasClass('worker')) {
        url.searchParams.set('worker', workerId);
        window.location.href = url.href.replace('/workers', '');
    }
};

$('#createWorker').on('click', function () {
   const url = new URL(window.location.href);
   url.pathname = url.pathname + '/create';
   window.location.href = url.href;
});

const gotoUpdateWorker = function (workerId) {
    const url = new URL(window.location.href);

    window.location.href = url.href + '/' + workerId + '/update';
}

const goBack = function () {
    const url = new URL(window.location.href);

    url.pathname = url.pathname.replace('/workers', '');

    window.location.href = url.href;
}