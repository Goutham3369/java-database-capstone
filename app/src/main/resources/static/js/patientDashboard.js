import { getDoctors, filterDoctors } from './services/doctorServices.js';
import { openModal } from './components/modals.js';
import { createDoctorCard } from './components/doctorCard.js';
import { patientSignup, patientLogin } from './services/patientServices.js';

document.addEventListener("DOMContentLoaded", () => {
    loadDoctorCards();

    const signupBtn = document.getElementById("patientSignup");
    if (signupBtn) {
        signupBtn.addEventListener("click", () => openModal("patientSignup"));
    }

    const loginBtn = document.getElementById("patientLogin");
    if (loginBtn) {
        loginBtn.addEventListener("click", () => openModal("patientLogin"));
    }

    const searchBar = document.getElementById("searchBar");
    const filterTime = document.getElementById("filterTime");
    const filterSpecialty = document.getElementById("filterSpecialty");

    if (searchBar) searchBar.addEventListener("input", filterDoctorsOnChange);
    if (filterTime) filterTime.addEventListener("change", filterDoctorsOnChange);
    if (filterSpecialty) filterSpecialty.addEventListener("change", filterDoctorsOnChange);
});

function loadDoctorCards() {
    getDoctors()
        .then(doctors => {
            const contentDiv = document.getElementById("content");
            if (!contentDiv) return;
            contentDiv.innerHTML = "";

            if (!doctors || doctors.length === 0) {
                contentDiv.innerHTML = "<p>No doctors currently available.</p>";
                return;
            }

            doctors.forEach(doctor => {
                const card = createDoctorCard(doctor);
                contentDiv.appendChild(card);
            });
        })
        .catch(error => {
            console.error("Failed to load doctors:", error);
            const contentDiv = document.getElementById("content");
            if (contentDiv) contentDiv.innerHTML = "<p>Error loading doctors. Please try again later.</p>";
        });
}

function filterDoctorsOnChange() {
    const searchBarVal = document.getElementById("searchBar")?.value.trim() || "";
    const filterTimeVal = document.getElementById("filterTime")?.value || "";
    const filterSpecialtyVal = document.getElementById("filterSpecialty")?.value || "";

    const name = searchBarVal.length > 0 ? searchBarVal : null;
    const time = filterTimeVal.length > 0 ? filterTimeVal : null;
    const specialty = filterSpecialtyVal.length > 0 ? filterSpecialtyVal : null;

    filterDoctors(name, time, specialty)
        .then(response => {
            const doctors = Array.isArray(response) ? response : (response.doctors || []);
            const contentDiv = document.getElementById("content");
            if (!contentDiv) return;
            contentDiv.innerHTML = "";

            if (doctors.length > 0) {
                doctors.forEach(doctor => {
                    const card = createDoctorCard(doctor);
                    contentDiv.appendChild(card);
                });
            } else {
                contentDiv.innerHTML = "<p>No doctors found with the given filters.</p>";
            }
        })
        .catch(error => {
            console.error("Failed to filter doctors:", error);
            const contentDiv = document.getElementById("content");
            if (contentDiv) contentDiv.innerHTML = "<p>An error occurred while filtering doctors.</p>";
        });
}

window.signupPatient = async function () {
    try {
        const name = document.getElementById("name")?.value || "";
        const email = document.getElementById("email")?.value || "";
        const password = document.getElementById("password")?.value || "";
        const phone = document.getElementById("phone")?.value || "";
        const address = document.getElementById("address")?.value || "";

        const data = { name, email, password, phone, address };
        const { success, message } = await patientSignup(data);
        
        if (success) {
            alert(message);
            const modal = document.getElementById("modal");
            if (modal) modal.style.display = "none";
            window.location.reload();
        } else {
            alert(message);
        }
    } catch (error) {
        console.error("Signup failed:", error);
        alert("❌ An error occurred while signing up.");
    }
};

window.loginPatient = async function () {
    try {
        const email = document.getElementById("email")?.value || "";
        const password = document.getElementById("password")?.value || "";

        const data = { email, password };
        const response = await patientLogin(data);
        
        if (response.ok) {
            const result = await response.json();
            if (typeof selectRole === 'function') {
                selectRole('loggedPatient');
            } else {
                localStorage.setItem("userRole", "loggedPatient");
            }
            localStorage.setItem('token', result.token || result.jwt || result);
            window.location.href = '/pages/loggedPatientDashboard.html';
        } else {
            alert('❌ Invalid credentials!');
        }
    } catch (error) {
        console.error("Error :: loginPatient :: ", error);
        alert("❌ Failed to Login. Please try again.");
    }
};