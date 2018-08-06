;
$(document).ready(function() {
	setTimeout(function() {
		$(".alert-hide").fadeOut(1000);
	}, 3000);

	$('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
		// var target = $(e.target).attr("href"); // activated tab
		// alert (target);
		$($.fn.dataTable.tables(true)).css('width', '100%');
		$($.fn.dataTable.tables(true)).DataTable().columns.adjust().draw();
	});
});

function submit() {
	var reqRow = [];
	var products = JSON.parse(localStorage.products);
	for (var i = 0; i < products.length; i++) {
		var productId = products[i].id;
		var quantity = products[i].quantity;
		var entry = {
			"productId" : productId,
			"quantity" : quantity
		}
		reqRow.push(entry);
	}

	$.ajax({
		type : "Post",
		contentType : "application/json",
		url : "/order",
		data : JSON.stringify({
			productList : reqRow
		}),
		success : function(result) {
			// clear customer body
			window.location.href = "/order/submit_order";
		},
		error : function(e) {
			alert("Error!"), console.log("ERROR: ", e);
		}
	});
	$('html, body').animate({
		scrollTop : $('#content').offset().top
	}, 1000);
}