package com.example.finalproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.finalproject.api.service
import com.example.finalproject.dataClasses.User
import com.example.finalproject.databinding.FragmentRegisterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignup.setOnClickListener {
            Log.v("2", "inside button register")
            val email = binding.etRegEmail.text.toString()
            val password = binding.etRegPassword.text.toString()
            val username = binding.etRegUsername.text.toString()
            val repeatedPass = binding.etRepeatPass.text.toString()
            if (email.isEmpty() || password.isEmpty() || username.isEmpty() || repeatedPass.isEmpty()) {
                Toast.makeText(activity, "please fill all required fields", Toast.LENGTH_SHORT).show()
            } else if (password != repeatedPass) {
                Toast.makeText(
                    activity,
                    "password field is not equal to repeated password field",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val user = User(username, password, email)
                lifecycleScope.launch(Dispatchers.IO){
                    val response = service.register(user)
                    if(response.code()==200){
                        launch(Dispatchers.Main){
                            Toast.makeText(
                                activity,
                                "User Registered Successfully",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        parentFragmentManager.beginTransaction().apply {
                            replace(R.id.fragment_container,LoginFragment())
                            addToBackStack(null)
                            commit()
                        }
                    } else if (response.code()==400){
                        launch(Dispatchers.Main){
                            Toast.makeText(
                                activity,
                                "Email is already in use",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else{
                        Log.v("4", "onResponse ${response.code()}")
                    }
                }
               /* service.register(user)
                    .enqueue(object : Callback<Unit> {
                        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                            if (response.isSuccessful) {
                                if (response.code() == 200) {
                                    Toast.makeText(
                                        activity,
                                        "User Registered Successfully",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    parentFragmentManager.beginTransaction().apply {
                                        replace(R.id.fragment_container,LoginFragment())
                                        addToBackStack(null)
                                        commit()
                                    }
                                }
                                Log.v("3", "onResponse ${response.body().toString()}")

                            } else if (response.code() == 400) {
                                Toast.makeText(
                                    activity,
                                    "Email is already in use",
                                    Toast.LENGTH_LONG
                                ).show()

                            } else {
                                Log.v("4", "onResponse ${response.code()}")
                            }
                        }

                        override fun onFailure(call: Call<Unit>, t: Throwable) {
                            Log.v("5", "onFailure ${t.localizedMessage} ")
                        }
                    })*/

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}