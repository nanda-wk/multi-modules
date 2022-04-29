// $(document).ready(function (){
//     $(".buttonAdd2Cart").on("click", function (evt){
//        addToCart();
//     });
//
// });
//
// function addToCart(pId) {
//
//     quantity = $("#quantity" + productId).val();
//     url = contextPath + "cart/add" + productId + "/" + quantity;
//
//     $.ajax({
//         type: "GET",
//         url: url,
//         beforeSend: function (xhr) {
//             xhr.setRequestHeader(csrfHeaderName, csrfValue);
//         }
//     }).done(function (response) {
//         alert("shopping cart item added", response);
//     }).fail(function () {
//         alert("error while adding");
//     });
// }
//
// // for quantity control
// $(document).ready(function () {
//     $(".linkMinus").on("click", function (evt) {
//         evt.preventDefault();
//         productId = $(this).attr("pid");
//         quantityInput = $("#quantity" + productId);
//         newQuantity = parseInt(quantityInput.val()) - 1;
//
//     });
//
//     $(".linkPlus").on("click", function (evt) {
//         evt.preventDefault();
//         productId = $(this).attr("pid");
//         quantityInput = $("#quantity" + productId);
//         newQuantity = parseInt(quantityInput.val()) + 1;
//
//     });
// });