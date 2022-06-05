const alertUser = function (request, status, error) {
    const response = request.responseJSON;
    const message = response.status + ': ' + response.message;
    alert(message);
}