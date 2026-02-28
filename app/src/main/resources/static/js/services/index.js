import { openModal } from '../components/modals.js';
import { API_BASE_URL } from '../config/config.js';

// --- THE FIX: Use API_BASE_URL here instead of "localhost" ---
const ADMIN_API = `${API_BASE_URL}/admin/login`;
const DOCTOR_API = `${API_BASE_URL}/doctor/login`;
const PATIENT_API = `${API_BASE_URL}/patient/login`;
const PATIENT_SIGNUP_API = `${API_BASE_URL}/patient`;
const ADD_DOCTOR_API = `${API_BASE_URL}/doctor`;

window.onload = function () {
    const adminBtn = document.getElementById('admin-btn');
    if (adminBtn) adminBtn.addEventListener('click', () => openModal('adminLogin'));

    const doctorBtn = document.getElementById('doctor-btn');
    if (doctorBtn) doctorBtn.addEventListener('click', () => openModal('doctorLogin'));

    const patientBtn = document.getElementById('patient-btn');
    if (patientBtn) patientBtn.addEventListener('click', () => openModal('patientLogin'));
};

/**
 * GLOBAL LOGIN HANDLERS
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
            alert("Login Successful!");
            window.location.href = '/admin-dashboard'; 
        } else {
            alert('Invalid Admin credentials!');
        }
    } catch (error) {
        console.error('Login error:', error);
        alert('Could not connect to the server.');
    }
};

window.doctorLoginHandler = async function () {
    const email = document.getElementById('email')?.value || '';
    const password = document.getElementById('password')?.value || '';

    try {
        const response = await fetch(DOCTOR_API, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password })
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('token', data.token || data.jwt || data);
            window.location.href = '/doctor-dashboard';
        } else {
            alert('Invalid Doctor credentials!');
        }
    } catch (error) {
        alert('Could not connect to the server.');
    }
};

window.loginPatient = async function () {
    const email = document.getElementById('email')?.value || '';
    const password = document.getElementById('password')?.value || '';

    try {
        const response = await fetch(PATIENT_API, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password })
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('token', data.token || data.jwt || data);
            window.location.href = '/patient-dashboard';
        } else {
            alert('Invalid Patient credentials!');
        }
    } catch (error) {
        alert('Could not connect to the server.');
    }
};

window.signupPatient = async function () {
    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const phone = document.getElementById('phone').value;
    const address = document.getElementById('address').value;

    try {
        const response = await fetch(PATIENT_SIGNUP_API, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ name, email, password, phone, address })
        });

        if (response.ok) {
            alert("Signup successful! Please login.");
            openModal('patientLogin');
        } else {
            alert("Signup failed.");
        }
    } catch (error) {
        alert("Server error during signup.");
    }
};

window.adminAddDoctor = async function () {
    const name = document.getElementById('doctorName').value;
    const speciality = document.getElementById('specialization').value;
    const email = document.getElementById('doctorEmail').value;
    const password = document.getElementById('doctorPassword').value;

    try {
        const response = await fetch(ADD_DOCTOR_API, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ name, speciality, email, password })
        });

        if (response.ok) {
            alert("Doctor added successfully!");
            document.getElementById('modal').style.display = 'none';
        } else {
            alert("Failed to add doctor.");
        }
    } catch (error) {
        alert("Server error adding doctor.");
    }
};