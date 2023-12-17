// Function to format a Date object into 'YYYY-MM-DD' format
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

// Function to get the current date and calculate the start and end of the week
function getWeekStartAndEnd() {
    const today = new Date();
    const dayOfWeek = today.getDay();
    const startOffset = (dayOfWeek === 0 ? -6 : 1) - dayOfWeek; // Adjust if the week starts on Sunday

    // Start of the week (Monday)
    let startOfWeek = new Date(today);
    startOfWeek.setDate(today.getDate() + startOffset);

    // End of the week (Sunday)
    let endOfWeek = new Date(startOfWeek);
    endOfWeek.setDate(startOfWeek.getDate() + 6);

    return {
        startOfWeek: formatDate(startOfWeek),
        endOfWeek: formatDate(endOfWeek)
    };
}

// Helper function to convert a time string to a Date object
function timeStringToDate(timeString, baseDate) {
    const [hours, minutes, seconds] = timeString.split(':').map(Number);
    const date = new Date(baseDate);
    date.setHours(hours, minutes, seconds);
    return date;
}

// Function to calculate total time
function calculateTotalTime(punches) {
    let totalTime = 0; // Total time in milliseconds
    let lastClockInTime = null;

    punches.forEach(punch => {
        if (punch.status === 'Clock-In') {
            lastClockInTime = timeStringToDate(punch.timestamp, punch.date);
        } else if (punch.status === 'Clock-Out' && lastClockInTime) {
            const clockOutTime = timeStringToDate(punch.timestamp, punch.date);
            totalTime += clockOutTime - lastClockInTime;
            lastClockInTime = null; // Reset after calculating a pair
        }
    });

    const formattedTotalTime = formatDuration(totalTime);
    return formattedTotalTime ; // Display the total time in the console
}

// Function to format duration from milliseconds to HH:MM:SS
function formatDuration(milliseconds) {
    let seconds = Math.floor(milliseconds / 1000);
    let minutes = Math.floor(seconds / 60);
    seconds = seconds % 60;
    let hours = Math.floor(minutes / 60);
    minutes = minutes % 60;

    return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
}

// Function to load timecard
async function getTimeCard(){
    var employeeId;
    var profileData = JSON.parse(sessionStorage.getItem('employee'));
    if (profileData) {
        employeeId = profileData.employeeId;
    }

    var startOfWeek = getWeekStartAndEnd().startOfWeek;
    var endOfWeek = getWeekStartAndEnd().endOfWeek;

    queryDto = {
        "query": employeeId + "," + "thisweek" + "," + startOfWeek + "," + endOfWeek
    }

   fetch("/api/v1/timecard", {
       method: "PUT",
       headers: {
           "Content-Type": "application/json"
       },
       body: JSON.stringify(queryDto)
   })
   .then(response => {
       // Check if the response is OK (status code in the range 200-299)
       if (!response.ok) {
           throw new Error('Network response was not ok');
       }
       return response.json(); // Parse the response as JSON
   })
   .then(data => {
       console.log(data);
       sessionStorage.setItem("timeCardId", data.id);
       if (data && Array.isArray(data.punches)) {
           updateMyPay(data.punches, 'pay-break-down', 'No punches for today');
       } else {
           console.error('Invalid or missing punches data:', data);
       }
   })
   .catch(error => {
       console.error('Problem fetching punches:', error);
   });
}

// Function to calculate Pay with houly rate and Time
function calculatePay(hourlyRate, timeDuration) {
    // Split the time duration into hours, minutes, and seconds
    const [hours, minutes, seconds] = timeDuration.split(':').map(Number);

    // Convert the time duration to hours in decimal form
    const totalHours = hours + (minutes / 60) + (seconds / 3600);

    // Calculate the pay
    return hourlyRate * totalHours;
}

// Function to update the Punches on the HTML
function updateMyPay(punches, elementId, defaultMessage) {

    var employeeRate;
    var pretaxAmount;
    var todaysDate = formatDate(new Date());
    var profileData = JSON.parse(sessionStorage.getItem('employee'));
    var totalHoursWorked = calculateTotalTime(punches)

     if (profileData) {
       employeeRate = profileData.payRate;
       pretaxAmount = calculatePay(employeeRate, totalHoursWorked)
    }

    var payBreakDownElement = document.getElementById(elementId);
    payBreakDownElement.innerHTML = '';

    if (punches.length === 0) {
        payBreakDownElement.innerHTML = `<p>${defaultMessage}</p>`;
    }
    else {
        var payDiv = document.createElement('div');
        payDiv.className = 'pay';
        payDiv.innerHTML = `
            <h4>MyPay Breakdown</h4>
            <hr>
            <p>Your <strong>Hourly Rate: </strong>$ ${employeeRate}</p>
            <p>Total <strong>Hours Worked: </strong>${totalHoursWorked}</p>
            <br>
            <p><strong>Total Pre-Tax: </strong>$ ${pretaxAmount}</p>
        `;
        payBreakDownElement.appendChild(payDiv);
    }
}


// Functions to call when the page loads
document.addEventListener("DOMContentLoaded", function() {
    getTimeCard();
});
