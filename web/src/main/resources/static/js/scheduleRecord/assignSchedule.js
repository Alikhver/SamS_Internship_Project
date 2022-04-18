const deleteArr = [];

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

let numCreated = 0;

const createRecords = function () {
    const url = new URL(window.location.href);

    let date = new Date($('#date').val());
    const options = {weekday: 'long', year: 'numeric', month: 'long', day: 'numeric'};
    const locale = "ru"
    validateDate();

    let startTime = parseInt($('#start-time').val());
    validateStartTime();

    let endTime = parseInt($('#end-time').val());
    validateEndTime()

    const workerId = parseInt(url.pathname.split('/')[4])


    if (validateDate() && validateEndTime() && validateStartTime()) {

        const restUrl = url;

        const data = {
          date,
          startTime,
          endTime,
            workerId
        };

        restUrl.pathname = '/records/create'
        $.ajax({
            url: restUrl,
            type: 'POST',
            data: JSON.stringify(data),
            contentType: "application/json",
            success: function () {
                const param = `${date.toLocaleDateString(locale, options)} Work time: ${startTime}:00 - ${endTime}:00`;

                $('#deleteRecords').after(
                    `<div class="option d-flex container-fluid col-8 px-0 my-3 justify-content-center">
                        <div class="col-9 container justify-content-center" align="center">
                            <p>${param}</p>
                        </div>
                        <div class="col-3 container" align="center">
                            <button id="${numCreated}" type="button" class="btn btn-danger" onclick="deleteBtn()">Cancel</button>
                        </div>
                    </div>`
                )

                numCreated = numCreated + 1;

                deleteArr.push(data)

                $('#date').val(null);
                $('#start-time').val(null);
                $('#end-time').val(null);
            }
        });
    }
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

    let date = new Date($('#d-date').val());
    validateD_Date();

    let startTime = parseInt($('#d-start-time').val());
    validateD_StartTime();

    let endTime = parseInt($('#d-end-time').val());
    validateD_EndTime()

    const workerId = parseInt(url.pathname.split('/')[4])


    if (validateD_Date() && validateD_EndTime() && validateD_StartTime()) {

        const restUrl = url;

        const data = {
            date,
            startTime,
            endTime,
            workerId
        };

        restUrl.pathname = '/records/cancel'
        $.ajax({
            url: restUrl,
            type: 'PUT',
            data: JSON.stringify(data),
            contentType: "application/json",
            success: function () {
                $('#d-date').val(null);
                $('#d-start-time').val(null);
                $('#d-end-time').val(null);
            }
        });
    }
}

const deleteBtn = function () {
    const createdNum = parseInt(event.srcElement.id);

    const data = deleteArr[createdNum];

    deleteRequest(data);

    $(`#${createdNum}`).parent().parent().remove();
}

const deleteRequest = function (data) {
    const restUrl = new URL(window.location.href);

    restUrl.pathname = '/records/cancel';

    $.ajax({
        url: restUrl,
        type: 'PUT',
        data: JSON.stringify(data),
        contentType: "application/json",
        success: function () {
        }
    });
}
