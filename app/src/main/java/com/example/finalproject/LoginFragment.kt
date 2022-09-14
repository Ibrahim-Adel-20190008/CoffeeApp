package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.activities.CoffeeActivity
import com.example.finalproject.activities.ProductListActivity
import com.example.finalproject.activities.RegisterActivity
import com.example.finalproject.api.service
import com.example.finalproject.dataClasses.LoginResponse
import com.example.finalproject.dataClasses.User
import com.example.finalproject.databinding.FragmentLoginBinding
import com.example.finalproject.databinding.FragmentRegisterBinding
import com.example.finalproject.localDataBase.SharedPre
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //TODO: is it safe to use activity.apllicationcontext or there is a better approach
        SharedPre.sharedPre = activity?.applicationContext?.getSharedPreferences("myPref",
            AppCompatActivity.MODE_PRIVATE
        )
        binding.btnLogin.setOnClickListener {

            // start user profile activity

            if (binding.etEmail.text.toString() != "" && binding.etPassword.text.toString() != "") {
                val newUser = User(null, binding.etPassword.text.toString(), binding.etEmail.text.toString())
                service.login(newUser)?.enqueue(object : Callback<LoginResponse> {

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful) {
                            if (response.code() == 200) {
                                SharedPre.setText(response.body()?.token.toString())
                                SharedPre.setEmail(response.body()?.email)
                                Log.v(
                                    "Logged successfully",
                                    "onResponse ${response.body().toString()}"
                                )
                                checkToken()
                            }

                        } else if (response.code() == 401) {
                            Toast.makeText(
                                activity,
                                "Wrong Email or Password",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Log.v("not 401 or 200", "onResponse ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.d("###", "Nothing")
                    }
                })
            } else {
                Toast.makeText(activity, "Please Fill All Required Fields", Toast.LENGTH_SHORT).show()
            }
        }
        binding.register.setOnClickListener {
            //startActivity(Intent(this, RegisterActivity::class.java))
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container,RegisterFragment())
                addToBackStack(null)
                commit()
            }
        }
        checkToken()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun checkToken() {
        if (!TextUtils.isEmpty(SharedPre.getText())) {
            //TODO: check if it Needs enhancement
           //val intent = Intent(activity, ProductListActivity::class.java)
           val intent = Intent(activity, CoffeeActivity::class.java)
           intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
//            parentFragmentManager.beginTransaction().apply {
//                replace(R.id.fragment_container,ProductListFragment())
//                addToBackStack(null)
//                commit()
//            }
        }
    }
}