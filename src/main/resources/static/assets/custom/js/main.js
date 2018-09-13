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

	// Initialize Select2 Elements
	$('.select2').select2()

	$('[data-toggle="tooltip"]').tooltip()

	$('#btnUpdateOrder').click(function(event) {

		// stop submit the form, we will post it manually.
		event.preventDefault();

		ajaxUpdateOrder();

	});
});

// function for cart's submit button
function submit() {
	var reqRow = [];
	var products = JSON.parse(localStorage.products);
	for (var i = 0; i < products.length; i++) {
		var productId = products[i].id;
		var quantity = products[i].quantity;
		var productName = products[i].name;
		var price = products[i].price;
		var product = {
			"productId" : productId,
			"productName": productName,
			"price": price
		}
		var entry = {
			"product" : product,
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
			alert(e.responseText), console.log("ERROR: ", e);
			localStorage.products = JSON.stringify([]);
			window.location.href = "/";
		}
	});
	$('html, body').animate({
		scrollTop : $('#content').offset().top
	}, 1000);
}

function reloadEvent() {
	var goToCartIcon = function($addTocartBtn) {
		var $cartIcon = $(".my-cart-icon");
		var $image = $(
				'<img width="30px" height="30px" src="'
						+ $addTocartBtn.data("image") + '"/>').css({
			"position" : "fixed",
			"z-index" : "999"
		});
		$addTocartBtn.prepend($image);
		var position = $cartIcon.position();
		$image.animate({
			top : position.top,
			left : position.left
		}, 500, "linear", function() {
			$image.remove();
		});
	}

	$('.my-cart-btn').myCart({
		classCartIcon : 'my-cart-icon',
		classCartBadge : 'my-cart-badge',
		affixCartIcon : true,
		checkoutCart : function(products) {
			$.each(products, function() {
				console.log(this);
			});
		},
		clickOnAddToCart : function($addTocart) {
			goToCartIcon($addTocart);
		},
		getDiscountPrice : function(products) {
			var total = 0;
			$.each(products, function() {
				total += this.quantity * this.price;
			});
			return total * 1;
		}
	});
}

function searchByKeyWord() {
	var keyword = $('#searchInputField').val();
	var destination = "/search?keyWord=" + keyword;
	$.ajax({
		type : "Get",
		url : destination,
		success : function(result) {
			// clear customer body
			$('#content').html(result);
			reloadEvent();
		},
		error : function(e) {
			alert("Error!"), console.log("ERROR: ", e);
		}
	});
}

function paginate(paginate) {
	var productType = paginate.getAttribute("data-productType");
	var page = paginate.getAttribute("data-page");
	var destination = "/productpage/" + productType + "/" + page;
	var element = '#' + productType + '_section';
	$.ajax({
		type : "Get",
		url : destination,
		success : function(result) {
			// clear customer body
			$(element).html(result);
			reloadEvent();
		},
		error : function(e) {
			alert("Error!"), console.log("ERROR: ", e);
		}
	});
}

function ajaxUpdateOrder() {

	// Validate
	var note = $('#note').val();
	console.log(note + "|" + note.length);
	if (note.length > 500) {
		$(".errorMessage").text("メモは500文字未満でなければなりません。");
	} else {
		// Get form
		var form = $('#updateOrderForm')[0];
		var data = new FormData(form);
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");

		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
		});

		$("#btnUpdateOrder").prop("disabled", true);

		$.ajax({
			type : "POST",
			enctype : 'multipart/form-data',
			url : "/order/update",
			data : data,
			processData : false,
			contentType : false,
			cache : false,
			timeout : 600000,
			success : function(data) {
				$("#modal-update-order").modal('hide');
				$("#showMessage").text(data.message)
				$(".message").show();
				setTimeout(function() {
					$(".message").hide(1000);
				}, 3000);
				$("#orderNote").text(data.note);
				console.log("SUCCESS : ", data);
				$("#btnUpdateOrder").prop("disabled", false);
				$(".errorMessage").text("");
			},
			error : function(data) {
				$('.errorMessage').text(data.responseText);
				$("#btnUpdateOrder").prop("disabled", false);
			}
		});
	}
}