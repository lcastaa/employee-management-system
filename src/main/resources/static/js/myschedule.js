// Function to format the Date to YYYY-MM-DD
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

// Function to get the current date
function getCurrentDate() {
    let today = new Date();
    return formatDate(today);
}

// Function to split the date into an array [YYYY, MM, DD]
function dateSplitter(date){
    return date.split("-");
}


// Function to display the TimeCards for the month on the HTML
function updateMonthlyPunchesDisplay(data, elementId, defaultMessage) {
    var punchesElement = document.getElementById(elementId);
    punchesElement.innerHTML = '';

    if (!data || data.length === 0) {
        punchesElement.innerHTML = `<p>${defaultMessage}</p>`;
        return;
    }

    data.forEach(timecard => {
        var timecardSection = document.createElement('section');
        timecardSection.className = 'timecard';
        timecardSection.innerHTML = `<h4>Timecard (Start: ${timecard.startDate}, End: ${timecard.endDate})</h4>`;

        timecard.punches.forEach(punch => {
            var punchDiv = document.createElement('div');
            punchDiv.className = 'punch';
            punchDiv.innerHTML = `
                <p>Date: ${punch.date}</p>
                <p>Status: ${punch.status}</p>
                <p>Time: ${punch.timestamp}</p>
                <hr>
            `;
            timecardSection.appendChild(punchDiv);
        });

        punchesElement.appendChild(timecardSection);
    });
}

// Function to fetch and display time cards for the month and year
async function getTimeCardsThisMonthAndYear(){
    var profileData = JSON.parse(sessionStorage.getItem('employee'));
    var employeeId = profileData ? profileData.employeeId : null;

    if (!employeeId) {
        console.error('Employee ID is not available');
        return;
    }

    let date = getCurrentDate();
    let dateArr = dateSplitter(date);

    var queryDto = {
        "query": `${employeeId},bymonthyear,${dateArr[1]},${dateArr[0]}`
    };

    fetch("/api/v1/timecard", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(queryDto)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        updateMonthlyPunchesDisplay(data, 'dates', 'No time cards available for this month');
    })
    .catch(error => {
        console.error('Problem fetching punches:', error);
    });
}

// Event listener for DOMContentLoaded
document.addEventListener("DOMContentLoaded", function() {
    getTimeCardsThisMonthAndYear();
});
