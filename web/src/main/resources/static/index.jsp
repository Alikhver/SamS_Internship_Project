<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="ru">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <title>Hello, world!</title>
    <link rel="stylesheet" href="organisation.css"/>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
            crossorigin="anonymous"
    />
    <link rel="stylesheet" href="../main.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            $.get("http://localhost:8080/organisations/1", function (data) {
                //  передаем и загружаем данные с сервера с помощью HTTP запроса методом GET
                $("#org_name").text(data.name);
                $("#org_desc").text(data.description);
                $("#org_address").text(data.address);
            });
        });
    </script>
</head>
<body class="container" style="background-color: rgb(247, 247, 247)">
<div
        class="d-flex container col-5 justify-content-center navbar sticky-top fixed-top"
        style="background-color: rgb(255, 255, 255)"
>
    <div class="col-10 d-flex flex-column">
        <p id="org_name" class="text-center p-2" style="margin: 0em">ГУД</p>
        <p id="org_desc" class="p-2" style="margin: 0em">
            выбирай мастера по общим интересам
        </p>
        <p id="org_address" class="p-2" style="margin: 0em">ул. Брилевская 9</p>
    </div>
    <div>
<%--        <a href="../personal cabinet/personal_cabinet.html">--%>
        <a href="<c:url value="personal_cabinet/personal_cabinet.html">">
            <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="30"
                    fill="currentColor"
                    class="bi bi-person"
                    viewBox="0 0 16 16"
            >
                <path
                        d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z"
                />
            </svg>
        </a>
    </div>
</div>

<div
        class="options d-flex container col-5 my-3"
        style="background-color: rgb(255, 255, 255); border-radius: 15px"
>
    <a href="../masters/masters.html">
        <div class="d-flex">
            <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="30"
                    fill="currentColor"
                    class="bi bi-people"
                    viewBox="0 0 16 16"
            >
                <path
                        d="M15 14s1 0 1-1-1-4-5-4-5 3-5 4 1 1 1 1h8zm-7.978-1A.261.261 0 0 1 7 12.996c.001-.264.167-1.03.76-1.72C8.312 10.629 9.282 10 11 10c1.717 0 2.687.63 3.24 1.276.593.69.758 1.457.76 1.72l-.008.002a.274.274 0 0 1-.014.002H7.022zM11 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4zm3-2a3 3 0 1 1-6 0 3 3 0 0 1 6 0zM6.936 9.28a5.88 5.88 0 0 0-1.23-.247A7.35 7.35 0 0 0 5 9c-4 0-5 3-5 4 0 .667.333 1 1 1h4.216A2.238 2.238 0 0 1 5 13c0-1.01.377-2.042 1.09-2.904.243-.294.526-.569.846-.816zM4.92 10A5.493 5.493 0 0 0 4 13H1c0-.26.164-1.03.76-1.724.545-.636 1.492-1.256 3.16-1.275zM1.5 5.5a3 3 0 1 1 6 0 3 3 0 0 1-6 0zm3-2a2 2 0 1 0 0 4 2 2 0 0 0 0-4z"
                />
            </svg>
            <div class="col-10">
                <p class="p-1 my-3 text-center">Выбрать Мастера</p>
            </div>
        </div>
    </a>
</div>

<div
        class="options d-flex container col-5 my-3"
        style="background-color: rgb(255, 255, 255); border-radius: 15px"
>
    <a href="../utilities/utilities.html">
        <div class="d-flex">
            <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="30"
                    fill="currentColor"
                    class="bi bi-postcard"
                    viewBox="0 0 16 16"
            >
                <path
                        fill-rule="evenodd"
                        d="M2 2a2 2 0 0 0-2 2v8a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V4a2 2 0 0 0-2-2H2ZM1 4a1 1 0 0 1 1-1h12a1 1 0 0 1 1 1v8a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V4Zm7.5.5a.5.5 0 0 0-1 0v7a.5.5 0 0 0 1 0v-7ZM2 5.5a.5.5 0 0 1 .5-.5H6a.5.5 0 0 1 0 1H2.5a.5.5 0 0 1-.5-.5Zm0 2a.5.5 0 0 1 .5-.5H6a.5.5 0 0 1 0 1H2.5a.5.5 0 0 1-.5-.5Zm0 2a.5.5 0 0 1 .5-.5H6a.5.5 0 0 1 0 1H2.5a.5.5 0 0 1-.5-.5ZM10.5 5a.5.5 0 0 0-.5.5v3a.5.5 0 0 0 .5.5h3a.5.5 0 0 0 .5-.5v-3a.5.5 0 0 0-.5-.5h-3ZM13 8h-2V6h2v2Z"
                />
            </svg>
            <div class="col-10">
                <p class="p-1 my-3 text-center">Выбрать Услугу</p>
            </div>
        </div>
    </a>
</div>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
        crossorigin="anonymous"
></script>
</body>
</html>
