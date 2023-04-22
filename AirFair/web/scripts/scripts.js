console.log("Hello, world!");

//get value input in each field and store in a user object
var user = {
    "email": "",
    "local_airport": "",
    "dest_airport": "",
    "depart_date": "",
    "return_date": "",
    "max_price": 0.00,
}

function ClearFields(textfield) {

    document.getElementById("textfield").value = " ";
}