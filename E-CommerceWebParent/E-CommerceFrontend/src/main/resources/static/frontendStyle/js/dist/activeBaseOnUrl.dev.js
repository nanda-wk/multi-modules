"use strict";

var currentLocation = location.href;
var menuItem = document.querySelectorAll('a');
var menuLength = menuItem.length;

for (var i = 0; i < menuLength; i++) {
  if (menuItem[i].href === currentLocation) {
    menuItem[i].className = "nav-link active";
  }
}