$(document).ready(function () {
    $("#submit-button").click(function () {
        //get values from all fields
        user.email = $("#email").val();
        user.local_airport = $("#local-airport").val();
        user.dest_airport = $("#dest-airport").val();
        user.depart_date = $("#depart-date").val();
        user.return_date = $("#return-date").val()
        user.max_price = $("#max-price").val();
        console.log(user);
        //if all fields have been filled
        if (allFieldsFilled) {
            console.log("submitting data to backend");
        }
        else console.log("Please fill in all fields.");
    });
});     

//get value input in each field and store in a user object
var user = {
    "email": "",
    "local_airport": "",
    "dest_airport": "",
    "depart_date": "",
    "return_date": "",
    "max_price": 0.00,
}