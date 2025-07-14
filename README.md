#SCM 2.0
💼 Smart Contact Manager (SCM)<br>
A modern, secure, and user-friendly Spring Boot web application to manage contacts with powerful features like email verification, OAuth login (Google/GitHub), Tailwind CSS UI, dark mode, and more.

🚀 Features
✅ User Registration & Login<br>
✅ Email Verification for Account Activation<br>
✅ Google & GitHub OAuth Login<br>
✅ Tailwind CSS UI + Dark Mode Support<br>
✅ Dynamic Profile Picture via OAuth (Google/GitHub)<br>
✅ Role-Based Access (User/Admin)<br>
✅ Contact Management (CRUD)<br>
✅ Email Notification using JavaMailSender<br>
✅ Excel Export for Contacts<br>
✅ Profile Image Upload Support<br>

✨ What's New?
🔒 Email Verification & Account Activation
On signup, an email with a verification link is sent.

Account becomes active only after verifying the email.

🔐 Google & GitHub Login (OAuth2)
Users can log in securely via:

Google Account (OAuth2)

GitHub Account (OAuth2)

Profile picture from OAuth is auto-fetched and displayed

🌓 Dark Mode + Tailwind UI
Fully responsive design with Tailwind CSS

Supports light and dark modes

User-friendly layout and intuitive design

🛠 Tech Stack
Layer	Technology
Frontend	Tailwind CSS, HTML, Thymeleaf
Backend	Java 17, Spring Boot, Spring Security
Database	MySQL
OAuth Login	Google, GitHub
Mail	JavaMailSender with verification links
Export	Apache POI (Excel export)
Build Tool	Maven

📦 Installation
1. Clone the repository
bash
Copy
Edit
git clone https://github.com/Shivkumarumar519/SCM.git
cd SCM
2. Configure the application
🔐 application.properties (recommended to externalize)
Set the following properties (or use environment variables):

properties
Copy
Edit
spring.datasource.url=jdbc:mysql://localhost:3306/scm
spring.datasource.username=root
spring.datasource.password=yourpassword

# Email config (use env vars ideally)
spring.mail.username=your_email
spring.mail.password=your_email_app_password
🔐 For OAuth2
properties
Copy
Edit
spring.security.oauth2.client.registration.google.client-id=your_google_client_id
spring.security.oauth2.client.registration.google.client-secret=your_google_client_secret

spring.security.oauth2.client.registration.github.client-id=your_github_client_id
spring.security.oauth2.client.registration.github.client-secret=your_github_client_secret
🛡️ Don't hardcode secrets in public repos!

▶️ Run the App
bash
Copy
Edit
./mvnw spring-boot:run
Then open in browser:

arduino
Copy
Edit
http://localhost:8080
📸 Screenshots
Here’s a quick preview of the app:

Add image tags here if needed — you already have screenshots

👨‍💻 Author
Shiv Kumar Umar
Java Fullstack Developer
GitHub | LinkedIn

📄 License
This project is licensed under the MIT License.
