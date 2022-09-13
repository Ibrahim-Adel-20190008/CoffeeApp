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
import com.bumptech.glide.Glide
import com.example.finalproject.activities.EditProfileActivity
import com.example.finalproject.activities.LoginActivity
import com.example.finalproject.activities.UserActivity
import com.example.finalproject.api.service
import com.example.finalproject.dataClasses.User
import com.example.finalproject.databinding.FragmentRegisterBinding
import com.example.finalproject.databinding.FragmentUserProfileBinding
import com.example.finalproject.localDataBase.SharedPre
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val profileText = "My Profile"
        binding.root.findViewById<TextView>(R.id.toolbar_text).text = profileText

        val urlToImg =
            "https://th.bing.com/th/id/R.fe20a57e77099fe1baea254d50f6802c?rik=FsyFE8G2RLe1EA&riu=http%3a%2f%2fehonami.blob.core.windows.net%2fmedia%2f2014%2f11%2fcoffee-even-decaf-found-benefit-liver-health.jpg&ehk=XtVvJ0uvsrpSLM02RVuUPBj0EQvfbEYoC7lew1%2bWrmk%3d&risl=&pid=ImgRaw&r=0"
        Glide.with(this).load(urlToImg).into(binding.imageView)

        binding.btnEditProfile.setOnClickListener {
            //startActivity(Intent(activity, EditProfileActivity::class.java))
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container2,EditProfileFragment())
                addToBackStack(null)
                commit()
            }
        }



        binding.logout.setOnClickListener {
            SharedPre.setText(null)
            val intent = Intent(activity, UserActivity::class.java)
            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        // click back
        binding.root.findViewById<ImageView>(R.id.arrow_back).setOnClickListener {
            activity?.onBackPressed()
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
        binding.userEmail.text = currentUser.email
        binding.username.text = currentUser.username
        binding.hi.text = "Hi ${currentUser.username}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}