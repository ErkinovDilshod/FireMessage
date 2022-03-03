package uz.myprog.firemessage

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import uz.myprog.firemessage.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun onChangeListener(dRef:DatabaseReference){
        dRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.apply {
                    rcView.ap
                    rcView.append("Erkinov:${snapshot.value.toString()}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        setUpActionBar()
        val database = Firebase.database("https://fbmessage-a6068-default-rtdb.firebaseio.com/")
        val myRef = database.getReference("message")

        binding.btnSend.setOnClickListener{
            myRef.setValue(binding.editText.text.toString())
            binding.editText.text.clear()
        }

        onChangeListener(myRef)
    }

    init {

    }

    private fun setUpActionBar(){
        val ab = (activity as AppCompatActivity?)!!.supportActionBar
       /* Thread{
            val bMap = Picasso.get().load(auth.currentUser?.photoUrl).get()
            val dIcon = BitmapDrawable(resources, bMap)
            ab?.setDisplayHomeAsUpEnabled(true)
            ab?.setHomeAsUpIndicator(dIcon)

        }.start()*/
       var a= Runnable {
           run {
                val bMap = Picasso.get().load(auth.currentUser?.photoUrl).get()
                val dIcon = BitmapDrawable(resources, bMap)
                ab?.setDisplayHomeAsUpEnabled(true)
                ab?.setHomeAsUpIndicator(dIcon)
            }
        }
//      a.run()
    }


}