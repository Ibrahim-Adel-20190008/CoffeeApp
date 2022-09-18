package com.example.finalproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.finalproject.R
import com.example.finalproject.data.models.User
import com.example.finalproject.databinding.FragmentLoginBinding
import com.example.finalproject.main.viewmodels.LoginViewModel


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO: is it safe to use activity.apllicationcontext or there is a better approach
//        SharedPre.sharedPre = activity?.applicationContext?.getSharedPreferences("myPref",
//            AppCompatActivity.MODE_PRIVATE
//        )
        binding.btnLogin.setOnClickListener {
            if (binding.etEmail.text.toString() != "" && binding.etPassword.text.toString() != "") {
                val newUser = User(null, binding.etPassword.text.toString(), binding.etEmail.text.toString())
                activity?.let { it1 -> viewModel.login(newUser, it1,binding.etPassword.text.toString()) }
            } else {
                Toast.makeText(activity, "Please Fill All Required Fields", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.register.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, RegisterFragment())
                addToBackStack(null)
                commit()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}