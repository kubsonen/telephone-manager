var inviteWindowAction = {

    inviteModalId : 'inviteModalCenter',
    sendInviteRequestApi : '/man/users/invite',
    sendInviteAction : function () {
        var emailAddress = $('#inviteEmail').val();
        var inviteMail = { mail:   emailAddress };
        var ret;
        telephoneAjaxConnector.postData(inviteWindowAction.sendInviteRequestApi, JSON.stringify(inviteMail), function (ret) {
            console.log(ret);
            if(ret !== '-1'){
                $('#' + inviteWindowAction.inviteModalId).modal('hide');
            }
        })}
};

//On invite modal show action
$( document ).ready(function() {

    $(document).on('show.bs.modal', '.modal', function () {
        var zIndex = 1040 + (10 * $('.modal:visible').length);
        $(this).css('z-index', zIndex);
        setTimeout(function() {
            $('.modal-backdrop').not('.modal-stack').css('z-index', zIndex - 1).addClass('modal-stack');
        }, 0);
    });

    $('#' + inviteWindowAction.inviteModalId).on('show.bs.modal', function (event) {
        $('#inviteEmail').val('');
        focusAndCursor();
    })

    $('#inviteAction').on('click', function (event) {
        inviteWindowAction.sendInviteAction();
    });

    function focusAndCursor(){
        setTimeout(function() {
            // this focus on last character if input isn't empty
            $('#inviteEmail').focus();
        }, 500);
    }

});



