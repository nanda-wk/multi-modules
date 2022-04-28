$(function() {
    $("#filter-open").on('click', function() {
        $("#filter").removeClass('filter-close').toggleClass('filter-open');
    });
  });
  $(function() {
    $("#filter-close").on('click', function() {
        $("#filter").removeClass('filter-open').toggleClass("filter-close");
    });
  });