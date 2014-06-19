/* 
 * WATER SHIP DOWN - Webtechnolody - HTWG Konstanz
 * (c)2014 - Artur Bauer, Johannes Dangelmaier, Dennis Weber
 */

$('document').ready(function() {

  /* JSON
   -------------------------------------------------- */
  var object = {
    "ships": {
      "coordinates": [
        ]
    },
    "hits": {
      "coordinates": [
      ]
    },
    "missed": {
      "coordinates": [
      ]
    }
  };


  /* SET CSS-CLASSES IN MAPS
   -------------------------------------------------- */
  var shipsCoord = object.ships.coordinates;
  for (coordinate in shipsCoord) {
    showShips(1, shipsCoord[coordinate].x, shipsCoord[coordinate].y);
  }
  ;
  var hitsCoord = object.hits.coordinates;
  for (coordinate in hitsCoord) {
    showHits(2, hitsCoord[coordinate].x, hitsCoord[coordinate].y);
  }
  ;
  var missedCoord = object.missed.coordinates;
  for (coordinate in missedCoord) {
    showMissed(2, missedCoord[coordinate].x, missedCoord[coordinate].y);
  }
  ;
  function showHits(map, row, col) {
    $('#map' + map + ' > ul:nth-child(' + row + ') > li:nth-child(' + col + ')').addClass('hit disable');
  }
  ;
  function showMissed(map, row, col) {
    $('#map' + map + ' > ul:nth-child(' + row + ') > li:nth-child(' + col + ')').addClass('missed disable');
  }
  ;
  function showTargets(map, row, col) {
    $('#map' + map + ' > ul:nth-child(' + row + ') > li:nth-child(' + col + ')').addClass('target disable');
  }
  ;
  function showShips(map, row, col) {
    $('#map' + map + ' > ul:nth-child(' + row + ') > li:nth-child(' + col + ')').addClass('ship');
  }
  ;

  /* toogle selected targets */
  $('.clickable > ul > li').click(function() {
    if (!$(this).hasClass('disable')) {
      $(this).toggleClass('target');
    }
  });


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

  // toggle control
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



});

