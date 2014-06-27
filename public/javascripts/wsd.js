/* 
 * WATER SHIP DOWN - Webtechnolody - HTWG Konstanz
 * (c)2014 - Artur Bauer, Johannes Dangelmaier, Dennis Weber
 */

$('document').ready(function() {

  /* better GUI
   -------------------------------------------------- */

  //bootstrap tooltip & popover
  $('.special_actions button').tooltip();
  $('.info_button').popover();


  //smooth scrolling to anchor
  var $root = $('html, body');
  $('.sliding-link').click(function() {
    $root.animate({
      scrollTop: $($.attr(this, 'href')).offset().top
    }, 500);
    return false;
  });

  //toggle control
  var controlHidden = false;
  $(".toggleControl").click(function() {
    $(".controlToggleContainer").toggle();
    if (controlHidden) {
      $(".toggleControl").text("click to hide control bar");
      controlHidden = false;
    } else {
      $(".toggleControl").text("click to show control bar");
      controlHidden = true;
    }
  });

  //exit button
  $('#exit').click(function() {
    window.location.href = '/';
  });

  //help button
  $('#help').click(function() {
    $("#helpDialog").modal();
  });

  // Every time a modal is shown, if it has an autofocus element, focus on it.
  $('.modal').on('shown.bs.modal', function() {
    $(this).find('[autofocus]').focus();
  });



});
