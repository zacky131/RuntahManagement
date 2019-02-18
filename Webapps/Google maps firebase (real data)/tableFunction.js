//For sorting datatable
// $(document).ready(function() {
//     $('#ex-table').DataTable( {
//         "order": [[ 3, "desc" ]]
//     } );
// } );

//Add data to html table
var database = firebase.database().ref().child('locations');
database.once('value', function(snapshot){
    if(snapshot.exists()){
        var content = [];
        snapshot.forEach(function(data){
            var SerialNumber = data.val().SerialNumber;
            var Latitude= data.val().Latitude;
            var Longitude= data.val().Longitude;
            var FillLevel= data.val().FillLevel;
            var TrashDistance= data.val().TrashDistance;
            content += '<tr>';
            content += '<td>' + SerialNumber + '</td>'; //column1
            content += '<td>' + Latitude + '</td>';//column2
            content += '<td>' + Longitude + '</td>';//column3
            content += '<td>' + FillLevel + '</td>';//column4
            content += '</tr>';
        });
        $('#ex-table').append(content);
    }
});



