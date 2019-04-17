<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>TStats</title>
</head>

<body>

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

    <!-- last phones -->
    <div class="container">
        <form>
            <div class="row mt-4">
                <div class="col-12">
                    <label><b>Last telephones</b></label>
                </div>
            </div>
            <div class="row mt-4">
                <div class="col-12">

                    <table class="table table-sm">
                        <thead>
                        <tr>
                            <th scope="col">Id</th>
                            <th scope="col">Add date</th>
                            <th scope="col">Note</th>
                            <th scope="col"></th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list telephones as t>
                            <tr>
                                <th>${t.id}</th>
                                <td>${t.phoneDate}</td>
                                <td>${t.note}</td>
                                <td><a href="/telephone/delete/${t.id}" class="btn btn-danger" role="button" aria-pressed="true">Usuń</a></td>
                            </tr>
                        </#list>


                        </tbody>
                    </table>

                </div>
                <!-- col -->
            </div>
            <!-- row -->
        </form>
    </div>
    <!-- container -->

    <hr class="my-4">

    <!-- stats -->
    <div class="container">
        <form>
            <div class="row mt-4">
                <div class="col-12">
                    <label><b>Stats</b></label>
                </div>
            </div>
            <div class="row mt-4">
                <div class="col-12">
                    <div class="form-group">
                        <table class="table table-sm">
                            <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Nazwa</th>
                                <th scope="col">Wartość</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <th scope="row">1</th>
                                <td>Ilość ogółem</td>
                                <td>352</td>
                            </tr>
                            <tr>
                                <th scope="row">2</th>
                                <td>Dziś</td>
                                <td>8</td>
                            </tr>
                            <tr>
                                <th scope="row">3</th>
                                <td>W ostatnim miesiącu</td>
                                <td>56</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!-- col -->
            </div>
            <!-- row -->
        </form>
    </div>
    <!-- container -->

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>