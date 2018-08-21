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

	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});

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
	switch (status) {
	case 'ORDERED':
		$('#orderStatus').removeClass().addClass("label label-warning");
		disableOrderStatus("ordered");
		break;
	case 'SHIPPING':
		$('#orderStatus').removeClass().addClass("label label-info");
		disableOrderStatus("shipping");
		break;
	case 'FINISHED':
		$('#orderStatus').removeClass().addClass("label label-success");
		disableOrderStatus("finished");
		break;
	case 'CANCELED':
		$('#orderStatus').removeClass().addClass("label label-danger");
		disableOrderStatus("canceled");
		break;
	}
}

function disableOrderStatus(status) {
	$('#ordered').prop('disabled', false);
	$('#shipping').prop('disabled', false);
	$('#finished').prop('disabled', false);
	$('#canceled').prop('disabled', false);

	$('#' + status).prop('disabled', !$('#' + status).prop('disabled'));
	$('.select2').select2();
}