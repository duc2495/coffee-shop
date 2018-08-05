;
$(document).ready(function() {
	setTimeout(function() {
		$(".alert-hide").fadeOut(1000);
	}, 3000);
});

//function for cart's submit button
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

	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	 
	$(document).ajaxSend(function(e, xhr, options) {
	    xhr.setRequestHeader(header, token);
	});

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
