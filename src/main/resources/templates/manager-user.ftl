<!doctype html>
<html lang="en" xmlns="http://www.w3.org/1999/html">

    <head>
        <#include "manager/manager-head.ftl">
    </head>
    <body>

    <#include "manager/manager-navbar.ftl">



    <div class="mt-4 container">

        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item">Dashboard</li>
                <li class="breadcrumb-item active" aria-current="page">Users</li>
            </ol>
        </nav>

        <form>
            <div class="card w-100">
                <div class="card-body">
                    <button type="button" class="btn btn-primary">Send invite link</button>
                    <button type="button" class="btn btn-danger">Reset password</button>
                    <button type="button" class="btn btn-danger">Lock user</button>
                </div>
            </div>

            <div class="row my-2">
                <div class="col-12">
                    <table class="table table-borderless">
                        <thead>
                            <tr>
                                <th scope="col"><input type="checkbox"></th>
                                <th scope="col">Username</th>
                                <th scope="col">Name</th>
                                <th scope="col">Last name</th>
                                <th scope="col">Email</th>
                            </tr>
                        </thead>
                        <tbody>
                            <#list users as u>
                                <tr>
                                    <th scope="row"><input type="checkbox" name="users${(u.index)!''}.selected"></th>
                                    <td><input name="users${(u.index)!''}.username" value="${(u.username)!''}"></td>
                                    <td><input name="users${(u.index)!''}.firstName" value="${(u.firstName)!''}"></td>
                                    <td><input name="users${(u.index)!''}.lastName" value="${(u.lastName)!''}"></td>
                                    <td><input name="users${(u.index)!''}.email" value="${(u.email)!''}"></td>
                                </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </form>

    </div>

    <#include "manager/manager-js.ftl">
    </body>

</html>