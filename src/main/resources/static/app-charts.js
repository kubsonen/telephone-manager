//Get chart canvas context
var ctx = document.getElementById('line-chart').getContext('2d');

$.ajax({
    url: "/man/telephones-stats",
    method: "POST",
    contentType: "application/json",
    success: function(chartData){

        //Create chart
        new Chart(ctx, {
            type: 'line',
            data: chartData,
            options: {}
        });

    }
});

