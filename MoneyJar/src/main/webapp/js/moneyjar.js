/**
 * The Javascript file for Moneyjar web application
 */

$(function () {
	$("input[type=date]").datepicker({
		dateFormat:"yy-mm-dd",
		changeYear: true,
		changeMonth: true
	});
	
	/* Chart.js */
	$.ajax({
		url:"reports/category-totals",
		type: "GET",
		dataType: "json",
		success: function(result) {
			var ctx = $("#categories").get(0).getContext("2d");
			var categoriesChart = new Chart(ctx).Doughnut(result,{
				animationEasing: "easeOutQuart",
			    legendTemplate : "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<segments.length; i++){%><li><span style=\"background-color:<%=segments[i].fillColor%>\"></span><%if(segments[i].label){%><%=segments[i].label%><%}%></li><%}%></ul>"
			});
			var legend = categoriesChart.generateLegend();
			$("#categories-legend").html(legend);
		},
		failure: function(response) {
			$("#categories").html("There was an error: " + response);
		}
	});
	
});