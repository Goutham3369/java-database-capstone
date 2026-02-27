import { openModal } from '../components/modals.js';
import { API_BASE_URL } from '../config/config.js';

const ADMIN_API = API_BASE_URL + '/admin/login'; // Added /login to match your controller
const DOCTOR_API = API_BASE_URL + '/doctor/login';
const PATIENT_API = API_BASE_URL + '/patient/login';

window.onload = function () {
    // 1. Fix: Changed 'adminLogin' to 'admin-btn' to match index.html
    const adminBtn = document.getElementById('admin-btn');
    if (adminBtn) {
        adminBtn.addEventListener('click', () => {
            openModal('adminLogin'); // This opens the specific modal template
        });
    }

    // 2. Fix: Changed 'doctorLogin' to 'doctor-btn'
    const doctorBtn = document.getElementById('doctor-btn');
    if (doctorBtn) {
        doctorBtn.addEventListener('click', () => {
            openModal('doctorLogin');
        });
    }

    // 3. Added: Patient button listener
    const patientBtn = document.getElementById('patient-btn');
    if (patientBtn) {
        patientBtn.addEventListener('click', () => {
            openModal('patientLogin');
        });
    }
};

/**
 * Global handlers for the login forms inside the modals
 */
window.adminLoginHandler = async function () {
    const username = document.getElementById('username')?.value || '';
    const password = document.getElementById('password')?.value || '';

    try {
        const response = await fetch(ADMIN_API, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('token', data.token || data.jwt || data);
            window.location.href = '/dashboard'; // Redirect on success
        } else {
            alert('Invalid Admin credentials!');
        }
    } catch (error) {
        console.error('Login error:', error);
        alert('Could not connect to the server.');
    }
};

// ... Similar handlers for window.doctorLoginHandler and window.patientLoginHandler