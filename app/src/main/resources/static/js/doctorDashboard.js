import { getAllAppointments } from './services/appointmentRecordService.js';
import { createPatientRow } from './components/patientRows.js';

let selectedDate = new Date().toISOString().split('T')[0];
let patientName = "null";
const token = localStorage.getItem("token");

document.addEventListener("DOMContentLoaded", () => {
    if (typeof renderContent === "function") {
        renderContent();
    }

    const datePicker = document.getElementById("dateFilter") || document.getElementById("datePicker");
    if (datePicker) {
        datePicker.value = selectedDate;
        datePicker.addEventListener("change", (e) => {
            selectedDate = e.target.value;
            loadAppointments();
        });
    }

    const todayBtn = document.getElementById("todayBtn") || document.getElementById("todayButton");
    if (todayBtn) {
        todayBtn.addEventListener("click", () => {
            selectedDate = new Date().toISOString().split('T')[0];
            if (datePicker) datePicker.value = selectedDate;
            loadAppointments();
        });
    }

    const searchBar = document.getElementById("searchBar");
    if (searchBar) {
        searchBar.addEventListener("input", (e) => {
            const val = e.target.value.trim();
            patientName = val !== "" ? val : "null";
            loadAppointments();
        });
    }

    loadAppointments();
});

async function loadAppointments() {
    const tableBody = document.querySelector("#patientTable tbody") || document.getElementById("patientTableBody");
    if (!tableBody) return;

    tableBody.innerHTML = "";

    try {
        const appointments = await getAllAppointments(selectedDate, patientName, token);

        if (!appointments || appointments.length === 0) {
            const tr = document.createElement("tr");
            const td = document.createElement("td");
            td.colSpan = 5;
            td.className = "noPatientRecord";
            td.textContent = "No Appointments found for today.";
            tr.appendChild(td);
            tableBody.appendChild(tr);
            return;
        }

        appointments.forEach(appt => {
            const patientData = {
                id: appt.patientId || appt.patient?.id || "N/A",
                name: appt.patientName || appt.patient?.name || "Unknown",
                phone: appt.patientPhone || appt.patient?.phone || "N/A",
                email: appt.patientEmail || appt.patient?.email || "N/A",
                appointmentId: appt.id || appt.appointmentId
            };
            
            const row = createPatientRow(patientData);
            tableBody.appendChild(row);
        });

    } catch (error) {
        console.error(error);
        const tr = document.createElement("tr");
        const td = document.createElement("td");
        td.colSpan = 5;
        td.className = "noPatientRecord";
        td.textContent = "Error loading appointments. Try again later.";
        tr.appendChild(td);
        tableBody.appendChild(tr);
    }
}