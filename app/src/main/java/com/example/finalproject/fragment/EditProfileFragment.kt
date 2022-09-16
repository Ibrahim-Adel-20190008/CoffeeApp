package com.example.finalproject.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.finalproject.R
import com.example.finalproject.activities.CoffeeActivity
import com.example.finalproject.api.service
import com.example.finalproject.dataClasses.User
import com.example.finalproject.databinding.FragmentEditProfileBinding
import com.example.finalproject.localDataBase.SharedPre
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val editProfileText = "Edit My Profile"
        binding.root.findViewById<TextView>(R.id.toolbar_text).text = editProfileText

        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)?.setVisibility(View.GONE)
        // click back
        binding.include.arrowBack.setVisibility(View.VISIBLE)
        binding.root.findViewById<ImageView>(R.id.arrow_back).setOnClickListener {
            activity?.onBackPressed()
        }

        binding.btnConfirm.setOnClickListener {
            var password = binding.etNewPassword.text.toString()
            val username = binding.etEditUsername.text.toString()
            val repeatedPass = binding.etRepeatNewPass.text.toString()
            val oldPassword = binding.etOldPassword.text.toString()
            val currentPassword = SharedPre.getPassword()
            if(currentPassword != oldPassword){
                Toast.makeText(activity, "Wrong old password", Toast.LENGTH_SHORT).show()
            }
            else if (username.isEmpty()) {
                Toast.makeText(activity, "please fill the username field", Toast.LENGTH_SHORT).show()
            }
            else if (password != repeatedPass) {
                Toast.makeText(
                    activity,
                    "password field is not equal to repeated password field",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if(repeatedPass.isEmpty() && password.isEmpty()){
                    password = currentPassword
                }else{
                    SharedPre.setPassword(password)
                }
                lifecycleScope.launch(Dispatchers.IO){

                    val response = service.editProfileInfo("Bearer ${SharedPre.getText()}",
                        SharedPre.getEmail(),
                        username,
                        password)

                    if (response.isSuccessful){
                        launch(Dispatchers.Main){
                            Toast.makeText(
                                activity,
                                "profile is edited successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            (activity as CoffeeActivity?)?.let {
                                it.setCurrentUser(User(username, password,SharedPre.getEmail()))
                            }
                        }
                        parentFragmentManager.beginTransaction().apply {
                            replace(R.id.fragment_container2, UserProfileFragment())
                            addToBackStack(null)
                            commit()
                        }
                    }else{
                        Log.v("401 ", "onResponse ${response.body()}")
                    }
                }
               /* service.editProfileInfo(
                    "Bearer ${SharedPre.getText()}",
                    SharedPre.getEmail(),
                    username,
                    password
                ).enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                activity,
                                "profile is edited successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            parentFragmentManager.beginTransaction().apply {
                                replace(R.id.fragment_container2,UserProfileFragment())
                                addToBackStack(null)
                                commit()
                            }
                        } else {
                            Log.v("401 ", "onResponse ${response.body()}")
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        Log.d("###", "Nothing")
                    }
                })*/
            }

        }
        getUserData()

    }
    private fun getUserData() {

        // to get current user from coffee activity
        (activity as CoffeeActivity?)?.let{
            displayData(it.getCurrentUser())
        }

    // move this part into coffee activity to call api only once
//        lifecycleScope.launch(Dispatchers.IO){
//            val response = service.getUser("Bearer ${SharedPre.getText()}", SharedPre.getEmail())
//            if(response.isSuccessful){
//                val email = response.body()?.email
//                val fullName = response.body()?.username
//                val password = response.body()?.password
//                withContext(Dispatchers.Main){
//                    displayData(User(fullName, password, email))
//                }
//            }else{
//                Log.v("401 ", "onResponse ${response.body()}")
//            }
//        }
        /*service.getUser("Bearer ${SharedPre.getText()}", SharedPre.getEmail())
            .enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val email = response.body()?.email
                        val fullName = response.body()?.username
                        val password = response.body()?.password
                        displayData(User(fullName, password, email))
                    } else {
                        Log.v("401 ", "onResponse ${response.body()}")
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("###", "Nothing")
                }
            })*/
    }

    private fun displayData(currentUser: User) {
        binding.etEditUsername.setText(currentUser.username)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}