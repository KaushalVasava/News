package com.kmv.news

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.getbase.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NewsItemListener {
    lateinit var recyclerview : RecyclerView
    lateinit var madapter: NewsAdapter
    lateinit var fabaction1 : FloatingActionButton
    lateinit var fabaction2 : FloatingActionButton
    lateinit var fabaction3 : FloatingActionButton
    lateinit var fabaction4 : FloatingActionButton

    var cat : String = "health"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerview = findViewById(R.id.recyclerview)
        fabaction1 = findViewById(R.id.fab_action1)
        fabaction2 = findViewById(R.id.fab_action2)
        fabaction3 = findViewById(R.id.fab_action3)
        fabaction4 = findViewById(R.id.fab_action4)

        recyclerview.layoutManager = LinearLayoutManager(this)
        fetchdata()
        madapter = NewsAdapter(this)
        recyclerview.adapter = madapter

        fabaction1.setOnClickListener(){
            Toast.makeText(this,"sports selected",Toast.LENGTH_SHORT).show()
            cat = "sports"
            fetchdata()
        }
        fabaction2.setOnClickListener(){
            Toast.makeText(this,"health selected",Toast.LENGTH_SHORT).show()
            cat = "health"
            fetchdata()
        }
        fabaction3.setOnClickListener(){
            Toast.makeText(this,"health selected",Toast.LENGTH_SHORT).show()
            cat = "entertainment"
            fetchdata()
        }

        fabaction4.setOnClickListener(){
            Toast.makeText(this,"health selected",Toast.LENGTH_SHORT).show()
            cat = "technology"
            fetchdata()
        }
    }
    private fun fetchdata(){
        //val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=e7b862bc8d7c46fb809cec353fed07d9"
        val url = "https://saurav.tech/NewsAPI/top-headlines/category/"+cat+"/in.json"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener{
               val newsJsonArray = it.getJSONArray("articles")
               val newsArray = ArrayList<News>()
               for(i in 0 until newsJsonArray.length()){
                   val newsObject = newsJsonArray.getJSONObject(i)
                   val news = News(
                       newsObject.getString("author"),
                       newsObject.getString("title"),
                       newsObject.getString("url"),
                       newsObject.getString("urlToImage")
                       )
                   newsArray.add(news)
               }
                madapter.updateNews(newsArray)
            },
            {
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()
            })
        MySingletone.getInstance(this).addToRequestQueue(jsonObjectRequest)
     }

    override fun onItemClicked(item: News) {
        val colorInt: Int = Color.parseColor("#1008FA") //blue

    //    Toast.makeText(this,"$item clicked",Toast.LENGTH_SHORT).show()
        val builder = CustomTabsIntent.Builder();
        builder.setToolbarColor(colorInt)
        val customTabsIntent= builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));
    }
}