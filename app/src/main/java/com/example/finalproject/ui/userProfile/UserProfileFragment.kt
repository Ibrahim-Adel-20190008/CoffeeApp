package com.example.finalproject.ui.userProfile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentUserProfileBinding
import com.example.finalproject.dataBase.localDB.SharedPre
import com.example.finalproject.mainActivitys.UserActivity
import com.example.finalproject.ui.editProfile.EditProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : Fragment() {

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

        val profileText = "My Profile"
        binding.include.toolbarText.text = profileText
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)?.visibility = View.VISIBLE

        activity?.let { viewModel.getUserInfoApi(it,binding) }
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}