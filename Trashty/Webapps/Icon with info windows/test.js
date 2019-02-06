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

// This example displays a marker at the center of Australia.
// When the user clicks the marker, an info window opens.

function initMap() {
  var uluru = {lat: -6.224448, lng: 106.926710};
  var map = new google.maps.Map(document.getElementById('map'), {
    zoom: 16,
    center: uluru
  });

  var contentString = 'smart trashbin'

  var infowindow = new google.maps.InfoWindow({
    content: contentString
  });

  var marker = new google.maps.Marker({
    position: uluru,
    map: map,
    title: '80 %'
  });
  marker.addListener('click', function() {
    infowindow.open(map, marker);
  });
}