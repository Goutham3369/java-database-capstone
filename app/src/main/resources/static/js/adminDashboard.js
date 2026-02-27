import { openModal } from './components/modals.js';
import { getDoctors, filterDoctors, saveDoctor } from './services/doctorServices.js';
import { createDoctorCard } from './components/doctorCard.js';

document.addEventListener("DOMContentLoaded", () => {
    const addDocBtn = document.getElementById('addDocBtn');
    if (addDocBtn) {
        addDocBtn.addEventListener('click', () => {
            openModal('addDoctor');
        });
    }

    loadDoctorCards();

    const searchBar = document.getElementById("searchBar");
    const filterTime = document.getElementById("sortByTime") || document.getElementById("filterTime");
    const filterSpecialty = document.getElementById("filterBySpecialty") || document.getElementById("filterSpecialty");

    if (searchBar) searchBar.addEventListener("input", filterDoctorsOnChange);
    if (filterTime) filterTime.addEventListener("change", filterDoctorsOnChange);
    if (filterSpecialty) filterSpecialty.addEventListener("change", filterDoctorsOnChange);
});

async function loadDoctorCards() {
    try {
        const doctors = await getDoctors();
        renderDoctorCards(doctors);
    } catch (error) {
        console.error(error);
    }
}

async function filterDoctorsOnChange() {
    const searchBar = document.getElementById("searchBar");
    const filterTime = document.getElementById("sortByTime") || document.getElementById("filterTime");
    const filterSpecialty = document.getElementById("filterBySpecialty") || document.getElementById("filterSpecialty");

    const name = searchBar && searchBar.value.trim() !== "" ? searchBar.value.trim() : null;
    const time = filterTime && filterTime.value !== "" ? filterTime.value : null;
    const specialty = filterSpecialty && filterSpecialty.value !== "" ? filterSpecialty.value : null;

    try {
        const doctors = await filterDoctors(name, time, specialty);
        const contentDiv = document.getElementById("content");
        
        if (doctors && doctors.length > 0) {
            renderDoctorCards(doctors);
        } else if (contentDiv) {
            contentDiv.innerHTML = "<p>No doctors found.</p>";
        }
    } catch (error) {
        console.error(error);
        alert("An error occurred while filtering doctors.");
    }
}

function renderDoctorCards(doctors) {
    const contentDiv = document.getElementById("content");
    if (!contentDiv) return;
    
    contentDiv.innerHTML = "";
    
    doctors.forEach(doctor => {
        const card = createDoctorCard(doctor);
        contentDiv.appendChild(card);
    });
}

window.adminAddDoctor = async function (event) {
    if (event) event.preventDefault();

    const name = document.getElementById("docName") ? document.getElementById("docName").value : "";
    const specialty = document.getElementById("docSpecialty") ? document.getElementById("docSpecialty").value : "";
    const email = document.getElementById("docEmail") ? document.getElementById("docEmail").value : "";
    const password = document.getElementById("docPassword") ? document.getElementById("docPassword").value : "";
    const phone = document.getElementById("docPhone") ? document.getElementById("docPhone").value : "";
    
    const availableTimesNodes = document.querySelectorAll("input[name='availability']:checked");
    let availableTimes = [];
    if (availableTimesNodes.length > 0) {
        availableTimes = Array.from(availableTimesNodes).map(node => node.value);
    } else {
        const timeSelect = document.getElementById("docAvailability");
        if (timeSelect && timeSelect.value) {
            availableTimes.push(timeSelect.value);
        }
    }

    const token = localStorage.getItem("token");
    if (!token) {
        alert("Session expired. Please log in as admin.");
        return;
    }

    const newDoctor = {
        name,
        specialty,
        email,
        password,
        phone,
        availableTimes
    };

    const result = await saveDoctor(newDoctor, token);
    
    if (result.success) {
        alert(result.message || "Doctor added successfully!");
        const modal = document.getElementById("modal");
        if (modal) modal.style.display = "none";
        loadDoctorCards();
    } else {
        alert(result.message || "Failed to add doctor.");
    }
};