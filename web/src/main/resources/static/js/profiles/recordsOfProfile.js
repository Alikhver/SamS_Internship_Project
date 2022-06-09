function goBack() {
    const url = new URL(window.location.href);

    url.pathname = "/profile/current";
    window.location.href = url.href;
}

const cancelRecord = function (recordId) {
    const restUrl = `/records/${recordId}/cancel`;
    $.ajax({
        url: restUrl,
        type: 'put',
        contentType: "application/json",
        success: function () {
            $(`#status${recordId}`).text("CANCELED");
            $(`#btn${recordId}`).remove();
        }, error: function (request, status, error) {
            alertUser(request, status, error);
        }
    });
}