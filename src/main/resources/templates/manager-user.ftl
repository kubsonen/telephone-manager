<!doctype html>
<html lang="en" xmlns="http://www.w3.org/1999/html">

    <head>
        <#include "manager/manager-head.ftl">
    </head>
    <body>
    <#include "manager/manager-navbar.ftl">
    <div class="mt-4 container">

        <#if successInfo??>
            <div class="alert alert-success" role="alert">
                ${successInfo}
            </div>
        </#if>

        <#if failInfo??>
            <div class="alert alert-danger" role="alert">
                ${failInfo}
            </div>
        </#if>

        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item">Dashboard</li>
                <li class="breadcrumb-item active" aria-current="page">Users</li>
            </ol>
        </nav>

        <form method="post" action="/man/users" name="users">
            <div class="card w-100">
                <div class="card-body">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#inviteModalCenter">Send invite link</button>
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
                                        <div class="btn-group">
                                            <#if u.manager == false && u.admin == false>
                                                <#if u.accountNotLock == true>
                                                    <a role="button" href="/man/users/lock/${(u.id?c)!''}" class="btn btn-danger">Lock</a>
                                                <#else>
                                                    <a role="button" href="/man/users/unlock/${(u.id?c)!''}" class="btn btn-primary">Unlock</a>
                                                </#if>
                                            </#if>
                                        </div>
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
    <#include "modal-error.ftl">
    <#include "modal-success.ftl">
    <#include "manager/manager-invite.ftl">
    </body>

</html>