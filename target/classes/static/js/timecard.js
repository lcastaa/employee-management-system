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

// Function to load timecard
async function getTimeCard(){
    var employeeId;
    var profileData = JSON.parse(sessionStorage.getItem('employee'));
    if (profileData) {
        employeeId = profileData.employeeId;
    }

    var startOfWeek = getWeekStartAndEnd().startOfWeek;
    var endOfWeek = getWeekStartAndEnd().endOfWeek;

    // Fetch TimeCard
    fetch('/api/v1/timecard?employeeId=' + employeeId + '&startDate=' + startOfWeek + '&endDate=' + endOfWeek)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            sessionStorage.setItem("timeCardId", data.id);
            if (data && Array.isArray(data.punches)) {
                updatePunchesDisplay(data.punches, 'punches', 'No punches for today');
            } else {
                console.error('Invalid or missing punches data:', data);
            }
        })
        .catch(error => {
            console.error('Problem fetching punches:', error);
        });
}

// Function to update the Punches on the HTML
function updatePunchesDisplay(punches, elementId, defaultMessage) {
    var todaysDate = formatDate(new Date());

    var punchesElement = document.getElementById(elementId);
    punchesElement.innerHTML = '';

    // Filter punches for today's date
    var todaysPunches = punches.filter(punch => {
        return punch.date === todaysDate;
    });

    if (todaysPunches.length === 0) {
        punchesElement.innerHTML = `<p>${defaultMessage}</p>`;
    } else {
        todaysPunches.forEach(punch => {
            var punchDiv = document.createElement('div');
            punchDiv.className = 'punch';
            punchDiv.innerHTML = `
                <h5>Date: ${punch.date}</h5>
                <h5>Status: ${punch.status}</h5>
                <p>Time: ${punch.timestamp || 'Not recorded'}</p>
                <hr>
            `;
            punchesElement.appendChild(punchDiv);
        });
    }
}


// Functions to call when the page loads
document.addEventListener("DOMContentLoaded", function() {
    getTimeCard();
});

