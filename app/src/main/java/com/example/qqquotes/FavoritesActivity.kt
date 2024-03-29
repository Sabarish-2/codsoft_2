package com.example.qqquotes

import android.annotation.SuppressLint
import android.content.ContentValues
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

    private val db: QuoteDB by lazy { QuoteDB.getDB(this) }


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        toolBar = findViewById(R.id.toolBar)
        setSupportActionBar(toolBar)
        supportActionBar?.title = "Favorite Quotes"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val quotes: java.util.ArrayList<String>? = intent.getStringArrayListExtra("Quotes")

        val fav = db.quoteDao().allQuotes
        //  Favourite list (Local copy) from DB to check if it is favourite or not
        val favL = ArrayList<Quote>()
        for (i in fav)
        {
            if (i.fav)
                favL.add(i)
        }

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
                    if (favL.isNotEmpty()) btnFav.visibility = View.VISIBLE
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
        if (favL.isNotEmpty()) {
            var curr = 0
            var curIndex = favL[curr].id
            val liked = R.drawable.ic_heart_black_24dp
            val unLike = R.drawable.ic_heart_outline_24dp


            tvQuote.text = quotes?.get(curIndex)

            cv.setOnTouchListener(object : OnSwipeTouchListener(this@FavoritesActivity) {
                override fun onDoubleClick() {
                    btnFav.performClick()
                }
            })
            if (favL.size > 1) cv.setOnTouchListener(object : OnSwipeTouchListener(this@FavoritesActivity) {
                override fun onDoubleClick() {
                    btnFav.performClick()
                }
            override fun onSwipeLeft() {
                if (--curr < 0) curr = favL.size - 1
                curIndex = favL[curr].id
                val chkFav: Boolean? = db.quoteDao().chkFav(curIndex)
                if ((chkFav != null) && !chkFav) {
                    btnFav.setImageResource(unLike)
                    btnFav.tag = "unlike"
                } else {
                    btnFav.setImageResource(liked)
                    btnFav.tag = "like"
                }
                tvQuote.text = quotes?.get(curIndex)
            }

            override fun onSwipeRight() {
                if (++curr >= favL.size) curr = 0
                curIndex = favL[curr].id
                tvQuote.text = quotes?.get(curIndex)
                val chkFav: Boolean? = db.quoteDao().chkFav(curIndex)
                if ((chkFav != null) && !chkFav) {
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

            val chkFav: Boolean? = db.quoteDao().chkFav(curIndex)
            if ((chkFav != null) && chkFav) {
                btnFav.setImageResource(liked)
                btnFav.tag = "like"
            }
            btnFav.visibility = View.VISIBLE
            btnFav.setOnClickListener {
                if (btnFav.tag == "like") {
                    btnFav.setImageResource(unLike)
                    btnFav.tag = "unlike"
                    db.quoteDao().favQuote(Quote(curIndex, false))
                } else {
                    btnFav.setImageResource(liked)
                    btnFav.tag = "like"
                    db.quoteDao().favQuote(Quote(curIndex, true))
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onBackPressedDispatcher.onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
