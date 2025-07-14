# Smart Contact Manager (SCM)

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

## 📸 Screenshots

<img width="1366" height="768" alt="Screenshot (34)" src="https://github.com/user-attachments/assets/33690fc8-9e17-4239-a243-1f444c202637" />
<img width="1366" height="768" alt="Screenshot (35)" src="https://github.com/user-attachments/assets/95f3a597-ed12-4dca-8b20-174b64ebe723" />
<img width="1366" height="768" alt="Screenshot (36)" src="https://github.com/user-attachments/assets/94733771-b9bf-4863-a581-2267ec46ce92" />
<img width="1366" height="768" alt="Screenshot (37)" src="https://github.com/user-attachments/assets/789c9bc3-ecff-4267-9c5b-6644a82bb575" />
<img width="1366" height="768" alt="Screenshot (38)" src="https://github.com/user-attachments/assets/171ab1bf-4543-428b-8c3e-1f6d0cd88a59" />
<img width="1366" height="768" alt="Screenshot (39)" src="https://github.com/user-attachments/assets/763d2a50-5875-40ad-b6fb-1c881efb9487" />
<img width="1366" height="768" alt="Screenshot (40)" src="https://github.com/user-attachments/assets/87009a80-97c0-4120-a925-d232010b21d3" />
<img width="1366" height="768" alt="Screenshot (41)" src="https://github.com/user-attachments/assets/f21f9ace-5ce5-4011-a1d9-34f0f7ebda3c" />
<img width="1366" height="768" alt="Screenshot (42)" src="https://github.com/user-attachments/assets/9df975e7-72b2-43de-921b-96cb37c3409d" />
<img width="1366" height="768" alt="Screenshot (43)" src="https://github.com/user-attachments/assets/827c8a1e-4047-44f1-aef5-d9cd685f2ee4" />
<img width="1366" height="768" alt="Screenshot (44)" src="https://github.com/user-attachments/assets/aabe278a-3cfe-402c-bbf9-5cf9cb1383a7" />
<img width="1366" height="768" alt="Screenshot (45)" src="https://github.com/user-attachments/assets/8cd71f0d-7a6d-43be-83d2-f2babfd49c11" />



👨‍💻 Author<br>
Shiv Kumar Umar<br>
Java Fullstack Developer<br>

