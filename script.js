//Create an instance of the map control and set some options.
var map = new atlas.Map('myMap', {
  center: [0.13, 52.205],
  zoom: 14,
  language: 'en-UK',
  authOptions: {
    authType: 'subscriptionKey',
    subscriptionKey: 'Q7QiCa3REAUKcQDfoXaOeciRvN0THdwzmhNx0t8KbWo'
  }
});
map.events.add('ready', function () {

  /* Construct a zoom control*/
  var zoomControl = new atlas.control.ZoomControl();

  /* Add the zoom control to the map*/
  map.controls.add(zoomControl, {
    position: "bottom-right"
  });
  /* Update the style of mouse cursor to a pointer */
  map.getCanvasContainer().style.cursor = "pointer";
  /* Create a popup */
  var popup = new atlas.Popup();

  /* Upon a mouse click, open a popup at the clicked location and render in the popup the address of the clicked location*/
  map.events.add("click", function (e) {
    //send a request to Azure Maps reverse address search API */
    var url = "https://atlas.microsoft.com/search/address/reverse/json?";
    url += "&api-version=1.0";
    url += "&query=" + e.position[1] + "," + e.position[0];

    /*Process request*/
    fetch(url, {
      headers: {
        "Authorization": "Bearer " + map.authentication.getToken(),
        "x-ms-client-id": "985e5872-eb9f-448a-a249-728d852360a5"
      }
    }).then(function (response) {
      return response.json();
    }).then(function (res) {
      var popupContent = document.createElement("div");
      popupContent.classList.add("popup-content");
      var address = res["addresses"];
      popupContent.innerHTML =
        address.length !== 0
          ? address[0]["address"]["freeformAddress"]
          : "No address for that location!";
      popup.setOptions({
        position: e.position,
        content: popupContent
      });
      // render the popup on the map 
      popup.open(map);
    });
  });
});