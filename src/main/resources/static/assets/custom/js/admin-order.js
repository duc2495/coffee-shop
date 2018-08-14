;
$(document).ready(function() {
	var status = $('#orderStatus').text();
	setOrderStatus(status);

	$('#btnUpdateOrderStatus').click(function(event) {

		// stop submit the form, we will post it manually.
		event.preventDefault();

		ajaxUpdateStatus();

	});

	$('#productTable').DataTable();

});

function ajaxUpdateStatus() {

	// Get form
	var form = $('#updateOrderStatusForm')[0];

	// Get orderId
	var id = $('#orderId').val();

	var data = new FormData(form);

	$("#btnUpdateOrderStatus").prop("disabled", true);

	$.ajax({
		type : "POST",
		enctype : 'multipart/form-data',
		url : "/admin/orders/" + id,
		data : data,
		processData : false,
		contentType : false,
		cache : false,
		timeout : 600000,
		success : function(data) {
			$("#modal-update-order-status").modal('hide');
			$("#showMessage").text(data.message)
			$(".message").show();
			setTimeout(function() {
				$(".message").hide(1000);
			}, 3000);
			$("#orderStatus").text(data.status);
			console.log("Status : ", data.status);
			setOrderStatus(data.status);
			console.log("SUCCESS : ", data);
			$("#btnUpdateOrderStatus").prop("disabled", false);

		},
		error : function(data) {
			console.log("ERROR : ", data.responseText);
			$("#btnUpdateOrderStatus").prop("disabled", false);

		}
	});
}

function setOrderStatus(status) {
	console.log("set class : " + status);
	switch (status) {
	case 'ORDERED':
		$('#orderStatus').removeClass().addClass("label label-warning");
		break;
	case 'SHIPPING':
		$('#orderStatus').removeClass().addClass("label label-info");
		break;
	case 'FINISHED':
		$('#orderStatus').removeClass().addClass("label label-success");
		break;
	case 'CANCELED':
		$('#orderStatus').removeClass().addClass("label label-danger");
		break;
	}
}