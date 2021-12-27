package com.example.minprojfrag

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import android.content.pm.PackageManager as PackageManager1



class SetEvents : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val b: Button = view.findViewById(R.id.button)
        val t1 = view.findViewById<TextInputLayout>(R.id.textview1).editText?.text
        val t2 = view.findViewById<TextInputLayout>(R.id.textview2).editText?.text
        val t3 = view.findViewById<TextInputLayout>(R.id.textview3).editText?.text

        val db = Firebase.firestore
        val uid = Firebase.auth.currentUser?.uid
        b.setOnClickListener {
            val intent = Intent(Intent.ACTION_INSERT)
            intent.setData(CalendarContract.Events.CONTENT_URI)
            intent.putExtra(CalendarContract.Events.TITLE,t1.toString())
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, t2.toString())
            intent.putExtra(CalendarContract.Events.DESCRIPTION, t3.toString())
            intent.putExtra(CalendarContract.Events.ALL_DAY,true)
            if ("$t1" != "") {
                val n = hashMapOf(
                    "t1" to "$t1",
                    "t2" to "$t2",
                    "t3" to "$t3"
                )


                val d = db.collection("$uid").document("Events")

                lifecycleScope.launch {

                    d.set(n)
                        .addOnSuccessListener { documentReference ->
                            Log.d("TAG name", "DocumentSnapshot added successfully")

                            if(activity?.let { it1 -> intent.resolveActivity(it1.packageManager) } != null){

                                startActivity(intent)
                            }



                        }
                }

            }




        }
    }


}