package com.example.fotografpaylasmafirebase.view

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fotografpaylasmafirebase.R
import com.example.fotografpaylasmafirebase.adapter.AnaMenuRecyclerAdapter
import com.example.fotografpaylasmafirebase.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_ana_menu.*

class AnaMenuActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var database : FirebaseFirestore
    var postListesi=ArrayList<Post>()
    private lateinit var recyclerViewAdapter:AnaMenuRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ana_menu)
        auth= FirebaseAuth.getInstance()
        database= FirebaseFirestore.getInstance()
        verileriAl()
        var layoutManager=LinearLayoutManager(this)
        recyclerView.layoutManager=layoutManager
        recyclerViewAdapter= AnaMenuRecyclerAdapter(postListesi)
        recyclerView.adapter=recyclerViewAdapter

    }
    fun verileriAl(){

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Yükleniyor,lütfen bekleyiniz...")
        progressDialog.show()

        database.collection("Post").orderBy("tarih",Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
            if(exception!=null){
                Toast.makeText(this,exception.localizedMessage,Toast.LENGTH_LONG).show()
            }else{
                if (snapshot!=null){
                    if (!snapshot.isEmpty){

                        val documents=snapshot.documents
                        postListesi.clear()
                            for (documents in documents){
                                val kullaniciEmail=documents.get("kullaniciemail") as String
                                val kullaniciYorum=documents.get("kullaniciyorum") as String
                                val gorselUrl=documents.get("gorselurl") as String

                                val indirilenPost= Post(kullaniciEmail,kullaniciYorum,gorselUrl)
                                postListesi.add(indirilenPost)

                            }
                        recyclerViewAdapter.notifyDataSetChanged()

                    }
                }
                progressDialog.dismiss()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater=menuInflater
        menuInflater.inflate(R.menu.secenekler_menusu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId== R.id.fotograf_paylas){
            //fotograf paylaşma aktiviteye gidilecek
            val intent=Intent(this, FotografPaylasmaActivity::class.java)
            startActivity(intent)

        }else if(item.itemId== R.id.cikis){
            auth.signOut()
            val intent=Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        else if(item.itemId==R.id.konumgoruntule)
        {
            val intent=Intent(this,GuncelKonumActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }
}