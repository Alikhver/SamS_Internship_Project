const goBack = function () {
    const url = new URL(window.location.href);
    url.pathname = url.pathname.replace('/select-worker', '')

    window.location.href = url.href;
}

const gotoAssignSchedule = function (workerId) {
    const url = new URL(window.location.href);
    url.pathname = url.pathname + '/' + workerId + '/schedule';

    window.location.href = url.href;

}