$('#organisations').on('click', function () {
    console.log(window.location.href)
    window.location.href = '/organisation';
})

$('#profiles').on('click', function () {
    window.location.href = '/profile';
})



$('.profile-link').on('click', function () {
    const url = new URL(window.location.href);

    url.pathname = '/profile/current'
    window.location.href = url.href;
})