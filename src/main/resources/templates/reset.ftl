<!doctype html>
<html lang="en" xmlns="http://www.w3.org/1999/html">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>Login page</title>

    <!-- Bootstrap CSS CDN -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
    <!-- Our Custom CSS -->
    <link rel="stylesheet" href="/login.css">

    <!-- Font Awesome JS -->
    <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js" integrity="sha384-tzzSw1/Vo+0N5UhStP3bvwWPq+uvzCMfrN1fEFe+xBmv1C/AtVX5K0uZtmcHitFZ" crossorigin="anonymous"></script>
    <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js" integrity="sha384-6OIrr52G08NpOFSZdxxz1xdNSndlD4vdcf/q2myIUVO0VsqaGHJsB0RaBE01VTOY" crossorigin="anonymous"></script>

</head>
<body>

<body>
<div class="container">
    <div class="row">
        <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
            <div class="card card-signin my-5">
                <div class="card-body">

                <#if resetPasswordSuccess??>
                    <div class="alert alert-success">
                        <strong>Success!</strong> ${resetPasswordSuccess}
                    </div>
                </#if>

                <#if resetPasswordError??>
                    <div class="alert alert-danger">
                        <strong>Danger!</strong> Fail on reset password process.
                    </div>
                </#if>

                <h5 class="card-title text-center">Reset password</h5>
                <form class="form-signin" method="post">

                    <div class="form-label-group">
                        <input type="email" id="resetEmail" name="resetEmail" class="form-control" placeholder="Email address" required autofocus>
                        <label for="resetEmail">Email address</label>
                    </div>

                    <div class="form-label-group">
                        <input type="password" id="resetPassword" name="resetPassword" class="form-control" placeholder="Password" required>
                        <label for="resetPassword">New password</label>
                    </div>

                    <button class="btn btn-lg btn-primary btn-block text-uppercase" type="submit">Reset</button>
                    <hr class="my-4">
                    <a href="/login" class="btn btn-lg btn-google btn-block text-uppercase"><i class="fab fa-google mr-2"></i>Back to login</a>
                </form>
            </div>
        </div>
    </div>
</div>
</div>
</body>

<!-- jQuery CDN -->
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<!-- Popper.JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
<!-- Bootstrap JS -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
</body>

</html>