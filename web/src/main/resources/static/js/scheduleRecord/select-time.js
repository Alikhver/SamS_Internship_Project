const goBack = function () {
    const url = new URL(window.location.href);
    const workerId = parseInt(url.pathname.split('/')[4]);

    url.pathname = url.pathname.replace('/records', '');
    url.searchParams.delete("date");

    window.location.href = url.href;
}

$('#datepicker1').on('change', function () {
    const date = validateDateOnDelete();
    if (date) {
        const url = new URL(window.location.href);
        url.searchParams.set("date", date.toString());
        window.location.href = url.href;
    }
})

const validateDateOnDelete = function () {
    const inputVal = $('#date1').val();
    const date = new Date(inputVal);

    if (date < new Date()) {
        $('#incorrect-date1').slideDown();
        return false;
    } else {
        $('#incorrect-date1').slideUp();
        return date;
    }
}

function chooseTime(recordId) {
    const url = new URL(window.location.href);
    url.searchParams.set("record", recordId);

    url.pathname = url.pathname.replace('/records', '');

    url.searchParams.delete("date");

    window.location.href = url.href;
}