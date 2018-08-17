;
var pieOptions     = {
		  // Boolean - Whether we should show a stroke on each segment
		  segmentShowStroke    : true,
		  // String - The colour of each segment stroke
		  segmentStrokeColor   : '#fff',
		  // Number - The width of each segment stroke
		  segmentStrokeWidth   : 1,
		  // Number - The percentage of the chart that we cut out of the
			// middle
		  percentageInnerCutout: 50, // This is 0 for Pie charts
		  // Number - Amount of animation steps
		  animationSteps       : 100,
		  // String - Animation easing effect
		  animationEasing      : 'easeOutBounce',
		  // Boolean - Whether we animate the rotation of the Doughnut
		  animateRotate        : true,
		  // Boolean - Whether we animate scaling the Doughnut from the centre
		  animateScale         : false,
		  // Boolean - whether to make the chart responsive to window resizing
		  responsive           : true,
		  // Boolean - whether to maintain the starting aspect ratio or not
			// when
			// responsive, if set to false, will take up entire container
		  maintainAspectRatio  : false,
		  // String - A legend template
		  legendTemplate       : '<ul class=\'<%=name.toLowerCase()%>-legend\'><% for (var i=0; i<segments.length; i++){%><li><span style=\'background-color:<%=segments[i].fillColor%>\'></span><%if(segments[i].label){%><%=segments[i].label%><%}%></li><%}%></ul>',
		  // String - A tooltip template
		  tooltipTemplate      : '<%=value %> <%=label%>'
};

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
    var bar = new Morris.Bar({
      element: 'bar-chart',
      resize: true,
      data: data,
      barColors: ['#00a65a', '#f56954', '#111111'],
      xkey: 'date',
      ykeys: ['pureCoffee', 'fromCoffee', 'noneCoffee'],
      labels: ['PURE COFFEE', 'FROM COFFEE', 'NONE COFFEE'],
      hideHover: 'auto'
    });
}

function initOrderPieChart(ordered, shipping, finnished, canceled) {
	var orderPieChartCanvas = $('#orderPieChart').get(0).getContext('2d');
	var orderPieChart       = new Chart(orderPieChartCanvas);
	var orderPieData   = [
	  {
	    value    : ordered,
	    color    : '#f39c12',
	    highlight: '#f39c12',
	    label    : 'ORDERED'
	  },
	  {
	    value    : shipping,
	    color    : '#00c0ef',
	    highlight: '#00c0ef',
	    label    : 'SHIPPING'
	  },
	  {
	    value    : finnished,
	    color    : '#00a65a',
	    highlight: '#00a65a',

	    label    : 'FINISHED'
	  },
	  {
	    value    : canceled,
	    color    : '#f56954',
	    highlight: '#f56954',
	    label    : 'CANCELED'
	  }
	];
	orderPieChart.Doughnut(orderPieData, pieOptions);
}

function initProductPieChart(pureCoffee, fromCoffee, noneCoffee) {
	var productPieChartCanvas = $('#productPieChart').get(0).getContext('2d');
	var productPieChart       = new Chart(productPieChartCanvas);
	var productPieData        = [
	  {
	    value    : pureCoffee,
	    color    : '#00a65a',
	    highlight: '#00a65a',
	    label    : 'PURE COFFEE'
	  },
	  {
	    value    : fromCoffee,
	    color    : '#f56954',
	    highlight: '#f56954',
	    label    : 'FROM COFFEE'
	  },
	  {
	    value    : noneCoffee,
	    color    : '#111111',
	    highlight: '#111111',
	    label    : 'NONE COFFEE'
	  }
	];
	productPieChart.Doughnut(productPieData, pieOptions);
}
