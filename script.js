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
  //Load the custom image icon into the map resources.
  map.imageSprite.add('my-custom-icon', 'https://ichef.bbci.co.uk/images/ic/720x405/p07xty0h.jpg').then(function () {

    var points = [
      new atlas.data.Feature(new atlas.data.Point([0.10755819733165595, 52.21099577761569]), { id: 1 }),
      new atlas.data.Feature(new atlas.data.Point([0.1257543032893409, 52.21281020658273]), { id: 2 }),
      new atlas.data.Feature(new atlas.data.Point([0.1359252398906392, 52.213309818944765]), { id: 3 }),
      new atlas.data.Feature(new atlas.data.Point([0.10597032967257292, 52.20636733337227]), { id: 4 }),
      new atlas.data.Feature(new atlas.data.Point([0.11064810219414767, 52.20752448964299]), { id: 5 }),
      new atlas.data.Feature(new atlas.data.Point([0.11790079537115616, 52.20889199910957]), { id: 6 }),
      new atlas.data.Feature(new atlas.data.Point([0.12832922402049007, 52.20786637095554]), { id: 7 }),
      new atlas.data.Feature(new atlas.data.Point([0.1374701923435282, 52.20918127456463]), { id: 8 }),
      new atlas.data.Feature(new atlas.data.Point([0.14558119244316003, 52.21033835756063]), { id: 9 }),
      new atlas.data.Feature(new atlas.data.Point([0.1543788380114961, 52.21344129497348]), { id: 10 }),
      new atlas.data.Feature(new atlas.data.Point([0.1348523563839592, 52.20584134329653]), { id: 11 }),
      new atlas.data.Feature(new atlas.data.Point([0.1487140125720714, 52.206761821859914]), { id: 12 }),
      new atlas.data.Feature(new atlas.data.Point([0.10524076893736378, 52.20145148638474]), { id: 13 }),
      new atlas.data.Feature(new atlas.data.Point([0.1073007054600339, 52.202687675323745]), { id: 14 }),
      new atlas.data.Feature(new atlas.data.Point([0.11970323994509613, 52.20450244359586]), { id: 15 }),
      new atlas.data.Feature(new atlas.data.Point([0.14553827717469403, 52.20384492742036]), { id: 16 }),
      new atlas.data.Feature(new atlas.data.Point([0.12579721887522055, 52.200972377759115]), { id: 17 }),
      new atlas.data.Feature(new atlas.data.Point([0.13111872156000004, 52.20247159734791]), { id: 18 }),
      new atlas.data.Feature(new atlas.data.Point([0.15489382228204818, 52.2017877490762]), { id: 19 }),
      new atlas.data.Feature(new atlas.data.Point([0.10703086861963129, 52.20045484646917]), { id: 20 }),
      new atlas.data.Feature(new atlas.data.Point([0.11668682107264772, 52.19766665712484]), { id: 21 }),
      new atlas.data.Feature(new atlas.data.Point([0.13089180001676937, 52.19903446998069]), { id: 22 }),
      new atlas.data.Feature(new atlas.data.Point([0.13758659371757176, 52.199849876847935]), { id: 23 }),
      new atlas.data.Feature(new atlas.data.Point([0.1521778107892544, 52.1993764166246]), { id: 24 }),
      new atlas.data.Feature(new atlas.data.Point([0.15715599072200348, 52.19979727041172]), { id: 25 }),
      new atlas.data.Feature(new atlas.data.Point([0.10019138751292189, 52.19754715969299]), { id: 26 }),
      new atlas.data.Feature(new atlas.data.Point([0.10800198016477225, 52.1966001851269]), { id: 27 }),
      new atlas.data.Feature(new atlas.data.Point([0.12508228717140923, 52.19833628975351]), { id: 28 }),
      new atlas.data.Feature(new atlas.data.Point([0.1430921631196327, 52.19799433511224]), { id: 29 }),
      new atlas.data.Feature(new atlas.data.Point([0.1487999039025567, 52.19823107321963]), { id: 30 })
    ]

    //Create a data source and add it to the map.
    var datasource = new atlas.source.DataSource();
    map.sources.add(datasource);

    //Create a point feature and add it to the data source.
    datasource.add(points);

    //Add a layer for rendering point data as symbols.
    map.layers.add(new atlas.layer.SymbolLayer(datasource, null, {
      iconOptions: {
        //Pass in the id of the custom icon that was loaded into the map resources.
        image: 'my-custom-icon',

        //Optionally scale the size of the icon.
        size: 0.1
      },
    }));
    debugger;
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
