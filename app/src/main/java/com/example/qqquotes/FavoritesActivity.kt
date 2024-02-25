package com.example.qqquotes

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.io.ByteArrayOutputStream

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

//        Getting from shared preferences
        val sp = getSharedPreferences("Fav", Context.MODE_PRIVATE)
//        Favourite list - local copy - with all quotes
        val fav = sp.getStringSet("fav", null)?.toMutableList()

        val btnShare: ImageView = findViewById(R.id.im_quote_share)
        val btnShareImg: ImageView = findViewById(R.id.im_quote_share_img)
        val btnFav: ImageView = findViewById(R.id.im_quote_like)
        val tvQuote: TextView = findViewById(R.id.tv_quote)
        val cv: ConstraintLayout = findViewById(R.id.cv)
        val btnReStyle: ImageView = findViewById(R.id.im_quote_restyle)
        val btnReColor: ImageView = findViewById(R.id.im_quote_recolor)

        btnReStyle.setOnClickListener { MainActivity.randomStyles(tvQuote) }
        btnReColor.setOnClickListener { cv.setBackgroundColor(MainActivity.generateRandomColor()) }
        btnReStyle.performClick()
        btnReColor.performClick()
        btnShareImg.setOnClickListener {
            // Hide buttons
            btnFav.visibility = View.GONE
            btnShare.visibility = View.GONE
            btnReStyle.visibility = View.GONE
            btnReColor.visibility = View.GONE
            btnShareImg.visibility = View.GONE

            val ll: LinearLayout = findViewById(R.id.ll)
            val layoutParams = ll.layoutParams

            // Change layout parameters to WRAP_CONTENT
            layoutParams?.let {
                it.height = ViewGroup.LayoutParams.WRAP_CONTENT
                ll.layoutParams = it
            }

            // Listen for layout changes
            ll.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    // Ensure we remove the layout listener to prevent it from being called multiple times
                    ll.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    // Capture the bitmap after layout changes
                    val bitmap = Bitmap.createBitmap(ll.width, ll.height, Bitmap.Config.ARGB_8888)
                    val canvas = Canvas(bitmap)
                    ll.draw(canvas)
                    val bytes = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)

                    // Save the bitmap to a file
                    val contentResolver = applicationContext.contentResolver
                    val contentValues = ContentValues().apply {
                        put(MediaStore.Images.Media.DISPLAY_NAME, "TitleFav")
                        put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                    }
                    val uri = contentResolver.insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
                    )
                    uri?.let {
                        contentResolver.openOutputStream(it)?.use { outputStream ->
                            outputStream.write(bytes.toByteArray())
                        }
                    }

                    // Share the bitmap using Intent.ACTION_SEND
                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_STREAM, uri)
                        type = "image/png"
                    }
                    startActivity(Intent.createChooser(shareIntent, "Share Quote as Image"))

                    // Restore original layout params (MATCH_PARENT)
                    layoutParams?.let {
                        it.height = ViewGroup.LayoutParams.MATCH_PARENT
                        ll.layoutParams = it
                    }

                    // Restore buttons visibility
                    btnShare.visibility = View.VISIBLE
                    btnReStyle.visibility = View.VISIBLE
                    btnReColor.visibility = View.VISIBLE
                    btnShareImg.visibility = View.VISIBLE
                    if (!fav.isNullOrEmpty()) btnFav.visibility = View.VISIBLE
                }
            })

            // Request a layout pass to ensure that layout changes take effect
            ll.requestLayout()
        }
        btnShare.setOnClickListener {
            val iShare = Intent(Intent.ACTION_SEND)
            iShare.setType("text/plain")
            iShare.putExtra(Intent.EXTRA_TEXT, tvQuote.text.toString())
            startActivity(Intent.createChooser(iShare, "Share quote via"))
        }
        if (!fav.isNullOrEmpty()) {
            var curr = 0
            var curIndex = fav[curr].toInt()

            val liked = R.drawable.ic_heart_black_24dp
            val unLike = R.drawable.ic_heart_outline_24dp

//            Favourite list in Shared preference to check if it is favourite or not
            var favL: MutableList<String>? = fav

            tvQuote.text = quotes?.get(curIndex)

            cv.setOnTouchListener(object :
                OnSwipeTouchListener(this@FavoritesActivity) {
                override fun onDoubleClick() {
                    btnFav.performClick()
                }
            })
            if (fav.size > 1) cv.setOnTouchListener(object :
            OnSwipeTouchListener(this@FavoritesActivity) {
                override fun onDoubleClick() {
                    btnFav.performClick()
                }
            override fun onSwipeLeft() {
                if (--curr < 0) curr = fav.size - 1
                curIndex = fav[curr].toInt()
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
                if (++curr >= fav.size) curr = 0
                curIndex = fav[curr].toInt()
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
                btnReColor.performClick()
            }
        })

            if (fav.contains(curIndex.toString())) {
                btnFav.setImageResource(liked)
                btnFav.tag = "like"
            }
            btnFav.visibility = View.VISIBLE
            btnFav.setOnClickListener {
                if (btnFav.tag == "like") {
                    btnFav.setImageResource(unLike)
                    btnFav.tag = "unlike"
                    val favQ = sp.getStringSet("fav", null)?.toMutableSet()
                    favQ?.remove(curIndex.toString())
                    val editor = sp.edit()
                    editor.apply {
                        putStringSet("fav", favQ)
                        apply()
                    }
                } else {
                    btnFav.setImageResource(liked)
                    btnFav.tag = "like"
                    val favQ = sp.getStringSet("fav", null)?.toMutableSet()
                    favQ?.add(curIndex.toString())
                    val editor = sp.edit()
                    editor.apply {
                        putStringSet("fav", favQ)
                        apply()
                    }
                }
//                Updating shared preference list copy to check if it is favourite or not.
                favL = sp.getStringSet("fav", null)?.toMutableList()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onBackPressedDispatcher.onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
