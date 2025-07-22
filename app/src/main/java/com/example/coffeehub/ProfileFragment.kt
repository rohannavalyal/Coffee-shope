package com.example.coffeehub

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var profileImageView: ImageView
    private lateinit var profileEmailTextView: TextView
    private lateinit var editTextName: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextInfo: EditText
    private lateinit var buttonSaveChanges: Button
    private lateinit var signOutButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        auth = FirebaseAuth.getInstance()

        profileImageView = view.findViewById(R.id.profileImageView)
        profileEmailTextView = view.findViewById(R.id.profileEmailTextView)
        editTextName = view.findViewById(R.id.editTextName)
        editTextPhone = view.findViewById(R.id.editTextPhone)
        editTextInfo = view.findViewById(R.id.editTextInfo)
        buttonSaveChanges = view.findViewById(R.id.buttonSaveChanges)
        signOutButton = view.findViewById(R.id.signOutButton)

        // Display user information
        val user = auth.currentUser
        user?.let {
            profileEmailTextView.text = it.email

            // Load profile image using Glide if available
            val photoUrl = it.photoUrl
            if (photoUrl != null) {
                Glide.with(this)
                    .load(photoUrl)
                    .placeholder(android.R.drawable.ic_menu_gallery) // Placeholder while loading
                    .error(android.R.drawable.ic_dialog_alert) // Error image if loading fails
                    .circleCrop() // Optional: Make the image circular
                    .into(profileImageView)
            } else {
                // Set a default placeholder if no photo URL is available
                profileImageView.setImageResource(android.R.drawable.ic_menu_gallery)
            }
        }

        // Set up sign out button click listener
        signOutButton.setOnClickListener { signOut() }

        // Set up save changes button click listener (implementation will come later)
        buttonSaveChanges.setOnClickListener { saveProfileChanges() }

        // Load existing profile data (implementation will come later)
        loadProfileData()

        return view
    }

    private fun signOut() {
        auth.signOut()
        Toast.makeText(context, "Signed out successfully", Toast.LENGTH_SHORT).show()
        // Navigate back to AuthActivity and clear the activity stack
        val intent = Intent(activity, AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun saveProfileChanges() {
        // Implementation to save data to Firestore will be added here
        Toast.makeText(context, "Save Changes clicked (Firestore implementation pending)", Toast.LENGTH_SHORT).show()
    }

    private fun loadProfileData() {
        // Implementation to load data from Firestore will be added here
        // For now, just a placeholder
    }
} 