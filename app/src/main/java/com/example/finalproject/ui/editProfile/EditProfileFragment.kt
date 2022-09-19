package com.example.finalproject.ui.editProfile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentEditProfileBinding
import com.example.finalproject.dataBase.localDB.SharedPre
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditProfileViewModel by viewModels()

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
        binding.include.toolbarText.text = editProfileText

        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)?.visibility = View.GONE
        // click back
        binding.include.arrowBack.visibility = View.VISIBLE
        binding.root.findViewById<ImageView>(R.id.arrow_back).setOnClickListener {
            activity?.onBackPressed()
        }
        activity?.let { viewModel.getUserData(it,binding) }

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
                }
                activity?.let { it1 -> viewModel.confirmChange(username,password, it1,this) }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}