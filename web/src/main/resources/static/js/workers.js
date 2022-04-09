// $(".worker").on("click", function (workerId) {
//     const url = new URL(window.location.href);
//
//     url.searchParams.append('worker', workerId);
//     window.location.href = url.href.replace('/workers', '');
// });

const onclickWorker = function (workerId) {
    const url = new URL(window.location.href);

    if ($('#' + workerId).hasClass('worker')) {
        url.searchParams.set('worker', workerId);
        window.location.href = url.href.replace('/workers', '');
    }
};