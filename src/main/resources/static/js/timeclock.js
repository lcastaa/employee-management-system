// Updates the Clock on the html side
function updateClock() {
    const now = new Date();
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');

    const timeString = `${hours}:${minutes}:${seconds}`;
    document.getElementById('digitalClock').textContent = timeString;
    return timeString;
}

// Updates the Current Date on the html side
function updateCurrentDate() {
    let today = new Date();
    var formattedDate = formatDate(today);
    document.getElementById('currentDate').textContent = formattedDate;
    return formattedDate;
}

// Formats the Date to YYYY-MM-DD
function formatDate(date) {
    let d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [year, month, day].join('-');
}

// Function to clock-in gets invoked when clock in button is invoked
function clockIn(){
    var date = updateCurrentDate();
    var time = updateClock();
    var status = 'Clock-In';
    postToPunch(date, time, status);
}

// Function to clock-out gets invoked when clock out button is invoked
function clockOut(){
    var date = updateCurrentDate();
    var time = updateClock();
    var status = 'Clock-Out';
    postToPunch(date, time, status);
}

// Function to POST to API
function postToPunch(date, time, status){

    // setting up DTO to send to backend
    var timeCardId = sessionStorage.getItem("timeCardId");
    var data = {
        timeCardId: timeCardId,
        date: date,
        timestamp: time,
        status: status
    };

    console.log(data);

    // End point of the API
    fetch("/api/v1/punch", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        })
        // check what the response is do logic based on response
        .then(function (response) {
            if (response.ok) {
                return response.json(); // Parse JSON response
            } else {
                alert("Alert User");
                throw new Error("Throws error");
            }
        })
        // check the data and do logic with response data
        .then(function (data) {
            // Handle successful login
        })

        // catch any errors and act upon thoes errors
        .catch(function (error) {
            // Handle errors, e.g., display an error message to the user
            console.error("Login failed:", error);
        });

}

// Update the clock every second
setInterval(updateClock, 1000);

// adds listeners for the buttons on the HTML
function addEventListeners() {
    const clockInButton = document.getElementById("clock-in-button");
    const clockOutButton = document.getElementById("clock-out-button")
    if (clockInButton) {
        clockInButton.addEventListener("click", clockIn);
    }
    if (clockOutButton) {
        clockOutButton.addEventListener("click", clockOut);
    }
}

// Functions to call when the page loads
document.addEventListener("DOMContentLoaded", function() {
    // Initialize the clock immediately
    updateClock();

    // Initailize the Date immediately
    updateCurrentDate();

    // Initialized the event listeners for the buttons
    addEventListeners();
});


