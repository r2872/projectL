package com.autopromaker.projectl

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.autopromaker.data.User
import com.autopromaker.projectl.databinding.ActivitySignUpBinding
import com.autopromaker.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit

class SignUpActivity : BaseActivity() {

    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    private lateinit var verificationId: String

    private val callbacks by lazy {
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {}

            override fun onVerificationFailed(p0: FirebaseException) {
                showSnackBar("인증실패")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, token)
                this@SignUpActivity.verificationId = verificationId
                showSnackBar("인증코드 전송완료, 60초 안에 입력 해 주세요")
                binding.certifyCodeEdt.setText("123412")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setValues()
        setUpEvents()

        mViewModel.currentCertify.observe(this) {
            checkSignUpReady()
        }
    }

    override fun setValues() {

        db = FirebaseFirestore.getInstance()
        mAuth.setLanguageCode("kr")
        mViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun setUpEvents() = with(binding) {

        emailCheckTxt.setOnClickListener {
            hideKeyboard()
            checkDuplication()
        }
        OkBtn.setOnClickListener {
            signUp()
        }
        signUpTxt.setOnClickListener {
            startActivity(Intent(mContext, SignInSelectActivity::class.java))
            finish()
        }
        requestCertifyBtn.setOnClickListener {
            val inputPhoneNum =
                "${binding.phoneNumFirst.text}${binding.phoneNumSecond.text}${binding.phoneNumThird.text}"
            mViewModel.setPhoneNum(inputPhoneNum)
            val phoneNum =
                "+82${inputPhoneNum.replaceFirst("0", "")}"
            Log.d("phoneNum", phoneNum)
            phoneAuth(phoneNum)
            binding.certificationLayout.visibility = View.VISIBLE
        }
        certifyBtn.setOnClickListener {
            val phoneCredential =
                PhoneAuthProvider.getCredential(verificationId, certifyCodeEdt.text.toString())
            verifyPhoneNumWithCode(phoneCredential)
        }

    }

    private fun checkDuplication() {

        val inputEmail = binding.emailEdt.text.toString()
        if (inputEmail.isEmpty()) {
            showSnackBar("이메일을 입력 해 주세요")
            return
        }
        if (!checkIsEmail(inputEmail)) {
            showSnackBar("이메일 형식으로 입력 해 주세요")
            return
        }
//        val collection = db.collection("User").document(inputEmail).get()
//        collection.addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val document = task.result
//                val email = document?.data?.get("email").toString()
//                Log.d("FireStore", email)
//                if (email == inputEmail) {
//                    showSnackBar("존재하는 이메일 입니다.")
//                    mViewModel.setOverlap(false)
//                } else {
//                    showSnackBar("사용 가능한 이메일입니다.")
//                    mViewModel.setOverlap(true)
//                    checkSignUpReady()
//                }
//            }
//        }
//            .addOnFailureListener {
//                Log.d("FireStore", it.toString())
//            }
        val collection = db.collection("User").get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    return@addOnSuccessListener
                }
                for (document in result) {
                    val userData = User(document["phone"] as String, document["email"] as String)
                    if (userData.email == inputEmail) {
                        showSnackBar("존재하는 이메일 입니다.")
                    } else {
                        showSnackBar("사용가능한 이메일 입니다.")
                    }
                }
            }
            .addOnFailureListener {

            }
    }

    private fun signUp() {
        val inputEmail = binding.emailEdt.text.toString()
        val inputPw = binding.pwEdt.text.toString()
        val inputRePw = binding.pwReEdt.text.toString()
        val userData = User(mViewModel.getPhoneNum(), inputEmail)
        if (inputPw != inputRePw) return
        mAuth.createUserWithEmailAndPassword(inputEmail, inputPw)

        db.collection("User").add(userData)
            .addOnSuccessListener {
                showSnackBar("가입에 성공하였습니다.")
            }
            .addOnFailureListener {

            }
    }

    private fun phoneAuth(inputPhoneNum: String) {
        val option = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(inputPhoneNum)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(option)
    }

    private fun checkIsEmail(inputText: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(inputText).matches()
    }

    private fun showSnackBar(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
    }

    private fun verifyPhoneNumWithCode(phoneAuthCredential: PhoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showSnackBar("인증 성공")
                    mViewModel.setCertify(true)
                    mAuth.currentUser?.delete()
                    mAuth.signOut()
                } else {
                    showSnackBar("인증 실패")
                    mViewModel.setCertify(false)
                }
            }
    }

    private fun checkSignUpReady() {
        val pw = binding.pwEdt.text.toString()
        val pwRe = binding.pwReEdt.text.toString()
        binding.OkBtn.isEnabled = mViewModel.getCertify() && mViewModel.getOverlap() && pw == pwRe
    }
}