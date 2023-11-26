// This Javascript is responsible for providing web page functionality to my schedule




// Make a function that returns time cards from the current week and the previous week
function getTimeCardsFromMonthAndYearUsingDate(date){

    //get current date in format of YYYY-MM-dd.


    // make request to backend to retrieve time cards.


    // handle if the request was 2xx.


    // handle if the request was 4xx.
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
