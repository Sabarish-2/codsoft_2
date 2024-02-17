package com.example.qqquotes

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FavoritesActivity : AppCompatActivity() {

    private lateinit var toolBar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        toolBar = findViewById(R.id.toolBar)
        setSupportActionBar(toolBar)
        supportActionBar?.title = "Favorite Quotes"


        val quotes: java.util.ArrayList<String>? = intent.getStringArrayListExtra("Quotes")
        val pref: SharedPreferences = getSharedPreferences("favQ", MODE_PRIVATE)
        val favQuotes = pref.getStringSet("favI", null)?.toMutableList()

        if (!favQuotes.isNullOrEmpty()) {
            val ivLeft: ImageView = findViewById(R.id.im_quote_left)
            val ivRight: ImageView = findViewById(R.id.im_quote_right)
            val btnFav: ImageView = findViewById(R.id.im_quote_like)
            val btnShare: ImageView = findViewById(R.id.im_quote_share)

            var curr = 0
            var curIndex = favQuotes[curr].toInt()

            val liked = R.drawable.ic_heart_black_24dp
            val unLike = R.drawable.ic_heart_outline_24dp
            var favL: MutableSet<String>? = null

            val tvQuote: TextView = findViewById(R.id.tv_quote)
            tvQuote.text = quotes?.get(curIndex)

            if (favQuotes.size > 1) {
                ivLeft.visibility = View.VISIBLE
                ivRight.visibility = View.VISIBLE
            }

            ivRight.setOnClickListener {
                if (++curr >= favQuotes.size) curr = 0
                curIndex = favQuotes[curr].toInt()
                tvQuote.text = quotes?.get(curIndex)
                if (favL != null && !favL!!.contains(curIndex.toString())) {
                    btnFav.setImageResource(unLike)
                    btnFav.tag = "unlike"
                } else {
                    btnFav.setImageResource(liked)
                    btnFav.tag = "like"
                }
            }
            ivLeft.setOnClickListener {
                if (--curr < 0) curr = favQuotes.size - 1
                curIndex = favQuotes[curr].toInt()
                if (favL != null && !favL!!.contains(curIndex.toString())) {
                    btnFav.setImageResource(unLike)
                    btnFav.tag = "unlike"
                } else {
                    btnFav.setImageResource(liked)
                    btnFav.tag = "like"
                }
                tvQuote.text = quotes?.get(curIndex)
            }

            btnShare.setOnClickListener {
                val iShare = Intent(Intent.ACTION_SEND)
                iShare.setType("text/plain")
                iShare.putExtra(Intent.EXTRA_TEXT, tvQuote.text.toString())
                startActivity(Intent.createChooser(iShare, "Share quote via"))
            }

            if (favQuotes.contains(curIndex.toString())) {
                btnFav.setImageResource(liked)
                btnFav.tag = "like"
            }
            btnFav.visibility = View.VISIBLE
            btnFav.setOnClickListener {
                if (btnFav.tag == "like") {
                    btnFav.setImageResource(unLike)
                    btnFav.tag = "unlike"
                    favL = pref.getStringSet("favI", null)
                    val setL = favL?.toMutableSet()
                    setL?.remove(curIndex.toString())
                    val editor = pref.edit()
                    editor.putStringSet("favI", setL)
                    editor.apply()
                } else {
                    btnFav.setImageResource(liked)
                    btnFav.tag = "like"
                    favL = pref.getStringSet("favI", null)
                    if (favL != null) {
                        favL!!.add(curIndex.toString())
                    } else {
                        favL = mutableSetOf(curIndex.toString())
                    }
                    val editor = pref.edit()
                    editor.putStringSet("favI", favL)
                    editor.apply()
                }
                favL = pref.getStringSet("favI", null)
            }
        }
    }
}
