/**
 * modals.js
 * Handles the dynamic generation and display of login and registration forms.
 */

export function openModal(type) {
    let modalContent = '';
    
    if (type === 'addDoctor') {
        modalContent = `
            <h2>Add Doctor</h2>
            <input type="text" id="doctorName" placeholder="Doctor Name" class="input-field">
            <select id="specialization" class="input-field select-dropdown">
                <option value="">Specialization</option>
                <option value="cardiologist">Cardiologist</option>
                <option value="dermatologist">Dermatologist</option>
                <option value="neurologist">Neurologist</option>
                <option value="pediatrician">Pediatrician</option>
                <option value="orthopedic">Orthopedic</option>
                <option value="gynecologist">Gynecologist</option>
                <option value="psychiatrist">Psychiatrist</option>
                <option value="dentist">Dentist</option>
                <option value="ophthalmologist">Ophthalmologist</option>
                <option value="ent">ENT Specialist</option>
                <option value="urologist">Urologist</option>
                <option value="oncologist">Oncologist</option>
                <option value="gastroenterologist">Gastroenterologist</option>
                <option value="general">General Physician</option>
            </select>
            <input type="email" id="doctorEmail" placeholder="Email" class="input-field">
            <input type="password" id="doctorPassword" placeholder="Password" class="input-field">
            <input type="text" id="doctorPhone" placeholder="Mobile No." class="input-field">
            <div class="availability-container">
                <label class="availabilityLabel">Select Availability:</label>
                <div class="checkbox-group">
                    <label><input type="checkbox" name="availability" value="09:00-10:00"> 9:00 AM - 10:00 AM</label>
                    <label><input type="checkbox" name="availability" value="10:00-11:00"> 10:00 AM - 11:00 AM</label>
                    <label><input type="checkbox" name="availability" value="11:00-12:00"> 11:00 AM - 12:00 PM</label>
                    <label><input type="checkbox" name="availability" value="12:00-13:00"> 12:00 PM - 1:00 PM</label>
                </div>
            </div>
            <button class="dashboard-btn" onclick="window.adminAddDoctor()">Save</button>
        `;
    } else if (type === 'patientLogin') {
        modalContent = `
            <h2>Patient Login</h2>
            <input type="text" id="email" placeholder="Email" class="input-field">
            <input type="password" id="password" placeholder="Password" class="input-field">
            <button class="dashboard-btn" onclick="window.loginPatient()">Login</button>
            <p>Don't have an account? <a href="#" onclick="openModal('patientSignup')">Sign Up</a></p>
        `;
    } else if (type === "patientSignup") {
        modalContent = `
            <h2>Patient Signup</h2>
            <input type="text" id="name" placeholder="Name" class="input-field">
            <input type="email" id="email" placeholder="Email" class="input-field">
            <input type="password" id="password" placeholder="Password" class="input-field">
            <input type="text" id="phone" placeholder="Phone" class="input-field">
            <input type="text" id="address" placeholder="Address" class="input-field">
            <button class="dashboard-btn" onclick="window.signupPatient()">Signup</button>
        `;
    } else if (type === 'adminLogin') {
        modalContent = `
            <h2>Admin Login</h2>
            <input type="text" id="username" name="username" placeholder="Username" class="input-field">
            <input type="password" id="password" name="password" placeholder="Password" class="input-field">
            <button class="dashboard-btn" onclick="window.adminLoginHandler()">Login</button>
        `;
    } else if (type === 'doctorLogin') {
        modalContent = `
            <h2>Doctor Login</h2>
            <input type="text" id="email" placeholder="Email" class="input-field">
            <input type="password" id="password" placeholder="Password" class="input-field">
            <button class="dashboard-btn" onclick="window.doctorLoginHandler()">Login</button>
        `;
    }

    const modalBody = document.getElementById('modal-body');
    const modalContainer = document.getElementById('modal');
    
    if (modalBody) modalBody.innerHTML = modalContent;
    if (modalContainer) modalContainer.style.display = 'block';

    const closeBtn = document.getElementById('closeModal');
    if (closeBtn) {
        closeBtn.onclick = () => {
            modalContainer.style.display = 'none';
        };
    }
}