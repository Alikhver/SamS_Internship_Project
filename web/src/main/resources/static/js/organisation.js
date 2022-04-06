var data = {
    workerId,
    utilityId,
    organisationId
};

$(document).ready(function () {
    $("#select-time").onclick(function () {
            // if (localStorage.getItem("workerId") === null) {
            window.location.href = '~/workers';
            // }
        }
    )
})

const keepOrganisation = function (orgId) {
    localStorage.setItem('organisationId', orgId);
    data.organisationId = orgId;
}