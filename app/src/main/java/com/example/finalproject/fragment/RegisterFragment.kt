package com.example.finalproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentRegisterBinding
import com.example.finalproject.main.viewmodels.RegisterViewModel


class RegisterFragment : Fragment() {
    private val viewModel : RegisterViewModel by viewModels()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignup.setOnClickListener {
            val email = binding.etRegEmail.text.toString()
            val password = binding.etRegPassword.text.toString()
            val username = binding.etRegUsername.text.toString()
            val repeatedPass = binding.etRepeatPass.text.toString()
            if (activity?.let { it1 -> viewModel.Register(email,password,username,repeatedPass, it1) } == true) {
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, LoginFragment())
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}