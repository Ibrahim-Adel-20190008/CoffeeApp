package com.example.finalproject.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.activities.CoffeeActivity
import com.example.finalproject.activities.UserActivity
import com.example.finalproject.api.service
import com.example.finalproject.dataClasses.User
import com.example.finalproject.databinding.FragmentUserProfileBinding
import com.example.finalproject.localDataBase.SharedPre
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserProfileFragment : Fragment() {
    lateinit var userEmail: TextView
    lateinit var userName: TextView
    lateinit var hiMsg: TextView

    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userEmail =binding.userEmail
        userName = binding.username
        hiMsg = binding.hi
        val profileText = "My Profile"
        binding.include.toolbarText.text = profileText
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)?.setVisibility(View.VISIBLE)

        //TODO  handle api image
        val urlToImg = SharedPre.getUrl()
            Glide.with(this).load(urlToImg).into(binding.imageView)

        binding.btnEditProfile.setOnClickListener {
            //startActivity(Intent(activity, EditProfileActivity::class.java))
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container2, EditProfileFragment())
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
        /*binding.userEmail.text = currentUser.email
        binding.username.text = currentUser.username
        binding.hi.text = "Hi ${currentUser.username}"*/

        userEmail.text = currentUser.email
        userName.text = currentUser.username
        hiMsg.text = "Hi ${currentUser.username}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}