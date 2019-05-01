<!doctype html>
<html lang="en" xmlns="http://www.w3.org/1999/html">

    <head>
        <#include "manager/manager-head.ftl">
    </head>
    <body>
    <#include "manager/manager-navbar.ftl">
    <div class="mt-4 container">

        <#if USERS_LOCK_NICKNAMES??>
            <div class="alert alert-success" role="alert">
                <b>${USERS_LOCK_NICKNAMES}</b> successfully locked!
            </div>
        </#if>

        <#if USERS_LOCK_FAIL??>
            <div class="alert alert-danger" role="alert">
                Lock users fail.
            </div>
        </#if>

        <#if USERS_UNLOCK_NICKNAMES??>
            <div class="alert alert-success" role="alert">
                <b>${USERS_UNLOCK_NICKNAMES}</b> successfully unlocked!
            </div>
        </#if>

        <#if USERS_UNLOCK_FAIL??>
            <div class="alert alert-danger" role="alert">
                Unlock users fail.
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
<#--                    <button type="button" class="btn btn-primary">Send invite link</button>-->
<#--                    <button type="button" class="btn btn-danger" name="reset" value="reset">Reset password</button>-->
<#--                    <button type="submit" class="btn btn-success" name="unlock" value="unlock">Unlock</button>-->
                    <button type="submit" class="btn btn-danger" >Lock</button>
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
                                    <th scope="row"><input type="checkbox" name="userModels${(u.index)!''}.selected"></th>
                                    <td>${(u.username)!''}</td>
                                    <td>${(u.firstName)!''}</td>
                                    <td>${(u.lastName)!''}</td>
                                    <td>${(u.email)!''}</td>
                                    <input type="hidden" name="userModels${(u.index)!''}.id" value="${(u.id)!''}">
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