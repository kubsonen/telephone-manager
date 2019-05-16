<!doctype html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Font awesome -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>TStats</title>
</head>

<body>

    <#include "chat.ftl">

    <!-- add telephones -->
    <div class="container">
        <form method="post">
            <div class="row mt-4">
                <div class="col-12">
                    <div class="form-group">
                        <label for="note"><b>Add telephone</b></label>
                        <input type="text" class="form-control" id="note" name="note" placeholder="Note">
                    </div>
                </div>
                <!-- col -->
            </div>
            <!-- row -->
            <div class="row">
                <div class="col-12">
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary w-100">Add telephone</button>
                    </div>
                </div>
                <!-- col -->
            </div>
            <!-- row -->
        </form>
    </div>
    <!-- container -->

    <hr class="my-4">

    <div class="container">
        <form action="" method="get" id="changeRowsOnPage">
            <div class="row">
                <div class="col-md-3">
                    <div class="form-group">
                        <label for="countInPage"><b>Rows on page</b></label>
                        <select id="countInPage" name="${ADD_MODEL_TELEPHONE_ATTR_ROWS_ON_PAGE}" class="form-control w-100">
                            <#if ROWS_ON_PAGE??>
                                <#list ROWS_ON_PAGE as row>
                                    <option value="${row.name}"
                                        <#if ACTUAL_ROWS_ON_PAGE?? && ACTUAL_ROWS_ON_PAGE == row.name>
                                            selected
                                        </#if>>${row.name}</option>
                                </#list>
                            </#if>
                        </select>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label for="showBy"><b>Show by</b></label>
                        <select id="showBy" name="${ADD_MODEL_TELEPHONE_ATTR_COUNTER_MODE}" class="form-control w-100">
                            <#if ENUM_SHOW_MODE_VALUES??>
                                <#list ENUM_SHOW_MODE_VALUES as mode>
                                    <option value="${mode.name()}"
                                        <#if ACTUAL_MODE?? && ACTUAL_MODE == mode>
                                            selected
                                        </#if>>${mode.name()}</option>
                                </#list>
                            </#if>
                        </select>
                    </div>
                </div>
            </div>
            <input type="hidden" name="${ADD_MODEL_TELEPHONE_ATTR_ACTUAL_PAGE}" id="actualPageFormField" value="${(PAGE_ACTUAL_ATTRIBUTE)!''}">
            <input type="hidden" name="${ADD_MODEL_TELEPHONE_ATTR_CHAT_VISIBLE}" id="visibleChat" value="${(CHAT_VISIBILITY)!''}">
        </form>
    </div>

    <!-- last phones -->
    <div class="container">
        <form>
            <div class="row mt-4">
                <div class="col-12">
                    <label><b>Last telephones</b></label>
                    <#if TELEPHONES_QUANTITY??>
                        <br><label>Total amount: ${TELEPHONES_QUANTITY}</label>
                    </#if>
                </div>
            </div>
            <div class="row mt-4">
                <div class="col-12">

                    <table class="table table-sm">
                        <thead>
                        <tr>
                            <th scope="col">Ordinal number</th>
                            <th scope="col">Add date</th>
                            <th scope="col">Note</th>
                            <th scope="col"></th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list telephones as t>
                            <tr>
                                <th>${t.ordinalNumber}</th>
                                <td>${t.phoneDate}</td>
                                <td>${t.note}</td>
                                <td><a href="/delete/${t.id?c}" class="btn btn-danger" role="button" aria-pressed="true">Delete</a></td>
                            </tr>
                        </#list>


                        </tbody>
                    </table>

                    <nav aria-label="Telephones navigation">
                        <ul class="pagination justify-content-center">

                            <li
                                <#if PAGE_PREVIOUS_ATTRIBUTE??>
                                    class="page-item"
                                <#else>
                                    class="page-item disabled"
                                </#if>>
                                <a class="page-link" <#if PAGE_PREVIOUS_ATTRIBUTE??> onclick="nextPage(${PAGE_PREVIOUS_ATTRIBUTE})" </#if> tabindex="-1">
                                    Previous
                                </a>
                            </li>
                            <li class="page-item active">
                                <div class="page-link">${(PAGE_ACTUAL_ATTRIBUTE)!''} / ${(PAGE_COUNT_ATTRIBUTE)!''}<span class="sr-only">(current)</span></div>
                            </li>
                            <li
                                <#if PAGE_NEXT_ATTRIBUTE??>
                                    class="page-item"
                                <#else>
                                    class="page-item disabled"
                                </#if>>
                                <a class="page-link" <#if PAGE_NEXT_ATTRIBUTE??> onclick="nextPage(${PAGE_NEXT_ATTRIBUTE})" </#if> tabindex="-1">
                                    Next
                                </a>
                            </li>
                        </ul>
                    </nav>

                </div>
                <!-- col -->
            </div>
            <!-- row -->
        </form>
    </div>
    <!-- container -->

    <hr class="my-4">

    <br>
    <br>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.3.0/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
    <script src="app-msg.js"></script>
</body>
</html>