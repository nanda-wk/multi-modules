// ************************************************
// Shopping Cart API
// ************************************************

var shoppingCart = (function () {
    // =============================
    // Private methods and propeties
    // =============================
    cart = [];

    // Constructor
    function Item(id, name, imgsrc, price, count) {
        this.id = id;
        this.name = name;
        this.imgsrc = imgsrc;
        this.price = price;
        this.count = count;
    }

    // Save cart
    function saveCart() {
        sessionStorage.setItem('shoppingCart', JSON.stringify(cart));
    }

    // Load cart
    function loadCart() {
        cart = JSON.parse(sessionStorage.getItem('shoppingCart'));
    }

    if (sessionStorage.getItem("shoppingCart") != null) {
        loadCart();
    }


    // =============================
    // Public methods and propeties
    // =============================
    var obj = {};

    // Add to cart
    obj.addItemToCart = function (id, name, imgsrc, price, count) {
        console.log("id " + id + "\n src " + imgsrc + "\n name " + name + "\n price " + price);
        for (var item in cart) {
            if (cart[item].id === id) {
                console.log("before count \n");
                cart[item].count++;
                console.log("after count \n");
                saveCart();
                return;
            }
        }
        console.log("no count");
        var item = new Item(id, name, imgsrc, price, count);
        cart.push(item);
        saveCart();
    }
    // Set count from item
    obj.setCountForItem = function (id, count) {
        for (var i in cart) {
            if (cart[i].id === id) {
                cart[i].count = count;
                break;
            }
        }
    };
    // Remove item from cart
    obj.removeItemFromCart = function (id) {
        for (var item in cart) {
            if (cart[item].id === id) {
                cart[item].count--;
                if (cart[item].count === 0) {
                    cart.splice(item, 1);
                }
                break;
            }
        }
        saveCart();
    }

    // Remove all items from cart
    obj.removeItemFromCartAll = function (id) {
        for (var item in cart) {
            if (cart[item].id === id) {
                cart.splice(item, 1);
                break;
            }
        }
        saveCart();
    }

    // Clear cart
    obj.clearCart = function () {
        cart = [];
        saveCart();
    }

    // Count cart
    obj.totalCount = function () {
        var totalCount = 0;
        for (var item in cart) {
            totalCount += cart[item].count;
        }
        return totalCount;
    }

    // Total cart
    obj.totalCart = function () {
        var totalCart = 0;
        for (var item in cart) {
            totalCart += cart[item].price * cart[item].count;
        }
        return Number(totalCart.toFixed(1));
        // return "<input type='number' value=${Number(totalCart.toFixed(1))}/>";
    }

    // List cart
    obj.listCart = function () {
        var cartCopy = [];
        for (i in cart) {
            item = cart[i];
            itemCopy = {};
            for (p in item) {
                itemCopy[p] = item[p];

            }
            itemCopy.total = Number(item.price * item.count).toFixed(1);
            cartCopy.push(itemCopy)
        }
        return cartCopy;
    }

    // cart : Array
    // Item : Object/Class
    // addItemToCart : Function
    // removeItemFromCart : Function
    // removeItemFromCartAll : Function
    // clearCart : Function
    // countCart : Function
    // totalCart : Function
    // listCart : Function
    // saveCart : Function
    // loadCart : Function
    return obj;
})();


// *****************************************
// Triggers / Events
// *****************************************
// Add item
$('.add-to-cart').click(function (event) {
    event.preventDefault();
    var id = $(this).data('id');
    var imgsrc = $(this).data('imgsrc');
    var name = $(this).data('name');
    var price = Number($(this).data('price'));
    shoppingCart.addItemToCart(id, name, imgsrc, price, 1);
    displayCart();
});

// Clear items
$('.clear-cart').click(function () {
    shoppingCart.clearCart();
    displayCart();
});


function displayCart() {
    var cartArray = shoppingCart.listCart();
    var output = "";
    for (var i in cartArray) {
        output += "<tr class='d-table-row'>"
            + "<td ><img class='card-img-heigh' src=" + cartArray[i].imgsrc + "></td>"
            + "<td class='col'>" + cartArray[i].name + "</td>"
            + "<td class='text-right'>" + cartArray[i].price + "</td>"
            + "<td class='text-nowrap'><button class='minus-item btn btn-primary' data-id=" + cartArray[i].id + "><i class='bi bi-dash'></i></button>"
            + "<input readonly type='number' class='item-count cart-quantity-input' data-id='" + cartArray[i].id + "' value='" + cartArray[i].count + "'>"
            + "<button class='plus-item btn btn-primary' data-id=" + cartArray[i].id + "><i class='bi bi-plus'></i></button></td>"
            + " = "
            + "<td class='text-right'>" + cartArray[i].total + "</td>"
            + "<td><button class='delete-item btn btn-danger' data-id=" + cartArray[i].id + "><i class='bi bi-trash3'></i></button></td>"
            + "</tr>";
    }
    $('.show-cart').html(output);
    $('.total-cart').html(shoppingCart.totalCart());
    $('.total-count').html(shoppingCart.totalCount());
}

function checkoutCart() {
    var cartArray = shoppingCart.listCart();
    var showItem = "";
    for (var i in cartArray) {
        showItem += "<tr class='d-table-row'>"
            + "<input type='hidden' name='productIds' value=" + cartArray[i].id + " />"
            + "<td class='d-table-cell position-relative'><img class='card-img-heigh' src=" + cartArray[i].imgsrc + "></td>"
            + "<td class='col d-table-cell'>" + cartArray[i].name + "</td>"
            + "<td class='text-right d-table-cell'>" + cartArray[i].price + "</td>"
            + "<td class='text-right d-table-cell'><input readonly class='form-control-plaintext cart-quantity-input' type='number' name='quantities' value='" + cartArray[i].count + "'/></td>"
            + "<td class='text-right d-table-cell'>" + cartArray[i].total + "</td>"
            + "</tr>";
    }
    tablefooter = "<tr class='d-table-row'>"
        + "<td class='d-table-cell' colspan='3'></td>"
        + "<td class='cart-total d-table-cell'>Total"
        + "</td>"
        + "<td class='text-right d-table-cell'><input name='total' type='text' class='total-cart form-control-plaintext' readonly value=''></td>"
        + "</tr>";
    result = showItem + tablefooter;
    $('.checkout-cart').html(result);
    $('.total-cart').val(shoppingCart.totalCart());
}

// Delete item button

$('.show-cart').on("click", ".delete-item", function (event) {
    var id = $(this).data('id')
    shoppingCart.removeItemFromCartAll(id);
    displayCart();
})


// -1
$('.show-cart').on("click", ".minus-item", function (event) {
    var id = $(this).data('id')
    shoppingCart.removeItemFromCart(id);
    displayCart();
})
// +1
$('.show-cart').on("click", ".plus-item", function (event) {
    var id = $(this).data('id')
    shoppingCart.addItemToCart(id);
    displayCart();
})

// Item count input
$('.show-cart').on("change", ".item-count", function (event) {
    var id = $(this).data('id');
    var count = Number($(this).val());
    shoppingCart.setCountForItem(id, count);
    displayCart();
});

displayCart();
checkoutCart();