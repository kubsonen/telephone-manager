function addCalendarEvent(date) {
    $('#addEventCalendarModalForm')[0].reset();
    $('#addCalendarEventModalCenterTitle').text('Add new event for date: ' + date);
    $('#addCalendarEventModalCenter').modal('show');

    //Delete event handlers
    $('#addEventCalendarDateSave').off();

    //Save calendar event
    $('#addEventCalendarDateSave').on('click', function(event) {

        var sendInviteRequestApi = '/graphic/addEvent';

        console.log(date + ' ' + $('#addEventDateHourFrom').val());
        console.log(date + ' ' + $('#addEventDateHourTo').val());

        //Construct an object
        var calendarEventData = {
            type: $('#addEventKind').val(),
            startDate: date + ' ' + $('#addEventDateHourFrom').val(),
            endDate: date + ' ' + $('#addEventDateHourTo').val(),
            note: $('#addEventNotes').val()
        };

        telephoneAjaxConnector.postData(sendInviteRequestApi, JSON.stringify(calendarEventData), function (ret) {
            if(ret !== '-1'){
                $('#addCalendarEventModalCenter').modal('hide');
                window.location.reload();
            }
        });

    });
}

function infoCalendarEvent(eventId, date) {

    $('#showEventCalendarModalForm')[0].reset();
    $('#showCalendarEventModalCenterTitle').text('View event: ' + eventId);
    $('#showCalendarEventModalCenter').modal('show');

    //Delete event handlers
    $('#saveShowEventModal').off();
    $('#deleteShowEventModal').off();

    //Get edit event
    var getEvent = '/graphic/getEvent/' + eventId;
    telephoneAjaxConnector.postData(getEvent, JSON.stringify(''), function (ret) {
                setFieldValue('showEventKind', ret.type);
                setFieldValue('showEventDateHourFrom', ret.startDate.split(' ')[1]);
                setFieldValue('showEventDateHourTo', ret.endDate.split(' ')[1]);
                setFieldValue('showEventNotes', ret.note); }, "GET");

    //Delete calendar event
    $('#deleteShowEventModal').on('click', function(event) {

        var sendInviteRequestApi = '/graphic/deleteEvent/' + eventId;
        telephoneAjaxConnector.postData(sendInviteRequestApi, JSON.stringify(''), function (ret) {
            if(ret !== '-1'){
                $('#showCalendarEventModalCenter').modal('hide');
                window.location.reload();
            }
        });

    });

    //Save calendar event
    $('#saveShowEventModal').on('click', function(event) {

        var sendInviteRequestApi = '/graphic/editEvent';

        //Construct an object
        var calendarEventData = {
            id: eventId,
            type: $('#showEventKind').val(),
            startDate: date + ' ' + $('#showEventDateHourFrom').val(),
            endDate: date + ' ' + $('#showEventDateHourTo').val(),
            note: $('#showEventNotes').val()
        };

        telephoneAjaxConnector.postData(sendInviteRequestApi, JSON.stringify(calendarEventData), function (ret) {
            if(ret !== '-1'){
                $('#showCalendarEventModalCenter').modal('hide');
                window.location.reload();
            }
        });

    });

}

//On invite modal show action
$( document ).ready(function() {

    $(document).on('show.bs.modal', '.modal', function () {
        var zIndex = 1040 + (10 * $('.modal:visible').length);
        $(this).css('z-index', zIndex);
        setTimeout(function() {
            $('.modal-backdrop').not('.modal-stack').css('z-index', zIndex - 1).addClass('modal-stack');
        }, 0);
    });

    var inputFrom = $('#addEventDateHourFrom');
    inputFrom.clockpicker({
        autoclose: true
    });
    var inputTo = $('#addEventDateHourTo');
    inputTo.clockpicker({
        autoclose: true
    });

});

function setFieldValue(field, value) {
    if(value) {
        $('#' + field).val(value);
    }

}
