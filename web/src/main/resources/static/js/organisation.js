$('#select-master').on('click', function () {
    const organisationId = localStorage.getItem("organisationId");
    const utilityId = localStorage.getItem("utilityId");
    const workerId = localStorage.getItem("workerId");

    if (utilityId === null || workerId !== null) {
        window.location.href = window.location.pathname + "/workers"
    } else {
        const utilityParam = "utility=" + utilityId;
        window.location.href = "/utility/" + localStorage.getItem("utilityId") + "/workers";
        window.location.href = organisationId + "/workers?" + utilityParam;
    }
})

$('#select-utility').on('click', function () {
    const organisationId = localStorage.getItem("organisationId");
    const utilityId = localStorage.getItem("utilityId");
    const workerId = localStorage.getItem("workerId");


    if (workerId === null || utilityId !== null) {
        window.location.href = window.location.pathname + "/utilities";
    } else {
        const workerParam = "worker=" + workerId;
        window.location.href = organisationId + "/utilities?" + workerParam;
    }
})

$('#select-time').on('click', function () {
    if ($('#select-time').hasClass('inactive')) {
        return;
    } else {
        window.location.href = "#";
    }

})

const appendInactiveToSelectTime = function () {
    const utilityId = localStorage.getItem("utilityId");
    const workerId = localStorage.getItem("workerId");

    if (utilityId === null || workerId === null) {
        $('#select-time').addClass('inactive').removeClass('option');
    }
}