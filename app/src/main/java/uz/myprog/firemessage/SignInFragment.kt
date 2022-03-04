package uz.myprog.firemessage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import uz.myprog.firemessage.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var binding: FragmentSignInBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun getClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun signInWithGoogle() {
        val signInClient = getClient()
        launcher.launch(signInClient.signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.i("MyLog", "firebaseAuthWithGoogle: done")
                checkAuthState()
            } else {
                Log.i("MyLog", "firebaseAuthWithGoogle: error")
            }
        }

    }


    private fun checkAuthState() {
//        if (auth.currentUser != null) {
//            val intent = Intent(requireContext(), Navigation.findNavController(binding.root).navigate(R.id.action_signInFragment_to_mainFragment)::class.java)
//            startActivity(intent)
//        }
      if (auth.currentUser != null)
      findNavController().navigate(R.id.action_signInFragment_to_mainFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        auth = Firebase.auth
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    firebaseAuthWithGoogle(account.idToken!!)

                }
            } catch (e: ApiException) {
                Log.i("MyException", "onCreateView: ApiException")
            }
        }

        binding.btnSignIn.setOnClickListener {
            signInWithGoogle()
            findNavController().navigate(R.id.action_signInFragment_to_mainFragment)
        }
        checkAuthState()


//        binding.btnSignIn.setOnClickListener {
//
//        }

    }

}