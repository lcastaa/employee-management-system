function addEventListeners() {
    const loginButton = document.getElementById("cred-submit");

    if (loginButton) {
        loginButton.addEventListener("click", login);
    }
}

function login() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    //DEBUG TO ENSURE FORM HAS CORRECT VALUES
    console.log(username);
    console.log(password);

    var data = {
        username: username,
        password: password
    };

    fetch("/api/v1/authenticate/login", {
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
            alert("Login Failed.");
            throw new Error("Login failed.");
        }
    })
    .then(function (data) {
        // Handle successful login
        console.log("Login successful, data returned:", data);
        alert("Login Successful.");

        var token = data.token;
        var employee = JSON.stringify(data.employee); // Ensure this matches the key in your response
        sessionStorage.setItem("token", token);
        sessionStorage.setItem("employee", employee);
        window.location.href = "/ui/home";
    })
    .catch(function (error) {
        // Handle errors, e.g., display an error message to the user
        console.error("Login failed:", error);
    });
}


// Add event listeners when the DOM content is loaded
document.addEventListener("DOMContentLoaded", addEventListeners);
