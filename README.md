#Smart Contact Manager (SCM)

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

✨ What's New?<br>
🔒 Email Verification & Account Activation<br>
On signup, an email with a verification link is sent.<br>

Account becomes active only after verifying the email.<br>

🔐 Google & GitHub Login (OAuth2)<br>
Users can log in securely via:

Google Account (OAuth2)

GitHub Account (OAuth2)

Profile picture from OAuth is auto-fetched and displayed

🌓 Dark Mode + Tailwind UI<br>
Fully responsive design with Tailwind CSS

Supports light and dark modes

User-friendly layout and intuitive design

🛠 Tech Stack<br>
Layer	Technology<br>
Frontend-	Tailwind CSS, HTML, Thymeleaf<br>
Backend-	Java 17, Spring Boot, Spring Security<br>
Database-	MySQL<br>
OAuth Login-	Google, GitHub<br>
Mail -	JavaMailSender with verification links<br>
Export -	Apache POI (Excel export)<br>
Build Tool -	Maven<br>

📦 Installation<br>
1. Clone the repository

git clone https://github.com/Shivkumarumar519/SCM.git<br>
cd SCM

2. Configure the application<br>
🔐 application.properties<br>

3. Set the following properties<br>

spring.datasource.url=jdbc:mysql://localhost:3306/scm<br>
spring.datasource.username=root<br>
spring.datasource.password=yourpassword

🔐 For OAuth2

spring.security.oauth2.client.registration.google.client-id=your_google_client_id<br>
spring.security.oauth2.client.registration.google.client-secret=your_google_client_secret

spring.security.oauth2.client.registration.github.client-id=your_github_client_id<br>
spring.security.oauth2.client.registration.github.client-secret=your_github_client_secret


▶️ Run the App

./mvnw spring-boot:run

Then open in browser:

http://localhost:9000


👨‍💻 Author<br>
Shiv Kumar Umar<br>
Java Fullstack Developer<br>

