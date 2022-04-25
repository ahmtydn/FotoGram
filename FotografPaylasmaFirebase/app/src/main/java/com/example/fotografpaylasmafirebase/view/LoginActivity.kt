package com.example.fotografpaylasmafirebase.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.fotografpaylasmafirebase.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth= FirebaseAuth.getInstance()
        val guncelKullanici=auth.currentUser
        if(guncelKullanici!=null){
            val intent=Intent(this, AnaMenuActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun girisYap(view:View){

        val email=emailText.text.toString()
        val sifre=PasswordText.text.toString()

        auth.signInWithEmailAndPassword(email,sifre).addOnCompleteListener { task->
            if (task.isSuccessful){
                //diğer aktiviteye git
                val guncelKullanici=auth.currentUser?.email.toString()
                Toast.makeText(this,"Hoşgeldiniz by ${guncelKullanici}",Toast.LENGTH_LONG).show()
                val intent= Intent(this, AnaMenuActivity::class.java)
                startActivity(intent)
                finish()

            }

        }.addOnFailureListener { exception->
            Toast.makeText(this,"Kullanıcı email veya şifre yanlış!",Toast.LENGTH_LONG).show()
        }

    }

    fun kayitOl(view: View){

        val email=emailText.text.toString()
        val sifre=PasswordText.text.toString()

        auth.createUserWithEmailAndPassword(email,sifre).addOnCompleteListener { task->
            //asenkron çalışır

            if(task.isSuccessful){

                val intent= Intent(this, LoginActivity::class.java)
                startActivity(intent)
                Toast.makeText(applicationContext,"Kayıt başarılı",Toast.LENGTH_LONG).show()
                finish()

            }
        }.addOnFailureListener { exception->

            Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }

    }
}