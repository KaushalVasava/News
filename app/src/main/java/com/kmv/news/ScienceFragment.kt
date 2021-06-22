package com.kmv.news

import android.graphics.Color
import android.net.Uri
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.kmv.news.*

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ScienceFragment: Fragment(), NewsItemListener {

    lateinit var recyclerview5: RecyclerView

    lateinit var madapter: NewsAdapter

    public override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {
        val view: View = inflater.inflate(R.layout.fragment_science, container, false);

        recyclerview5 = view.findViewById(R.id.recyclerview5)
        recyclerview5.layoutManager = LinearLayoutManager(view.context)
        madapter = NewsAdapter(this)
        recyclerview5.adapter = madapter
        fetchdata()

        // Inflate the layout for this fragment
        return view
    }


    private fun fetchdata() {
        //   val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=e7b862bc8d7c46fb809cec353fed07d9"
        val url = "https://saurav.tech/NewsAPI/top-headlines/category/science/in.json"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsObject.getString("author"),
                        newsObject.getString("title"),
                        newsObject.getString("url"),
                        newsObject.getString("urlToImage")
                    )

                    val s = newsObject.getString("title")

                    val last = s.length - 1
                    var index = 0
                    for (i in last downTo 0) {
                        if (s[i] == '-') {
                            index = i
                            break
                        }
                    }
                    val sub = s.substring(index + 1, last + 1)
                    news.author = sub
                    newsArray.add(news)
                }
                madapter.updateNews(newsArray)
            },
            {
                val toast = Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG)
                toast.show()
            })
        context?.let { MySingletone.getInstance(it).addToRequestQueue(jsonObjectRequest) }
    }

    override fun onItemClicked(item: News) {
        val colorInt: Int = Color.parseColor("#FFFFFF") //blue

        //    Toast.makeText(this,"$item clicked",Toast.LENGTH_SHORT).show()
        val builder = CustomTabsIntent.Builder();
        builder.setToolbarColor(colorInt)
        val customTabsIntent = builder.build();
        context?.let { customTabsIntent.launchUrl(it, Uri.parse(item.url)) };
    }
}