$('.select-master').on('click', function () {
    const url = new URL(window.location.href);
    const pathArr = url.pathname.split('/');

    // const organisationId = parseInt(pathArr[pathArr.length - 1]);
    // const utilityId = url.searchParams.get('utility');
    // const workerId = url.searchParams.get('worker');

    url.pathname = url.pathname + "/workers"

    window.location.href = url;

    // if (utilityId === null || workerId !== null) {
    //     url.pathname = url.pathname + "/workers"
    // } else {
    //     url.searchParams.append('utility', utilityId);
    //     window.location.href = "/utility/" + localStorage.getItem("utilityId") + "/workers";
    //     window.location.href = organisationId + "/workers?" + utilityParam;
    // }
})

$('.select-utility').on('click', function () {
    const url = new URL(window.location.href);
    const pathArr = url.pathname.split('/');

    const organisationId = parseInt(pathArr[pathArr.length - 1]);
    const utilityId = url.searchParams.get('utility');
    const workerId = url.searchParams.get('worker');


    if (workerId === null || utilityId !== null) {
        window.location.href = window.location.pathname + "/utilities";
    } else {
        const workerParam = "worker=" + workerId;
        window.location.href = organisationId + "/utilities?" + workerParam;
    }
})

$('.select-time').on('click', function () {
    if (!$('.select-time').hasClass('inactive')) {
        window.location.href = "#";
    }
})

$('#trash-utility').on('click', function () {
    const url = new URL(window.location.href);
    url.searchParams.delete('utility');
    window.location.href = url.href;

    localStorage.removeItem("utilityId");
})

$('#trash-worker').on('click', function () {
    const url = new URL(window.location.href);
    url.searchParams.delete('worker');
    window.location.href = url.href;

    localStorage.removeItem("workerId");
})

const appendInactiveToSelectTime = function () {
    const url = new URL(window.location.href);
    const workerId = url.searchParams.get('worker');

    if (workerId === null) {
        $('.select-time').addClass('inactive').removeClass('option');
    }
}