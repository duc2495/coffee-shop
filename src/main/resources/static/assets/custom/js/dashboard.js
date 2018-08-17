;
$(document).ready(function() {
	initDashboardDaterange();
	ajaxGetResource();
	$('.btnGetValueByDate').click(function(event) {
		
		event.preventDefault();
		// ajax get value
		ajaxGetResource();
	});
    
});

function initDashboardDaterange() {
    jQuery().daterangepicker&&($("#dashboard-report-range").daterangepicker( {
        ranges: {
            Today: [moment(), moment()], Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")], "Last 7 Days": [moment().subtract(6, "days"), moment()], "Last 30 Days": [moment().subtract(29, "days"), moment()], "This Month": [moment().startOf("month"), moment().endOf("month")], "Last Month": [moment().subtract(1, "month").startOf("month"), moment().subtract(1, "month").endOf("month")]
        }
        , locale: {
            format: "MM/DD/YYYY", separator: " - ", applyLabel: "Apply", cancelLabel: "Cancel", fromLabel: "From", toLabel: "To", customRangeLabel: "Custom", daysOfWeek: ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"], monthNames: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"], firstDay: 1
        }
       
    }
    , function(e, t, a) {
        "0"!=$("#dashboard-report-range").attr("data-display-range")&&$("#dashboard-report-range span").html(e.format("MMMM D, YYYY")+" - "+t.format("MMMM D, YYYY"))
    }
    ), "0"!=$("#dashboard-report-range").attr("data-display-range")&&$("#dashboard-report-range span").html(moment().subtract(29, "days").format("MMMM D, YYYY")+" - "+moment().format("MMMM D, YYYY")), $("#dashboard-report-range").show())
}

function ajaxGetResource() {
	var from = $("input[name='daterangepicker_start']").val();
	var to = $("input[name='daterangepicker_end']").val();
	url = "/admin/resource" + "?from=" + from + "&to=" + to;
	
	$.ajax({
		type : "GET",
		url : url,
		processData : false,
		contentType : false,
		cache : false,
		timeout : 600000,
		success : function(data) {
			
			$('#newOrderNumber').text(data.newOrderNumber);
			$('#newProductNumber').text(data.newProductNumber);
			$('#income').text(data.income);
			$('#bestProductName').text(data.bestProduct.productName);
			$('#totalOrders').text(data.totalOrders);
			$('#totalOrders').attr('data-original-title', data.totalOrders + " Orders");
			$('#totalProducts').text(data.totalProducts);
			$('#totalProducts').attr('data-original-title', data.totalProducts + " Products");
			
			initLineChart(data.listIncome);
			initBarChart(data.listProduct);
			initOrderPieChart(data.orderedNumber, data.shippingNumber, data.finishedNumber, data.canceledNumber);
			initProductPieChart(data.pureCoffee, data.fromCoffee, data.noneCoffee);

			console.log("SUCCESS : ", data);
		}, 
		error : function(data) {
			console.log("ERROR : ", data.responseText);
		}
	});
}

function initLineChart(data) {
	$('#line-chart').empty();
    var line = new Morris.Line({
      element: 'line-chart',
      resize: true,
      data: data,
      xkey: 'date',
      ykeys: ['value'],
      labels: ['円'],
      lineColors: ['#3c8dbc'],
      hideHover: 'auto'
    });
}

function initBarChart(data) {
	$('#bar-chart').empty();
    var bar = new Morris.Bar({
      element: 'bar-chart',
      resize: true,
      data: data,
      barColors: ['#00a65a', '#D81B60', '#3c8dbc'],
      xkey: 'date',
      ykeys: ['pureCoffee', 'fromCoffee', 'noneCoffee'],
      labels: ['PURE COFFEE', 'FROM COFFEE', 'NONE COFFEE'],
      hideHover: 'auto'
    });
}

function initOrderPieChart(ordered, shipping, finnished, canceled) {
	$('#orderPieChart').empty();
	var orderPieChart = new Morris.Donut({
	      element: 'orderPieChart',
	      resize: true,
	      colors: ["#ff851b", "#00c0ef", "#00a65a", "#f56954"],
	      data: [
	        {label: "Ordered", value: ordered},
	        {label: "Shipping", value: shipping},
	        {label: "Finished", value: finnished},
	        {label: "Canceled", value: canceled}
	      ],
	      hideHover: 'auto'
	    });
}

function initProductPieChart(pureCoffee, fromCoffee, noneCoffee) {
	$('#productPieChart').empty();
	var productPieChart = new Morris.Donut({
	      element: 'productPieChart',
	      resize: true,
	      colors: ["#00a65a", "#D81B60", "#3c8dbc"],
	      data: [
	        {label: "PURE COFFEE", value: pureCoffee},
	        {label: "FROM COFFEE", value: fromCoffee},
	        {label: "NONE COFFEE", value: noneCoffee}
	      ],
	      hideHover: 'auto'
	    });
}
