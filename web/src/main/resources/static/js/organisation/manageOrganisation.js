
const goBackM = function () {
    const url = new URL(window.location.href);
    url.pathname = url.pathname.replace('/manage', '');

    window.location.href = url.href;
}