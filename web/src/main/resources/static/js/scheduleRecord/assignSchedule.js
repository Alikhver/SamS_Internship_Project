const goBack = function () {
    const url = new URL(window.location.href);
    const workerId = parseInt(url.pathname.split('/')[4]);

    url.pathname = url.pathname.replace('/' + workerId + '/schedule', '');

    window.location.href = url.href;
}


const validateDate = function () {
    const inputVal = $('#date').val();
    const date = new Date(inputVal);

    if (date < new Date()) {
        $('#incorrect-date').slideDown();
        return false;
    } else {
        $('#incorrect-date').slideUp();
        return true;
    }
}

const validateStartTime = function () {
    const inputVal = parseInt($('#start-time').val());

    if (inputVal < 0 || inputVal > 24 || isNaN(inputVal)) {
        $('#incorrect-start-hour').slideDown();
        $('#end-time').attr('min', 0);
        return false;
    } else {
        $('#incorrect-start-hour').slideUp();
        $('#end-time').attr('min', inputVal);
        validateEndTime();
        return true;
    }
}

const validateEndTime = function () {
    const endVal = parseInt($('#end-time').val());
    let startVal = parseInt($('#start-time').val());

    if (isNaN(startVal)) {
        startVal = 0;
    }

    if (endVal > startVal && endVal <= 24) {
        $('#incorrect-end-hour').slideUp();
        $('#incorrect-end-hour1').slideUp();
        return true;
    } else if (endVal === startVal) {
        $('#incorrect-end-hour1').slideDown();
        $('#incorrect-end-hour').slideUp();
        return false;
    } else {
        $('#incorrect-end-hour').slideDown();
        $('#incorrect-end-hour1').slideUp();
        return false;
    }
}

$('#date').on('change', validateDate);
$('#start-time').on('change', validateStartTime);
$('#end-time').on('change', validateEndTime);


const createRecords = function () {
    const url = new URL(window.location.href);

    let date = $('#date').val();
    let startTime = $('#start-time').val();
    let endTime = $('#end-time').val();

    $('#createRecords').before(`<div class="option d-flex container-fluid col-3 px-0 my-3 justify-content-center">
    <p>${new Date(date)}</p>
</div>`)

    $('#date').val(null);
    $('#start-time').val(null);
    $('#end-time').val(null);
}


//Delete Records

const validateD_Date = function () {
    const inputVal = $('#d-date').val();
    const date = new Date(inputVal);

    if (date < new Date()) {
        $('#d-incorrect-date').slideDown();
        return false;
    } else {
        $('#d-incorrect-date').slideUp();
        return true;
    }
}

const validateD_StartTime = function () {
    const inputVal = parseInt($('#d-start-time').val());

    if (inputVal < 0 || inputVal > 24 || isNaN(inputVal)) {
        $('#d-incorrect-start-hour').slideDown();
        $('#d-end-time').attr('min', 0);
        return false;
    } else {
        $('#d-incorrect-start-hour').slideUp();
        $('#d-end-time').attr('min', inputVal);
        validateD_EndTime();
        return true;
    }
}

const validateD_EndTime = function () {
    const endVal = parseInt($('#d-end-time').val());
    let startVal = parseInt($('#d-start-time').val());

    if (isNaN(startVal)) {
        startVal = 0;
    }

    if (endVal > startVal && endVal <= 24) {
        $('#d-incorrect-end-hour').slideUp();
        $('#d-incorrect-end-hour1').slideUp();
        return true;
    } else if (endVal === startVal) {
        $('#d-incorrect-end-hour1').slideDown();
        $('#d-incorrect-end-hour').slideUp();
        return false;
    } else {
        $('#d-incorrect-end-hour').slideDown();
        $('#d-incorrect-end-hour1').slideUp();
        return false;
    }
}

$('#d-date').on('change', validateD_Date);
$('#d-start-time').on('change', validateD_StartTime);
$('#d-end-time').on('change', validateD_EndTime);



//Send delete Request
const deleteRecords = function () {
    const url = new URL(window.location.href);



    $('#d-date').val(null);
    $('#d-start-time').val(null);
    $('#d-end-time').val(null);
}
