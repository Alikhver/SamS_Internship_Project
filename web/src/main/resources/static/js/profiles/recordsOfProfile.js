function goBack() {
    window.location.href = '/profile/current';
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

        }
    });
}