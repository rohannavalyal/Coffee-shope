# Coffee-Hub

## Firebase Setup Instructions

### 1. Create a Firebase Project
1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click on "Add project"
3. Enter your project name (e.g., "Coffee-Hub")
4. Follow the setup wizard (you can disable Google Analytics if not needed)
5. Click "Create project"

### 2. Add Android App to Firebase
1. In the Firebase Console, click on the Android icon (</>) to add an Android app
2. Enter your app's package name (found in your `app/build.gradle.kts` or `AndroidManifest.xml`)
3. Enter app nickname (optional)
4. Enter SHA-1 (optional for now)
5. Click "Register app"

### 3. Download and Add Configuration File
1. Download the `google-services.json` file
2. Place it in your project's `app` directory

### 4. Add Firebase Dependencies
Add the following dependencies to your project-level `build.gradle.kts`:
```kotlin
buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
    }
}
```

Add these to your app-level `build.gradle.kts`:
```kotlin
plugins {
    id("com.google.gms.google-services")
}

dependencies {
    // Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
    
    // Firebase features
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-storage")
}
```

### 5. Sync and Build
1. Click "Sync Now" in Android Studio
2. Build your project to ensure everything is working

### 6. Enable Authentication Methods (Optional)
1. In Firebase Console, go to Authentication
2. Click "Get Started"
3. Enable the authentication methods you need (Email/Password, Google, etc.)

### 7. Set Up Firestore Database (Optional)
1. In Firebase Console, go to Firestore Database
2. Click "Create Database"
3. Choose your security rules (Start in test mode for development)
4. Choose a location for your database
5. Click "Enable"

### 8. Set Up Storage (Optional)
1. In Firebase Console, go to Storage
2. Click "Get Started"
3. Choose your security rules (Start in test mode for development)
4. Choose a location for your storage
5. Click "Done"

## Additional Notes
- Make sure to keep your `google-services.json` file secure and never commit it to public repositories
- Update the SHA-1 and SHA-256 fingerprints in Firebase Console when you're ready to release your app
- Consider setting up proper security rules for Firestore and Storage before going to production
# Coffee-shope
