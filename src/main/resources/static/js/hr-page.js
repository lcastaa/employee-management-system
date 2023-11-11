document.addEventListener("DOMContentLoaded", function() {
    addEventListeners();
    loadTickets();
});

function addEventListeners() {
    const submitHrTicketButton = document.getElementById("hr-ticket-submit");
    if (submitHrTicketButton) {
        submitHrTicketButton.addEventListener("click", submitHrTicket);
    }
}

function submitHrTicket(event) {
    event.preventDefault(); // Prevent form submission if button is inside a form

    var employeeId = null; // Default to null if employee data is not available
    var profileData = JSON.parse(sessionStorage.getItem('employee'));
    if (profileData) {
        employeeId = profileData.employeeId;
    }

    var subject = document.getElementById("subjectInput").value;
    var message = document.getElementById("messageInput").value;
    var dateCreated = getTodaysDateInSqlFormat();

    // Log data for debugging
    console.log(subject, employeeId, message);

    var data = {
        employeeId: employeeId,
        subject: subject,
        message: message,
        dateCreated: dateCreated,
        dateClosed: '0000-00-00',
        isResolved: false
    };

    fetch("/api/v1/hr", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(function (response) {
        if (response.ok) {
            return response.json();
        } else {
            alert("Transfering Request Failed!");
            throw new Error("Request to submit HR ticket failed.");
        }
    })
    .then(function (data) {
        console.log("HR ticket submitted successfully:", data);
        alert("HR ticket submitted successfully!");
        window.location.href = "/ui/hr";
    })
    .catch(function (error) {
        console.error("HR ticket submission failed:", error);
    });
}

function loadTickets() {
    var employeeId;
    var profileData = JSON.parse(sessionStorage.getItem('employee'));
    if (profileData) {
        employeeId = profileData.employeeId;
    }

    // Fetch resolved tickets
    fetch('/api/v1/hr?employeeId=' + employeeId + '&isResolved=true')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            updateTicketsUI(data, 'resolvedTickets', 'No Resolved Tickets');
        })
        .catch(error => {
            console.error('Problem fetching resolved tickets:', error);
        });

    // Fetch active tickets
    fetch('/api/v1/hr?employeeId=' + employeeId + '&isResolved=false')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            updateTicketsUI(data, 'activeTickets', 'No Active Tickets');
        })
        .catch(error => {
            console.error('Problem fetching active tickets:', error);
        });
}

function updateTicketsUI(tickets, elementId) {
    var ticketsElement = document.getElementById(elementId);
    ticketsElement.innerHTML = ''; // Clear existing content

    if (tickets.length === 0) {
        ticketsElement.innerHTML = '<p>No Tickets Found</p>';
    } else {
        tickets.forEach(ticket => {
            var ticketDiv = document.createElement('div');
            ticketDiv.className = 'ticket';
            ticketDiv.innerHTML = `
                <h5>${ticket.subject}</h5>
                <p>Date Created: ${ticket.dateCreated}</p>
                <hr>
            `;
            ticketsElement.appendChild(ticketDiv);
        });
    }
}


function getTodaysDateInSqlFormat() {
    const today = new Date();
    const year = today.getFullYear();
    const month = ('0' + (today.getMonth() + 1)).slice(-2);
    const day = ('0' + today.getDate()).slice(-2);
    return `${year}-${month}-${day}`;
}
