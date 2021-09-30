package com.kmv.news.fragments

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle;
import android.util.Log

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.kmv.news.NewsAdapter
import com.kmv.news.NewsItemListener
import com.kmv.news.R
import com.kmv.news.data.News
import com.kmv.news.singletone.MySingletone
import android.view.View as View1

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SportFragment : Fragment(), NewsItemListener {
    lateinit var recyclerview1 : RecyclerView

    lateinit var madapter: NewsAdapter
    private var newsArray = ArrayList<News>()

    public override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View {
        val view : View1 = inflater.inflate(R.layout.fragment_sport, container, false);

        recyclerview1= view.findViewById(R.id.recyclerview1)
        recyclerview1.layoutManager = LinearLayoutManager(view.context)
        fetchdata()
        madapter = NewsAdapter(this,requireContext(),newsArray)
        recyclerview1.adapter = madapter
        // Inflate the layout for this fragment
       return view
    }


    private fun fetchdata() : ArrayList<News>{
        //val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=e7b862bc8d7c46fb809cec353fed07d9"
        val url = "https://saurav.tech/NewsAPI/top-headlines/category/sports/in.json"
        val newsArray = ArrayList<News>()
        Log.d("TAG","inside fetchdata")

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener{
                val newsJsonArray = it.getJSONArray("articles")
                for(i in 0 until newsJsonArray.length()){
                    val newsObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsObject.getString("author"),
                        newsObject.getString("title"),
                        newsObject.getString("url"),
                        newsObject.getString("urlToImage")
                    )

                    val s = newsObject.getString("title")

                    val last = s.length-1
                    var index = 0
                    for (i in last downTo 0) {
                        if (s[i] == '-') {
                            index = i
                            break
                        }
                    }
                    val sub = s.substring(index+1, last+1)
                    news.author=sub
                    newsArray.add(news)
                    Log.d("TAG","every ${newsArray.size}")
                }
                madapter.updateNews(newsArray)
                // madapter.notifyDataSetChanged()

            },
            {
                val toast = Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG)
                toast.show()
            })
         context?.let { MySingletone.getInstance(it).addToRequestQueue(jsonObjectRequest) }
       return newsArray
    }
    override fun onItemClicked(item: News) {
        val colorInt: Int = Color.parseColor("#FFFFFF") //blue

        //    Toast.makeText(this,"$item clicked",Toast.LENGTH_SHORT).show()
        val builder = CustomTabsIntent.Builder();
        builder.setToolbarColor(colorInt)
        val customTabsIntent= builder.build();
        context?.let { customTabsIntent.launchUrl(it, Uri.parse(item.url)) };
    }
    override fun onPause() {
        super.onPause()
        Log.d("T","On PAUSE")

        val preference = requireContext().getSharedPreferences("DATA", Context.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putInt("Page",2)
        editor.apply()
    }
}