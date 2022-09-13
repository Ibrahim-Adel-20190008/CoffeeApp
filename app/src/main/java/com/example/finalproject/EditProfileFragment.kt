package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.finalproject.activities.UserProfileActivity
import com.example.finalproject.adapters.CartAdapter
import com.example.finalproject.api.service
import com.example.finalproject.dataClasses.User
import com.example.finalproject.databinding.FragmentCartBinding
import com.example.finalproject.databinding.FragmentEditProfileBinding
import com.example.finalproject.localDataBase.SharedPre
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        // click back
        binding.root.findViewById<ImageView>(R.id.arrow_back).setOnClickListener {
            activity?.onBackPressed()
        }

        binding.btnConfirm.setOnClickListener {
            val password = binding.etNewPassword.text.toString()
            val username = binding.etEditUsername.text.toString()
            val repeatedPass = binding.etRepeatNewPass.text.toString()
            if (password.isEmpty() || username.isEmpty() || repeatedPass.isEmpty()) {
                Toast.makeText(activity, "please fill all required fields", Toast.LENGTH_SHORT).show()
            } else if (password != repeatedPass) {
                Toast.makeText(
                    activity,
                    "password field is not equal to repeated password field",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                service.editProfileInfo(
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
                })
            }

        }
        getUserData()

    }
    fun getUserData() {
        service.getUser("Bearer ${SharedPre.getText()}", SharedPre.getEmail())
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
            })
    }

    fun displayData(currentUser: User) {
        binding.etEditUsername.setText(currentUser.username)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}