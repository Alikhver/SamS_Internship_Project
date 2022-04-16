$('.select-master').on('click', function () {
    const url = new URL(window.location.href);

    url.pathname = url.pathname + "/workers";

    window.location.href = url.href;
})

$('.select-master-redactor').on('click', function () {
    const url = new URL(window.location.href);

    url.pathname = url.pathname + "/workers";

    for (const key of url.searchParams.keys()) {
        console.log(key)
        url.searchParams.delete(key);
    }

    window.location.href = url.href;
})

$('.select-utility').on('click', function () {
    const url = new URL(window.location.href);

    url.pathname = url.pathname + "/utilities";

    window.location.href = url.href;
})

$('.select-utility-redactor').on('click', function () {
    const url = new URL(window.location.href);

    url.pathname = url.pathname + "/utilities";

    for (const key of url.searchParams.keys()) {
        url.searchParams.delete(key);
    }

    window.location.href = url.href;
})

$('.select-time').on('click', function () {

    if (!$('.select-time').hasClass('inactive')) {
        window.location.href = "#";
    }
})

$('.select-time-redactor').on('click', function () {
    const url = new URL(window.location.href);


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

$('.select-change-organisation').on('click', function () {
    const url = new URL(window.location.href);

    url.pathname = url.pathname + "/update";

    for (const key of url.searchParams.keys()) {
        console.log(key)
        url.searchParams.delete(key);
    }

    window.location.href = url.href;
});

$('#suspended').on('click', function () {
    $('#modal').modal('show');
})

const appendInactiveToSelectTime = function () {
    const url = new URL(window.location.href);
    const workerId = url.searchParams.get('worker');

    if (workerId === null || $('#suspended').length) {
        $('.select-time').addClass('inactive').removeClass('option');
    }
}