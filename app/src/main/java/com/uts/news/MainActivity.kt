package com.uts.news

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    private val newsData: MutableList<NewsData> = mutableListOf()
    private lateinit var db: FirebaseFirestore
    private lateinit var progressDialog: ProgressDialog
    private lateinit var adapter : NewsAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        db = FirebaseFirestore.getInstance()

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val floadButton: FloatingActionButton = findViewById(R.id.addNews)
        floadButton.setOnClickListener {
            val toAddPage = Intent(this, NewsAdd::class.java)
            startActivity(toAddPage)
        }
        progressDialog = ProgressDialog(this).apply {
            setTitle("Loading....")
        }
        recyclerView.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)

        adapter = NewsAdapter(newsData)
        adapter.setOnItemClickListener(object : NewsAdapter.OnItemClickListener{
            override fun onItemClick(item: NewsData) {
                val intent = Intent(this@MainActivity, NewsDetail::class.java).apply {
                    putExtra("id", item.id)
                    putExtra("title", item.title)
                    putExtra("desc", item.desc)
                    putExtra("imageUrl", item.urlToImage)
                }
                startActivity(intent)
            }
        })
        recyclerView.adapter = adapter


    }

    override fun onStart() {
        super.onStart()
        getData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
        progressDialog.show()
        db.collection ("news").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                newsData.clear()
                for (document in task.result) {
                    val item = NewsData(
                        document.id,
                        document.getString("title") ?: "",
                        document.getString("image") ?: "",
                        document.getString("desc") ?: ""
                    )
                    newsData.add(item)
                    Log.d("data", "${document.id} => ${document.data}")
                }
                adapter.notifyDataSetChanged ()
            } else {
                Log.w("data", "Error getting documents.", task.exception)
            }
            progressDialog.dismiss ()
        }
    }


}