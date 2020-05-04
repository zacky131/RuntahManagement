$('#orders').dataTable();
var rootRef = firebase.database().ref();
rootRef.on("child_added", snap => {
    var SerialNumber = data.val().SerialNumber;
    var Latitude = data.val().Latitude;
    var Longitude = data.val().Longitude;
    var FillLevel = data.val().FillLevel;

    $("#example").append("<tr id='" + key + "'><td>" + key + "</td><td>" +
    SerialNumber + "</td><td>" + Latitude + "</td><td>" + Longitude + "</td><td>" +
    FillLevel + "</td><td>" );
}
);
