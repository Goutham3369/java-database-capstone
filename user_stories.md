# Patient Appointment Portal - User Stories

##  Admin User Stories

**Title:** Admin - Log In
_As an admin, I want to log into the portal with my username and password, so that I can manage the platform securely._
**Acceptance Criteria:**
1. Username and password fields are visible.
2. Entering correct details redirects to the admin dashboard.
3. Entering incorrect details shows an error message.
**Priority:** High
**Story Points:** 3
**Notes:**
- Passwords must be encrypted.

**Title:** Admin - Log Out
_As an admin, I want to log out of the portal, so that I can protect system access._
**Acceptance Criteria:**
1. A logout button is visible on the dashboard.
2. Clicking the button ends the active session.
3. User is redirected to the public home page.
**Priority:** High
**Story Points:** 1
**Notes:**
- Clear all cookies on logout.

**Title:** Admin - Add Doctors
_As an admin, I want to add doctors to the portal, so that patients can book appointments with them._
**Acceptance Criteria:**
1. An "Add Doctor" form is accessible.
2. Form captures name, specialty, and contact info.
3. Submitting the form updates the database and directory.
**Priority:** High
**Story Points:** 5
**Notes:**
- Ensure duplicate doctor profiles cannot be created.

**Title:** Admin - Delete Doctors
_As an admin, I want to delete a doctor's profile from the portal, so that inactive staff are removed._
**Acceptance Criteria:**
1. A delete button is next to each doctor's name.
2. A warning popup asks for confirmation.
3. Profile is removed from the patient view.
**Priority:** Medium
**Story Points:** 3
**Notes:**
- Handle existing patient appointments before deleting.

**Title:** Admin - Track Usage Statistics
_As an admin, I want to run a stored procedure in MySQL CLI to get the number of appointments per month, so that I can track usage statistics._
**Acceptance Criteria:**
1. The stored procedure exists in the MySQL database.
2. The procedure accepts a month/year parameter.
3. The query returns the accurate total count.
**Priority:** Low
**Story Points:** 2
**Notes:**
- Requires direct database CLI access.

---

## Exercise 3: Patient User Stories

**Title:** Patient - Explore Doctors
_As a patient, I want to view a list of doctors without logging in, so that I can explore options before registering._
**Acceptance Criteria:**
1. A public "Find a Doctor" page exists.
2. The page lists doctor names and specialties.
3. No login prompt blocks this page.
**Priority:** Medium
**Story Points:** 2
**Notes:**
- Add a search or filter bar later.

**Title:** Patient - Sign Up
_As a patient, I want to sign up using my email and password, so that I can book appointments._
**Acceptance Criteria:**
1. Registration form asks for email and password.
2. System checks if the email is already used.
3. Successful registration logs the user in.
**Priority:** High
**Story Points:** 5
**Notes:**
- Send a welcome email upon creation.

**Title:** Patient - Log In
_As a patient, I want to log into the portal, so that I can manage my bookings._
**Acceptance Criteria:**
1. Login page accepts email and password.
2. Authenticated users see their patient dashboard.
3. Failed attempts show an error message.
**Priority:** High
**Story Points:** 3
**Notes:**
- Include a "Forgot Password" link.

**Title:** Patient - Log Out
_As a patient, I want to log out of the portal, so that I can secure my account._
**Acceptance Criteria:**
1. Logout button is accessible from the menu.
2. Session ends immediately upon clicking.
3. Redirects to the login screen.
**Priority:** High
**Story Points:** 1
**Notes:**
- None

**Title:** Patient - Book Appointment
_As a patient, I want to log in and book an hour-long appointment, so that I can consult with a doctor._
**Acceptance Criteria:**
1. User can select a doctor and view available slots.
2. Booking reserves a 1-hour block.
3. User receives a confirmation screen.
**Priority:** High
**Story Points:** 8
**Notes:**
- Prevent double-booking the same slot.

**Title:** Patient - View Upcoming Appointments
_As a patient, I want to view my upcoming appointments, so that I can prepare accordingly._
**Acceptance Criteria:**
1. Dashboard displays a list of future bookings.
2. List includes doctor name, date, and time.
3. Past appointments are separated from upcoming ones.
**Priority:** Medium
**Story Points:** 3
**Notes:**
- Order chronologically.

---

##  Doctor User Stories

**Title:** Doctor - Log In
_As a doctor, I want to log into the portal, so that I can manage my appointments._
**Acceptance Criteria:**
1. Portal accepts doctor credentials.
2. Authenticated doctors see the provider dashboard.
3. Access is restricted to doctor-level permissions.
**Priority:** High
**Story Points:** 3
**Notes:**
- Use role-based access control (RBAC).

**Title:** Doctor - Log Out
_As a doctor, I want to log out of the portal, so that I can protect my data._
**Acceptance Criteria:**
1. Logout ends the secure session.
2. Browser cannot use the "Back" button to see patient data.
**Priority:** High
**Story Points:** 1
**Notes:**
- Strict security for HIPAA/privacy compliance.

**Title:** Doctor - View Calendar
_As a doctor, I want to view my appointment calendar, so that I can stay organized._
**Acceptance Criteria:**
1. Calendar view displays the current week.
2. Booked slots show patient names.
3. Clicking a slot shows appointment details.
**Priority:** High
**Story Points:** 5
**Notes:**
- Ensure calendar is mobile-friendly.

**Title:** Doctor - Mark Unavailability
_As a doctor, I want to mark my unavailability, so that patients only see available slots._
**Acceptance Criteria:**
1. Doctor can block out specific hours or days.
2. Blocked times disappear from the patient booking view.
3. Doctor can remove blocks if plans change.
**Priority:** Medium
**Story Points:** 5
**Notes:**
- Allow recurring blocks (e.g., lunch breaks).

**Title:** Doctor - Update Profile
_As a doctor, I want to update my profile with specialization and contact information, so that patients have up-to-date information._
**Acceptance Criteria:**
1. Doctor can edit text fields for specialty and bio.
2. Changes save to the database.
3. Public directory immediately reflects changes.
**Priority:** Low
**Story Points:** 2
**Notes:**
- Limit bio character length.

**Title:** Doctor - View Patient Details
_As a doctor, I want to view the patient details for upcoming appointments, so that I can be prepared._
**Acceptance Criteria:**
1. Clicking an appointment reveals patient history.
2. View includes reason for visit and past notes.
3. Data is read-only in this view.
**Priority:** High
**Story Points:** 5
**Notes:**
- Highly sensitive data; ensure secure rendering.
