package com.example.finalproject.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.activities.CoffeeActivity
import com.example.finalproject.activities.UserActivity
import com.example.finalproject.data.api.ServicesApi
import com.example.finalproject.data.models.User
import com.example.finalproject.databinding.FragmentUserProfileBinding
import com.example.finalproject.localDataBase.SharedPre
import com.example.finalproject.main.viewmodels.UserProfileViewModel
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
    private val viewModel: UserProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)?.visibility = View.VISIBLE

        val urlToImg = SharedPre.getUrl()
            Glide.with(this).load(urlToImg).into(binding.imageView)

        binding.btnEditProfile.setOnClickListener {
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
        activity?.let { viewModel.getUserInfoApi(it) }
        getUserData()
    }

    private fun getUserData() {
        (activity as CoffeeActivity?)?.let{
            it.getCurrentUser()?.let { it1 -> displayData(it1) }
        }
    }

    private fun displayData(currentUser: User) {
        userEmail.text = currentUser.email
        userName.text = currentUser.username
        hiMsg.text = "Hi ${currentUser.username}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}