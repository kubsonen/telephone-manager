var telephoneAjaxConnector = {

    successModalId : 'successModalCenter',
    successModalMsgContent : 'modalSuccessContent',
    errorModalId : 'errorModalCenter',
    errorModalMsgContent : 'modalErrorContent',

    postData : function (api, data, callback, method) {
        var output;

        if(typeof method === "undefined") {
            method = "POST";
        }


        $.ajax({
            url: api,
            method: method,
            contentType: "application/json",
            data: data,
            success: function(data){

                if(data.anServerResponse === undefined){
                    telephoneAjaxConnector.showError('Response is not an declared object in the api.');
                    output = '-1';
                } else {

                    if(data.error == true){
                        telephoneAjaxConnector.showError(data.msg);
                        output = '-1';
                    } else {
                        if(data.returnedObject !== undefined && data.returnedObject !== null){
                            output = data.returnedObject;
                        } else {
                            telephoneAjaxConnector.showSuccess(data.msg);
                            output = '0';
                        }
                    }

                }
            },
            error: function (error) {
                telephoneAjaxConnector.showError('Undefined.');
                output = '-1';
            },
            complete: function () {
                callback(output);
            }
        });
        return output;
    },

    showError : function (msg) {
        $('#' + this.errorModalMsgContent).html(msg);
        $('#' + this.errorModalId).modal('show');
    },

    showSuccess : function (msg) {
        $('#' + this.successModalMsgContent).html(msg);
        $('#' + this.successModalId).modal('show');
    }

}