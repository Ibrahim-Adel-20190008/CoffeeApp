package com.example.finalproject.ui.userProfile

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.mainActivitys.CoffeeActivity
import com.example.finalproject.dataBase.remoteDB.models.User
import com.example.finalproject.databinding.FragmentUserProfileBinding
import com.example.finalproject.dataBase.localDB.SharedPre
import com.example.finalproject.domain.Repository
import com.example.finalproject.ui.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel(){

    fun getUserInfoApi(activity: FragmentActivity,binding:FragmentUserProfileBinding) {
        (activity as CoffeeActivity?)?.let {
            if (it.getCurrentUser()?.email == null) {
                viewModelScope.launch(Dispatchers.IO) {
                    when (val response =
                        repo.getUser("Bearer ${SharedPre.getText()}", SharedPre.getEmail())) {
                        is Resources.Success -> {
                            val email = response.data?.email
                            val fullName = response.data?.username
                            it.setCurrentUser(User(fullName, null, email))
                            withContext(Dispatchers.Main){
                                getUserData(activity,binding)
                            }
                        }
                        is Resources.Error -> {
                            Log.v("401 ", "onResponse ${response.msg}")
                        }
                    }
                }
            }
            else{
                getUserData(activity,binding)
            }

        }
    }
    private fun getUserData(activity: FragmentActivity,binding:FragmentUserProfileBinding) {
        (activity as CoffeeActivity?)?.let{
            it.getCurrentUser()?.let { it1 -> displayData(it1,binding) }
        }
    }

    private fun displayData(currentUser: User,binding:FragmentUserProfileBinding) {
        binding.userEmail.text = currentUser.email
        binding.username.text = currentUser.username
        binding.hi.text = "Hi ${currentUser.username}"
    }



}