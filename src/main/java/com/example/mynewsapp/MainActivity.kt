package com.example.mynewsapp

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity(), NewsItemClick {
    private  lateinit var mAdapter : NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
//        var progress = findViewById<ProgressBar>(R.id.progressBar)
        fetchData()
        recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter=mAdapter

        // all news...
        val all  = findViewById<Button>(R.id.all)
        val ent = findViewById<Button>(R.id.entertainment)
        val business = findViewById<Button>(R.id.business)
        val general = findViewById<Button>(R.id.general)
        val health = findViewById<Button>(R.id.health)
        val sports = findViewById<Button>(R.id.sports)
        val tech = findViewById<Button>(R.id.tech)
        val science = findViewById<Button>(R.id.science)

        all.setOnClickListener {
            all.setBackgroundColor(Color.parseColor("#D14646"))
            ent.setBackgroundColor(Color.parseColor("#000000"))
            business.setBackgroundColor(Color.parseColor("#000000"))
            general.setBackgroundColor(Color.parseColor("#000000"))
            health.setBackgroundColor(Color.parseColor("#000000"))
            sports.setBackgroundColor(Color.parseColor("#000000"))
            tech.setBackgroundColor(Color.parseColor("#000000"))
            science.setBackgroundColor(Color.parseColor("#000000"))

            fetchData()
        }

        // business news...

        business.setOnClickListener {
            all.setBackgroundColor(Color.parseColor("#000000"))
            ent.setBackgroundColor(Color.parseColor("#000000"))
            business.setBackgroundColor(Color.parseColor("#D14646"))
            general.setBackgroundColor(Color.parseColor("#000000"))
            health.setBackgroundColor(Color.parseColor("#000000"))
            sports.setBackgroundColor(Color.parseColor("#000000"))
            tech.setBackgroundColor(Color.parseColor("#000000"))
            science.setBackgroundColor(Color.parseColor("#000000"))

            fetchBussiness()
        }

        //Entertainment news...
//        v ent = findViewById<Button>(R.id.entertainment)
        ent.setOnClickListener {
            ent.setBackgroundColor(Color.parseColor("#D14646"))
            all.setBackgroundColor(Color.parseColor("#000000"))
//            ent.setBackgroundColor(Color.parseColor("#000000"))
            business.setBackgroundColor(Color.parseColor("#000000"))
            general.setBackgroundColor(Color.parseColor("#000000"))
            health.setBackgroundColor(Color.parseColor("#000000"))
            sports.setBackgroundColor(Color.parseColor("#000000"))
            tech.setBackgroundColor(Color.parseColor("#000000"))
            science.setBackgroundColor(Color.parseColor("#000000"))


            fetchEntertainment()
        }


        // general news..

        general.setOnClickListener {
            general.setBackgroundColor(Color.parseColor("#D14646"))
            all.setBackgroundColor(Color.parseColor("#000000"))
            ent.setBackgroundColor(Color.parseColor("#000000"))
            business.setBackgroundColor(Color.parseColor("#000000"))
//            general.setBackgroundColor(Color.parseColor("#000000"))
            health.setBackgroundColor(Color.parseColor("#000000"))
            sports.setBackgroundColor(Color.parseColor("#000000"))
            tech.setBackgroundColor(Color.parseColor("#000000"))
            science.setBackgroundColor(Color.parseColor("#000000"))

            fetchGeneral()
        }

        // Health news..

        health.setOnClickListener {
            health.setBackgroundColor(Color.parseColor("#D14646"))
            all.setBackgroundColor(Color.parseColor("#000000"))
            ent.setBackgroundColor(Color.parseColor("#000000"))
            business.setBackgroundColor(Color.parseColor("#000000"))
            general.setBackgroundColor(Color.parseColor("#000000"))
//            health.setBackgroundColor(Color.parseColor("#000000"))
            sports.setBackgroundColor(Color.parseColor("#000000"))
            tech.setBackgroundColor(Color.parseColor("#000000"))
            science.setBackgroundColor(Color.parseColor("#000000"))

            fetchhealth()
        }

        // Science News...

        science.setOnClickListener {
            science.setBackgroundColor(Color.parseColor("#D14646"))
            all.setBackgroundColor(Color.parseColor("#000000"))
            ent.setBackgroundColor(Color.parseColor("#000000"))
            business.setBackgroundColor(Color.parseColor("#000000"))
            general.setBackgroundColor(Color.parseColor("#000000"))
            health.setBackgroundColor(Color.parseColor("#000000"))
            sports.setBackgroundColor(Color.parseColor("#000000"))
            tech.setBackgroundColor(Color.parseColor("#000000"))
//            science.setBackgroundColor(Color.parseColor("#000000"))

            fetchScience()
        }

        // Sports News...


        sports.setOnClickListener {
            sports.setBackgroundColor(Color.parseColor("#D14646"))
            all.setBackgroundColor(Color.parseColor("#000000"))
            ent.setBackgroundColor(Color.parseColor("#000000"))
            business.setBackgroundColor(Color.parseColor("#000000"))
            general.setBackgroundColor(Color.parseColor("#000000"))
            health.setBackgroundColor(Color.parseColor("#000000"))
//            sports.setBackgroundColor(Color.parseColor("#000000"))
            tech.setBackgroundColor(Color.parseColor("#000000"))
            science.setBackgroundColor(Color.parseColor("#000000"))

            fetchSports()
        }

        // tech.. News..

        tech.setOnClickListener {
            tech.setBackgroundColor(Color.parseColor("#D14646"))
            all.setBackgroundColor(Color.parseColor("#000000"))
            ent.setBackgroundColor(Color.parseColor("#000000"))
            business.setBackgroundColor(Color.parseColor("#000000"))
            general.setBackgroundColor(Color.parseColor("#000000"))
            health.setBackgroundColor(Color.parseColor("#000000"))
            sports.setBackgroundColor(Color.parseColor("#000000"))
//            tech.setBackgroundColor(Color.parseColor("#000000"))
            science.setBackgroundColor(Color.parseColor("#000000"))

            fetchTech()
        }

    }

    private fun fetchData() {
        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=20a32e6ad0464c6c9fcfb27b217cceea"
        var progress = findViewById<ProgressBar>(R.id.progressBar)
        progress.setVisibility(View.VISIBLE)

        val jsonObjectRequest = object:JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener{
                             val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)

                }
                mAdapter.updateNews(newsArray)
                progress.setVisibility(View.INVISIBLE)
            },
            {

            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)




    }


    private fun fetchBussiness(){
        val url = "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=20a32e6ad0464c6c9fcfb27b217cceea"
        var progress = findViewById<ProgressBar>(R.id.progressBar)
        progress.setVisibility(View.VISIBLE)

        val jsonObjectRequest = object:JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener{
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)

                }
                mAdapter.updateNews(newsArray)
                progress.setVisibility(View.INVISIBLE)

            },
            {

            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


    }

    private fun fetchEntertainment(){
        val url = "https://newsapi.org/v2/top-headlines?country=in&category=entertainment&apiKey=20a32e6ad0464c6c9fcfb27b217cceea"
        var progress = findViewById<ProgressBar>(R.id.progressBar)
        progress.setVisibility(View.VISIBLE)

        val jsonObjectRequest = object:JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener{
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)

                }
                mAdapter.updateNews(newsArray)
                progress.setVisibility(View.INVISIBLE)
            },
            {

            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)



    }

    private fun fetchGeneral(){
        val url = "https://newsapi.org/v2/top-headlines?country=in&category=general&apiKey=20a32e6ad0464c6c9fcfb27b217cceea"
        var progress = findViewById<ProgressBar>(R.id.progressBar)
        progress.setVisibility(View.VISIBLE)

        val jsonObjectRequest = object:JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener{
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)

                }
                mAdapter.updateNews(newsArray)
                progress.setVisibility(View.INVISIBLE)
            },
            {

            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)



    }

    private fun fetchhealth(){

        val url = "https://newsapi.org/v2/top-headlines?country=in&category=health&apiKey=20a32e6ad0464c6c9fcfb27b217cceea"
        var progress = findViewById<ProgressBar>(R.id.progressBar)
        progress.setVisibility(View.VISIBLE)

        val jsonObjectRequest = object:JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener{
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)

                }
                mAdapter.updateNews(newsArray)
                progress.setVisibility(View.INVISIBLE)
            },
            {

            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


    }

    private fun fetchScience(){
        val url = "https://newsapi.org/v2/top-headlines?country=in&category=science&apiKey=20a32e6ad0464c6c9fcfb27b217cceea"
        var progress = findViewById<ProgressBar>(R.id.progressBar)
        progress.setVisibility(View.VISIBLE)

        val jsonObjectRequest = object:JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener{
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)

                }
                mAdapter.updateNews(newsArray)
                progress.setVisibility(View.INVISIBLE)
            },
            {

            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    private fun fetchSports(){
        val url = "https://newsapi.org/v2/top-headlines?country=in&category=sports&apiKey=20a32e6ad0464c6c9fcfb27b217cceea"
        var progress = findViewById<ProgressBar>(R.id.progressBar)
        progress.setVisibility(View.VISIBLE)

        val jsonObjectRequest = object:JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener{
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)

                }
                mAdapter.updateNews(newsArray)
                progress.setVisibility(View.INVISIBLE)
            },
            {

            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    private fun fetchTech(){
        val url = "https://newsapi.org/v2/top-headlines?country=in&category=technology&apiKey=20a32e6ad0464c6c9fcfb27b217cceea"
        var progress = findViewById<ProgressBar>(R.id.progressBar)
        progress.setVisibility(View.VISIBLE)

        val jsonObjectRequest = object:JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener{
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)

                }
                mAdapter.updateNews(newsArray)
                progress.setVisibility(View.INVISIBLE)
            },
            {

            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }


    override fun onItemClick(item: News) {
//        Toast.makeText(this,"Clicked item is ",Toast.LENGTH_SHORT).show()
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }





}