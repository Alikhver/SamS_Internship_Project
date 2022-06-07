const alertUser = function (request, status, error) {
    const response = request.responseJSON;
    let message;
    if (response.status == null) {
        message = response.message;
    } else {
        message = response.status + ': ' + response.message;
    }
    alert(message);
}