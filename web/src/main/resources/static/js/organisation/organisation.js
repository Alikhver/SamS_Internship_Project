//USER & UNAUTHENTICATED

$('.select-master').on('click', function () {
    const url = new URL(window.location.href);

    url.pathname = url.pathname + "/workers";

    window.location.href = url.href;
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

$('.select-time').on('click', function () {

    if (!$('.select-time').hasClass('inactive')) {
        window.location.href = "#";
    }
})

$('.select-utility').on('click', function () {
    const url = new URL(window.location.href);

    url.pathname = url.pathname + "/utilities";

    window.location.href = url.href;
})

const appendInactiveToSelectTime = function () {
    const url = new URL(window.location.href);
    const workerId = url.searchParams.get('worker');

    if (workerId === null || $('#suspended').length) {
        $('.select-time').addClass('inactive').removeClass('option');
    }
}


//REDACTOR
$('.select-master-redactor').on('click', function () {
    const url = new URL(window.location.href);

    url.pathname = url.pathname + "/workers";

    for (const key of url.searchParams.keys()) {
        console.log(key)
        url.searchParams.delete(key);
    }

    window.location.href = url.href;
})

$('.select-time-redactor').on('click', function () {
    const url = new URL(window.location.href);
    url.pathname = url.pathname + '/select-worker'

    window.location.href = url.href;
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

$('.select-utility-redactor').on('click', function () {
    const url = new URL(window.location.href);

    url.pathname = url.pathname + "/utilities";

    for (const key of url.searchParams.keys()) {
        url.searchParams.delete(key);
    }

    window.location.href = url.href;
})

//ADMIN

$('.select-time-admin').on('click', function () {
    const url = new URL(window.location.href);
    url.pathname = url.pathname + '/#'

    window.location.href = url.href;
})

$('.select-utility-admin').on('click', function () {
    const url = new URL(window.location.href);

    url.pathname = url.pathname + "/utilities";

    for (const key of url.searchParams.keys()) {
        url.searchParams.delete(key);
    }

    window.location.href = url.href;
})

$('.select-master-admin').on('click', function () {
    const url = new URL(window.location.href);

    url.pathname = url.pathname + "/workers";

    for (const key of url.searchParams.keys()) {
        console.log(key)
        url.searchParams.delete(key);
    }

    window.location.href = url.href;
})

$('.select-suspend-organisation-admin').on('click', function () {
    const url = new URL(window.location.href);

    url.pathname = url.pathname + "/manage";

    for (const key of url.searchParams.keys()) {
        console.log(key)
        url.searchParams.delete(key);
    }

    window.location.href = url.href;
});

$('#back').on('click', function () {
    const url = new URL(window.location.href);

    const orgId = parseInt(url.pathname.split('/')[2]);
    url.pathname = '/organisation';

    window.location.href = url.href;
});


//COMMON

$('#suspended').on('click', function () {
    $('#modal').modal('show');
})

$('.profile-link').on('click', function () {
    const url = new URL(window.location.href);

    url.pathname = '/profile/current'
    window.location.href = url.href;
})