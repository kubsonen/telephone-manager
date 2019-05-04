<!doctype html>
<html lang="en" xmlns="http://www.w3.org/1999/html">

    <head>
        <#include "manager/manager-head.ftl">
    </head>
    <body>
    <#include "manager/manager-navbar.ftl">
    <div class="mt-4 container">

        <#if lockSuccess??>
            <div class="alert alert-success" role="alert">
                Successfully locked!
            </div>
        </#if>

        <#if lockFail??>
            <div class="alert alert-danger" role="alert">
                Lock user fail.
            </div>
        </#if>

        <#if unlockSuccess??>
            <div class="alert alert-success" role="alert">
                Successfully unlocked!
            </div>
        </#if>

        <#if unlockFail??>
            <div class="alert alert-danger" role="alert">
                Unlock user fail.
            </div>
        </#if>

        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item">Dashboard</li>
                <li class="breadcrumb-item active" aria-current="page">Users</li>
            </ol>
        </nav>

        <form method="post" action="/manager/users" name="users">
            <div class="card w-100">
                <div class="card-body">
                    <button type="button" class="btn btn-primary">Send invite link</button>
                </div>
            </div>

            <div class="row my-2">
                <div class="col-12">
                    <table class="table table-borderless">
                        <thead>
                            <tr>
                                <th scope="col">Username</th>
                                <th scope="col">Name</th>
                                <th scope="col">Last name</th>
                                <th scope="col">Email</th>
                                <th scope="col"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <#list users as u>

                                <#if u.accountNotLock == true>
                                    <tr>
                                <#else>
                                    <tr class="table-danger">
                                </#if>

                                    <td>${(u.username)!''}</td>
                                    <td>${(u.firstName)!''}</td>
                                    <td>${(u.lastName)!''}</td>
                                    <td>${(u.email)!''}</td>
                                    <td>
                                        <a role="button" href="/manager/users/lock/${(u.id)!''}" class="btn btn-danger">Lock</a>
                                        <a role="button" href="/manager/users/unlock/${(u.id)!''}" class="btn btn-primary">Unlock</a>
                                        <a role="button" href="/manager/users/reset/${(u.id)!''}" class="btn btn-danger">Reset password</a>
                                    </td>
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