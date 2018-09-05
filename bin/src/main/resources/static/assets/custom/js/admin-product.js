;
$(document).ready(function() {
	$("#productImage").click(function() {
		$("#modal-update-image").modal('show');
	});
});

$('.imageupload').imageupload();
$(document).ready(function() {

	$('#btnUpdateImage').click(function(event) {

		// stop submit the form, we will post it manually.
		event.preventDefault();

		ajaxUpdateImage();

	});

});

function ajaxUpdateImage() {

	// Get form
	var form = $('#updateImageForm')[0];

	// Get productId
	var id = $('#productId').val();

	var data = new FormData(form);

	$("#btnUpdateImage").prop("disabled", true);

	$.ajax({
		type : "POST",
		enctype : 'multipart/form-data',
		url : "/admin/products/" + id + "/updateImage",
		data : data,
		processData : false,
		contentType : false,
		cache : false,
		timeout : 600000,
		success : function(data) {
			$("#modal-update-image").modal('hide');
			$("#productImage").removeAttr("src").attr("src",
					"/admin/products/images?image=" + data.imageUrl);
			$("#showMessage").text(data.message)
			$(".message").show();
			setTimeout(function() {
				$(".message").hide(1000);
			}, 3000);

			console.log("SUCCESS : ", data);
			$("#btnUpdateImage").prop("disabled", false);

		},
		error : function(data) {
			$("#hasError").text(data.responseText);
			console.log("ERROR : ", data.responseText);
			$("#btnUpdateImage").prop("disabled", false);

		}
	});

}