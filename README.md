# ☕ Coffee-Hub

Coffee-Hub is a modern Android application built to provide a seamless experience for discovering and ordering coffee. This guide helps you get started with setting up Firebase services for the app.

---

## 🔧 Firebase Setup Instructions

### 1️⃣ Create a Firebase Project
1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click **"Add project"**
3. Enter a project name (e.g., `Coffee-Hub`)
4. Follow the setup wizard (you can disable Google Analytics if not needed)
5. Click **"Create project"**

---

### 2️⃣ Register Your Android App
1. In the Firebase Console, click the Android icon to add an app
2. Enter your app's **package name** (found in `app/build.gradle.kts` or `AndroidManifest.xml`)
3. Provide an optional app nickname and SHA-1 (optional at this stage)
4. Click **"Register app"**

---

### 3️⃣ Download & Add `google-services.json`
1. Download the generated `google-services.json`
2. Move it to your Android project’s `app/` directory

---

### 4️⃣ Configure Gradle Files

#### 🏗️ Project-level `build.gradle.kts`:
```kotlin
buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
    }
}
```

#### 📦 App-level `build.gradle.kts`:
```kotlin
plugins {
    id("com.google.gms.google-services")
}

dependencies {
    // Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))

    // Firebase services
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-storage")
}
```
---

### 5️⃣ Sync and Build Project
Click "Sync Now" in Android Studio when prompted

Run a build to verify that Firebase is properly integrated

---

### 6️⃣ Enable Authentication (Optional)
Navigate to Authentication in Firebase Console

Click "Get Started"

Enable your preferred sign-in methods (e.g., Email/Password, Google)

---

### 7️⃣ Set Up Firestore (Optional)
Go to Firestore Database in the console

Click "Create Database"

Select test mode for development (update rules later for production)

Choose your region

Click "Enable"

---

### 8️⃣ Set Up Firebase Storage (Optional)
Open the Storage section in Firebase Console

Click "Get Started"

Choose test mode for development

Select your region

Click "Done"

---

📌 Additional Notes
❗ Do not commit google-services.json to public repositories

🔐 Update SHA-1 and SHA-256 fingerprints in Firebase Console before production release

🔒 Always define proper security rules before deploying to production

📱 About Coffee-Hub
Coffee-Hub is designed to enhance the user experience for coffee lovers by combining rich UI, Firebase-powered backend, and real-time features for order tracking, authentication, and storage.

Happy Brewing! ☕🚀
