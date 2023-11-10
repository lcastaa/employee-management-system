function formatName(firstName, lastName) {
   return firstName + " " + lastName;
}

window.onload = function() {
   var profileData = JSON.parse(sessionStorage.getItem('employee'));
   if (profileData) {
       document.getElementById('employeeId').textContent = profileData.employeeId;
       document.getElementById('username').textContent = profileData.username;
       document.getElementById('fullName').textContent = formatName(profileData.firstName, profileData.lastName);
       document.getElementById('email').textContent = profileData.email;
       document.getElementById('phoneNumber').textContent = profileData.phoneNumber;
       document.getElementById('dateOfBirth').textContent = profileData.dateOfBirth;
       document.getElementById('payRate').textContent = profileData.payRate.toFixed(2);
       document.getElementById('role').textContent = profileData.role;
   }
};