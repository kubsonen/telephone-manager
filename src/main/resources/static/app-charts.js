//Get chart canvas context
var ctx = document.getElementById('line-chart').getContext('2d');

$.ajax({
    url: "/manager/get-telephones-by-day",
    method: "POST",
    contentType: "application/json",
    success: function(chartData){

        //Create chart
        var myLineChart = new Chart(ctx, {
            type: 'line',
            data: chartData,
            options: {}
        });

    }
});

