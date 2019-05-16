<#-- @ftlvariable name="registerError" type="String" -->
<!doctype html>
<#import "/spring.ftl" as spring>
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
    <title>TStats</title>
</head>

<body>
<hr class="my-4">

<div class="container">

    <#if !(registerErrorTokenNotFound??)>
    <div class="alert alert-danger" role="alert" id="modalErrorContent">Register token not found. <a href="/"><b>Go to index page.</b></b></a></div>
    <#else>

    <div class="card">
        <div class="card-header">
            <h3>Complete your registration</h3>
        </div>
        <div class="card-body">
            <#if registerError??>
                <div class="alert alert-danger" role="alert" id="modalErrorContent">${registerError}</div></#if>
            <form method="post">
                <div class="form-group">
                    <@spring.bind path="registerForm.registerToken"/>
                    <label for="registerToken">Token</label>
                    <input type="text"
                            <#if spring.status.errorMessages?? && spring.status.errorMessages?size != 0> class="form-control is-invalid"
                            <#else> class="form-control" </#if>
                           id="registerToken" name="${spring.status.expression}"
                           value="${spring.status.value?default("")}" readonly/>
                    <#list spring.status.errorMessages as error>
                        <div class="invalid-feedback">${error}</div> </#list>
                </div>
                <div class="form-group">
                    <@spring.bind path="registerForm.email"/>
                    <label for="registerEmailAddress">Email address</label>
                    <input type="email"
                            <#if spring.status.errorMessages?? && spring.status.errorMessages?size != 0> class="form-control is-invalid"
                            <#else> class="form-control" </#if>
                           id="registerEmailAddress" name="${spring.status.expression}"
                           value="${spring.status.value?default("")}" readonly/>
                    <#list spring.status.errorMessages as error>
                        <div class="invalid-feedback">${error}</div> </#list>
                </div>
                <div class="form-group">
                    <@spring.bind path="registerForm.login"/>
                    <label for="registerLogin">Login</label>
                    <input type="text"
                            <#if spring.status.errorMessages?? && spring.status.errorMessages?size != 0> class="form-control is-invalid"
                            <#else> class="form-control" </#if>
                           id="registerLogin" name="${spring.status.expression}"
                           value="${spring.status.value?default("")}" placeholder="Login"/>
                    <#list spring.status.errorMessages as error>
                        <div class="invalid-feedback">${error}</div> </#list>
                </div>
                <div class="form-group">
                    <@spring.bind path="registerForm.password"/>
                    <label for="registerPassword">Password</label>
                    <input type="password"
                            <#if spring.status.errorMessages?? && spring.status.errorMessages?size != 0> class="form-control is-invalid"
                            <#else> class="form-control" </#if>
                           id="registerPassword" name="${spring.status.expression}"
                           value="${spring.status.value?default("")}" placeholder="Password"/>
                    <#list spring.status.errorMessages as error>
                        <div class="invalid-feedback">${error}</div> </#list>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>

        </#if>

    </div>
</div>

<hr class="my-4">

<br>
<br>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>