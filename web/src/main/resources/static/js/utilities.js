$(".utility").on("click", function () {
    const organisationId = localStorage.getItem("organisationId")
    window.location.href = "../" + organisationId;
});