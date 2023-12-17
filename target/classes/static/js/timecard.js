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

// Function to get the start and end of the week
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
    var profileData = JSON.parse(sessionStorage.getItem('employee'));
    var employeeId = profileData ? profileData.employeeId : null;

    if (!employeeId) {
        console.error('Employee ID is not available');
        return;
    }

    var { startOfWeek, endOfWeek } = getWeekStartAndEnd();

    var queryDto = {
        "query": `${employeeId},thisweek,${startOfWeek},${endOfWeek}`
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
        console.log(data);
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

    var todaysPunches = punches.filter(punch => punch.date === todaysDate);

    if (todaysPunches.length === 0) {
        punchesElement.innerHTML = `<p>${defaultMessage}</p>`;
    } else {
        todaysPunches.forEach(punch => {
            var punchDiv = document.createElement('div');
            punchDiv.className = 'punches';
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
