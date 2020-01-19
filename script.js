//Create an instance of the map control and set some options.
var map = new atlas.Map('myMap', {
  center: [0.13, 52.205],
  zoom: 14,
  language: 'en-UK',
  authOptions: {
    authType: 'subscriptionKey',
    subscriptionKey: 'Q7QiCa3REAUKcQDfoXaOeciRvN0THdwzmhNx0t8KbWo',
    clientID: "879384a8-948f-40b1-a44b-460e74def766",
    getToken: function (resolve, reject, map) {
      fetch(url).then(function (response) {
        return response.text();
      }).then(function (token) {
        resolve(token);
      });
    }
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
    var popupContent = document.createElement("div");
    popupContent.classList.add("popup-content");
    popupContent.innerHTML =
      e.position
    popup.setOptions({
      position: e.position,
      content: popupContent
    });
    // render the popup on the map 
    popup.open(map);
  });
});
