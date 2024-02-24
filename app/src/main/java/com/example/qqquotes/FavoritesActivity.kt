package com.example.qqquotes

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class FavoritesActivity : AppCompatActivity() {

    private lateinit var toolBar: androidx.appcompat.widget.Toolbar

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        toolBar = findViewById(R.id.toolBar)
        setSupportActionBar(toolBar)
        supportActionBar?.title = "Favorite Quotes"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val quotes: java.util.ArrayList<String>? = intent.getStringArrayListExtra("Quotes")
        val pref: SharedPreferences = getSharedPreferences("favQ", MODE_PRIVATE)
        val favQuotes = pref.getStringSet("favI", null)?.toMutableList()
        val btnShare: ImageView = findViewById(R.id.im_quote_share)
        val tvQuote: TextView = findViewById(R.id.tv_quote)
        val cv: ConstraintLayout = findViewById(R.id.cv)
        val btnReStyle: ImageView = findViewById(R.id.im_quote_restyle)

        btnReStyle.setOnClickListener {
            cv.setBackgroundColor(MainActivity.generateRandomColor())
            MainActivity.randomStyles(tvQuote)
        }
        btnReStyle.performClick()

        btnShare.setOnClickListener {
            val iShare = Intent(Intent.ACTION_SEND)
            iShare.setType("text/plain")
            iShare.putExtra(Intent.EXTRA_TEXT, tvQuote.text.toString())
            startActivity(Intent.createChooser(iShare, "Share quote via"))
        }

        if (!favQuotes.isNullOrEmpty()) {
            val btnFav: ImageView = findViewById(R.id.im_quote_like)

            var curr = 0
            var curIndex = favQuotes[curr].toInt()

            val liked = R.drawable.ic_heart_black_24dp
            val unLike = R.drawable.ic_heart_outline_24dp
            var favL: MutableSet<String>? = null

            tvQuote.text = quotes?.get(curIndex)


            if (favQuotes.size > 1) cv.setOnTouchListener(object : OnSwipeTouchListener(this@FavoritesActivity) {
                override fun onSwipeLeft() {
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

                override fun onSwipeRight() {
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

                override fun onSwipe() {
                    btnReStyle.performClick()
                }
            })

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onBackPressedDispatcher.onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
