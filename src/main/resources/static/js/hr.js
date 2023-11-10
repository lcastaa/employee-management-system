 function addEventListeners() {
     const submitHrTicketButton = document.getElementById("hr-ticket-submit");

     if (submitHrTicketButton) {
         submitHrTicketButton.addEventListener("click", submitHrTicket);
     }
 }

 function submitHrTicket() {
     var subject = document.getElementById("subjectInput").value;
     var message = document.getElementById("messageInput").value;
     var employeeId = sessionStorage.getItem("employee").employeeId.value

     //DEBUG TO ENSURE FORM HAS CORRECT VALUES
     console.log(subject);
     console.log(employeeId);
     console.log(message);

     var data = {
         subject: subject,
         employeeId: employeeId,
         message: message
     };

     fetch("/api/v1/hr/ticket", {
         method: "POST",
         headers: {
             "Content-Type": "application/json"
         },
         body: JSON.stringify(data)
     })
     .then(function (response) {
         if (response.ok) {
             return response.json(); // Parse JSON response
         } else {
             alert("Transfering Request Failed!");
             throw new Error("Request to submit HR ticket failed.");
         }
     })
     .then(function (data) {
         // Handle successful login
         console.log("Request to submit HR ticket was successful, data returned:", data);
         alert("HR ticket submitted successfully!");
         window.location.href = "/ui/hr";
     })
     .catch(function (error) {
         // Handle errors, e.g., display an error message to the user
         console.error("Login failed:", error);
     });
 }

 function loadTickets() {
        // Load active tickets
        var activeTickets = [];
        var activeTicketsElement = document.getElementById('activeTickets');
        if (activeTickets.length === 0) {
            activeTicketsElement.innerHTML = '<p>No Active Tickets</p>';
        } else {
            // Populate with actual ticket data
        }

        // Load resolved tickets
        var resolvedTickets = [];
        var resolvedTicketsElement = document.getElementById('resolvedTickets');
        if (resolvedTickets.length === 0) {
            resolvedTicketsElement.innerHTML = '<p>No Resolved Tickets</p>';
        } else {
            // Populate with actual ticket data
        }
    }

 // Call the function on page load
 window.onload = loadTickets;