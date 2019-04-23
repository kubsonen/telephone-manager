$( "#changeRowsOnPage" ).change(function() { this.submit(); });
$( "#changeShowMode" ).change(function() { this.submit(); });
function nextPage(p){
    $( "#actualPageFormField" ).val(p);
    $( "#changeRowsOnPage" ).submit();
}