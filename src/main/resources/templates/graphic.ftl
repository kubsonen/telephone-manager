<html lang="en" xmlns="http://www.w3.org/1999/html">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Font awesome -->
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css"
              integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <!-- Bootstrap Date picker -->
        <link rel="stylesheet" type="text/css" media="screen"
              href="http://tarruda.github.com/bootstrap-datetimepicker/assets/css/bootstrap-datetimepicker.min.css">

        <!-- Own styles -->
        <link rel="stylesheet" href="/standalone.css">
        <link rel="stylesheet" href="/clockpicker.css">
        <link rel="stylesheet" href="/app.css">
        <title>TStats - graphic</title>
    </head>

    <body>

        <style>
            .calendar-row > td {
                min-width:120px;
                height:100px;
            }
            .day-event {
                width: 100%;
                background-color: #03a9f3;
                color: white;
                font-weight: bold;
                font-size: 15px;
                margin-top: 2px;
                padding: 1px 2px;
                border: solid .1px black;
            }
            .day-event:hover {
                background: white;
                color: black;
            }
            .day-number {
                display: inline-block;
                border: solid 1px black;
                border-radius: 300px;
            }
            .day-number:hover {
                background: black;
                color: white;
            }
            .today{
                background: #ff3b30;
                color: white;
            }
        </style>

        <div class="container-fluid">
            <div class="row pb-4 pt-2">
                <div class="col-12">
                    <a href="/" type="button" class="btn btn-primary">Back to application</a>
                </div>
            </div>
            <div class="row py-2">
                <div class="col-md-12">
                    <h2 class="d-inline">
                        <i class="fas fa-chevron-left"></i>
                        July 2019
                        <i class="fas fa-chevron-right"></i>
                    </h2>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <table class="table table-bordered table-sm">
                        <thead class="thead-dark">
                        <tr>
                            <th scope="col">Monday</th>
                            <th scope="col">Tuesday</th>
                            <th scope="col">Wednesday</th>
                            <th scope="col">Thursday</th>
                            <th scope="col">Friday</th>
                            <th scope="col">Saturday</th>
                            <th scope="col">Sunday</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list calendar.weeks as week>
                            <tr class="calendar-row">

                                <#if week.days["1"]??>
                                    <td>
                                        <div class="text-right">
                                            <div
                                            <#if week.days["1"].today == true>
                                                class="day-number today px-2 mb-1"
                                            <#else>
                                                class="day-number px-2 mb-1"
                                            </#if>
                                                onclick="addCalendarEvent('${week.days["1"].time?date}')">
                                                <span>${week.days["1"].dayInMonth}</span>
                                            </div>
                                        </div>

                                        <#if week.days["1"].events??>
                                            <#list week.days["1"].events as event>
                                                <div class="day-event" onclick="infoCalendarEvent('${event.eventId?c}', '${week.days["1"].time?date}')">
                                                    ${event.shortDesc} : ${event.start?time} - ${event.end?time}
                                                </div>
                                            </#list>
                                        </#if>

                                    </td>
                                <#else>
                                    <td></td>
                                </#if>

                                <#if week.days["2"]??>
                                    <td>
                                        <div class="text-right">
                                            <div
                                            <#if week.days["2"].today == true>
                                                class="day-number today px-2 mb-1"
                                            <#else>
                                                class="day-number px-2 mb-1"
                                            </#if>
                                             onclick="addCalendarEvent('${week.days["2"].time?date}')">
                                                <span>${week.days["2"].dayInMonth}</span>
                                            </div>
                                        </div>

                                        <#if week.days["2"].events??>
                                            <#list week.days["2"].events as event>
                                                <div class="day-event" onclick="infoCalendarEvent('${event.eventId?c}', '${week.days["2"].time?date}')">
                                                    ${event.shortDesc} : ${event.start?time} - ${event.end?time}
                                                </div>
                                            </#list>
                                        </#if>

                                    </td>
                                <#else>
                                    <td></td>
                                </#if>

                                <#if week.days["3"]??>
                                    <td>
                                        <div class="text-right">
                                            <div
                                                <#if week.days["3"].today == true>
                                                    class="day-number today px-2 mb-1"
                                                <#else>
                                                    class="day-number px-2 mb-1"
                                                </#if>
                                                onclick="addCalendarEvent('${week.days["3"].time?date}')">
                                                <span>${week.days["3"].dayInMonth}</span>
                                            </div>
                                        </div>

                                        <#if week.days["3"].events??>
                                            <#list week.days["3"].events as event>
                                                <div class="day-event" onclick="infoCalendarEvent('${event.eventId?c}', '${week.days["3"].time?date}')">
                                                    ${event.shortDesc} : ${event.start?time} - ${event.end?time}
                                                </div>
                                            </#list>
                                        </#if>

                                    </td>
                                    <#else>
                                    <td></td>
                                </#if>

                                <#if week.days["4"]??>
                                    <td>
                                        <div class="text-right">
                                            <div
                                                <#if week.days["4"].today == true>
                                                    class="day-number today px-2 mb-1"
                                                <#else>
                                                    class="day-number px-2 mb-1"
                                                </#if>
                                                onclick="addCalendarEvent('${week.days["4"].time?date}')">
                                                <span>${week.days["4"].dayInMonth}</span>
                                            </div>
                                        </div>

                                        <#if week.days["4"].events??>
                                            <#list week.days["4"].events as event>
                                                <div class="day-event" onclick="infoCalendarEvent('${event.eventId?c}', '${week.days["4"].time?date}')">
                                                    ${event.shortDesc} : ${event.start?time} - ${event.end?time}
                                                </div>
                                            </#list>
                                        </#if>

                                    </td>
                                    <#else>
                                    <td></td>
                                </#if>

                                <#if week.days["5"]??>
                                    <td>
                                        <div class="text-right">
                                            <div
                                                <#if week.days["5"].today == true>
                                                    class="day-number today px-2 mb-1"
                                                <#else>
                                                    class="day-number px-2 mb-1"
                                                </#if>
                                                onclick="addCalendarEvent('${week.days["5"].time?date}')">
                                                <span>${week.days["5"].dayInMonth}</span>
                                            </div>
                                        </div>

                                        <#if week.days["5"].events??>
                                            <#list week.days["5"].events as event>
                                                <div class="day-event" onclick="infoCalendarEvent('${event.eventId?c}', '${week.days["5"].time?date}')">
                                                    ${event.shortDesc} : ${event.start?time} - ${event.end?time}
                                                </div>
                                            </#list>
                                        </#if>

                                    </td>
                                    <#else>
                                    <td></td>
                                </#if>

                                <#if week.days["6"]??>
                                    <td>
                                        <div class="text-right">
                                            <div
                                                <#if week.days["6"].today == true>
                                                    class="day-number today px-2 mb-1"
                                                <#else>
                                                    class="day-number px-2 mb-1"
                                                </#if>
                                                onclick="addCalendarEvent('${week.days["6"].time?date}')">
                                                <span>${week.days["6"].dayInMonth}</span>
                                            </div>
                                        </div>

                                        <#if week.days["6"].events??>
                                            <#list week.days["6"].events as event>
                                                <div class="day-event" onclick="infoCalendarEvent('${event.eventId?c}', '${week.days["6"].time?date}')">
                                                    ${event.shortDesc} : ${event.start?time} - ${event.end?time}
                                                </div>
                                            </#list>
                                        </#if>

                                    </td>
                                    <#else>
                                    <td></td>
                                </#if>

                                <#if week.days["7"]??>
                                    <td>
                                        <div class="text-right">
                                            <div
                                            <#if week.days["7"].today == true>
                                                class="day-number today px-2 mb-1"
                                            <#else>
                                                class="day-number px-2 mb-1"
                                            </#if>
                                            onclick="addCalendarEvent('${week.days["7"].time?date}')">
                                                <span>${week.days["7"].dayInMonth}</span>
                                            </div>
                                        </div>

                                        <#if week.days["7"].events??>
                                            <#list week.days["7"].events as event>
                                                <div class="day-event" onclick="infoCalendarEvent('${event.eventId?c}', '${week.days["7"].time?date}')">
                                                    ${event.shortDesc} : ${event.start?time} - ${event.end?time}
                                                </div>
                                            </#list>
                                        </#if>

                                    </td>
                                <#else>
                                    <td></td>
                                </#if>


                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>



        <div class="modal fade" id="addCalendarEventModalCenter" tabindex="-1" role="dialog" aria-labelledby="resetModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addCalendarEventModalCenterTitle"></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="addEventCalendarModalForm">
                            <div class="form-group">
                                <label for="addEventDateHourFrom">Time from</label>
                                <div class="input-group clockpicker">
                                    <input id="addEventDateHourFrom" type="text" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="addEventDateHourTo">Time to</label>
                                <div class="input-group clockpicker">
                                    <input id="addEventDateHourTo" type="text" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="addEventKind">Kind of event</label>
                                <select class="form-control" id="addEventKind">
                                    <option value="WORK">WORK</option>
                                    <option value="OTHER">OTHER</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="addEventNotes">Event notes</label>
                                <textarea class="form-control" id="addEventNotes" rows="3"></textarea>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal" id="addEventCalendarDateSave">Save</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="showCalendarEventModalCenter" tabindex="-1" role="dialog" aria-labelledby="resetModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="showCalendarEventModalCenterTitle"></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="showEventCalendarModalForm">
                            <div class="form-group">
                                <label for="showEventDateHourFrom">Time from</label>
                                <div class="input-group clockpicker">
                                    <input id="showEventDateHourFrom" type="text" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="showEventDateHourTo">Time to</label>
                                <div class="input-group clockpicker">
                                    <input id="showEventDateHourTo" type="text" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="showEventKind">Kind of event</label>
                                <select class="form-control" id="showEventKind">
                                    <option value="WORK">WORK</option>
                                    <option value="OTHER">OTHER</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="showEventNotes">Event notes</label>
                                <textarea class="form-control" id="showEventNotes" rows="3"></textarea>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal" id="saveShowEventModal">Save</button>
                        <button type="button" class="btn btn-danger" data-dismiss="modal"id="deleteShowEventModal">Delete</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Window to show errors -->
        <#include "modal-error.ftl">

        <!-- Optional JavaScript -->
        <!-- jQuery first, then Popper.js, then Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
                integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
                crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
                integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
                crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
        <script type="text/javascript"
                src="http://tarruda.github.com/bootstrap-datetimepicker/assets/js/bootstrap-datetimepicker.min.js">
        </script>
        <script src="clockpicker.js"></script>
        <script src="app-ajax.js"></script>
        <script src="app-graphic.js"></script>
    </body>
</html>