package com.example.finalproject.ui.editProfile

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.R
import com.example.finalproject.mainActivitys.CoffeeActivity
import com.example.finalproject.dataBase.remoteDB.models.User
import com.example.finalproject.databinding.FragmentEditProfileBinding
import com.example.finalproject.ui.userProfile.UserProfileFragment
import com.example.finalproject.dataBase.localDB.SharedPre
import com.example.finalproject.domain.Repository
import com.example.finalproject.ui.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val repo : Repository
) : ViewModel(){

    fun confirmChange(
        username:String,
        password:String,
        activity:Context,
        fragment: EditProfileFragment
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = repo.editProfileInfo(
                "Bearer ${SharedPre.getText()}",
                SharedPre.getEmail(),
                username,
                password
            )) {
                is Resources.Success -> {
                    launch(Dispatchers.Main) {
                        Toast.makeText(
                            activity,
                            "profile is edited successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        (activity as CoffeeActivity?)?.let {
                            it.setCurrentUser(User(username, password, SharedPre.getEmail()))
                        }
                    }
                    startFragment(fragment)
                    if(SharedPre.getPassword()!=password) {SharedPre.setPassword(password)}
                }
                is Resources.Error -> {
                    Log.v("401 ", "onResponse ${response.data}")
                }
            }
        }
    }
    private fun startFragment(fragment: EditProfileFragment){
        fragment.parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container2, UserProfileFragment())
            addToBackStack(null)
            commit()
        }
    }
    fun getUserData(activity: FragmentActivity,binding: FragmentEditProfileBinding) {
        (activity as CoffeeActivity?)?.let{
            displayData(it.getCurrentUser()?.username,binding)
        }
    }

    private fun displayData(username: String?,binding: FragmentEditProfileBinding) {
        binding.etEditUsername.setText(username)
    }
}