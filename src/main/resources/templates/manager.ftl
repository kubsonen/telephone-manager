<!doctype html>
<html lang="en" xmlns="http://www.w3.org/1999/html">

    <head>
        <#include "manager/manager-head.ftl">
        <!--Chart CSS-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.css">
    </head>
    <body>

        <#include "manager/manager-navbar.ftl">
        <div class="mt-4 container">
            <div class="row">
                <div class="col-12">
                    <canvas class="w-100" id="line-chart"></canvas>
                </div>
            </div>
        </div>

        <#include "manager/manager-js.ftl">
        <!--Chart JS-->
        <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
        <!--Chart data-->
        <script src="app-charts.js"></script>
    </body>

</html>