function initialize() {
  var infowindow = new google.maps.InfoWindow();
  var map = new google.maps.Map(
    document.getElementById("map_canvas"), {
      center: new google.maps.LatLng(37.4419, -122.1419),
      zoom: 13,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    });
  // Initialize Firebase
  var config = {
    apiKey: "AIzaSyAeI6a8xlYIzMepZgpjspF8bDDFvlxtoBU",
    authDomain: "map-webapps-database.firebaseio.com",
    databaseURL: "https://map-webapps-database.firebaseio.com/",
    projectId: "map-webapps-database",
    storageBucket: "",
    messagingSenderId: "509904089954"
  };
  firebase.initializeApp(config);

  //Create a node at firebase location to add locations as child keys
  var locationsRef = firebase.database().ref("locations");
  var bounds = new google.maps.LatLngBounds();
  locationsRef.on('child_added', function(snapshot) {
    var data = snapshot.val();
    console.log(data);
    var marker = new google.maps.Marker({
      position: {
        lat: data.lat,
        lng: data.lng
      },
      map: map
    });
    bounds.extend(marker.getPosition());
    marker.addListener('click', (function(data) {
      return function(e) {
        infowindow.setContent(data.name + "<br>" + this.getPosition().toUrlValue(6) + "<br>" + data.message);
        infowindow.open(map, this);
      }
    }(data)));
    map.fitBounds(bounds);
  });
}
google.maps.event.addDomListener(window, "load", initialize);
