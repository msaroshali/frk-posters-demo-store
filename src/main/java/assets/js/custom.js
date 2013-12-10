$(document).ready(hideMessages);

function hideMessages()
{
	$("#errorMessage").hide();
	$("#successMessage").hide();
	$("#infoMessage").hide();
}

function deleteFromCart(basketProductId, cartIndex)
{
	hideMessages();
	var url = '/deleteFromCart?basketProductId=' + basketProductId;
	$.get(url, function(data)
		{
			// remove product from cart overview
			$('#product' + cartIndex).remove();
			// update cart in header
			$("#headerBasketOverview span").text(data.headerCartOverview);
			// update total price
			$("#totalPrice:first-child").text(data.totalPrice);
			// update cart slider
			getCartSliderText();
		});
}

function updateProductCount(basketProductId, count, cartIndex)
{
	var url = '/updateProductCount?basketProductId=' + basketProductId + '&productCount=' + count;
	$.get(url)
		.always(function() {
			hideMessages();
		})
		.done(function(data) {
			// update cart in header
			$("#headerBasketOverview span").text(data.headerCartOverview);
			// update total price
			$("#totalPrice:first-child").text(data.totalPrice);
			// update cart slider
			getCartSliderText();
		})
		.error(function(data) {
			var err = eval("(" + data.responseText + ")");
			$("#infoMessage").show();
			$("#infoMessage div strong").text(err.message);
		});
}