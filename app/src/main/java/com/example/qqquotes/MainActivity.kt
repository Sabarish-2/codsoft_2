package com.example.qqquotes

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private val quotes: ArrayList<String> = arrayListOf(
        "Strive for progress, not perfection.",
        "Every accomplishment starts with the decision to try.",
        "Believe you can and you're halfway there.",
        "In the middle of difficulty lies opportunity.",
        "Success is not final, failure is not fatal: It is the courage to continue that counts.",
        "The only way to do great work is to love what you do.",
        "Action is the foundational key to all success.",
        "The best way to predict the future is to create it.",
        "Success is walking from failure to failure with no loss of enthusiasm.",
        "Dream big and dare to fail.",
        "Don't watch the clock; do what it does. Keep going.",
        "The only limit to our realization of tomorrow will be our doubts of today.",
        "Opportunities don't happen, you create them.",
        "The secret of getting ahead is getting started.",
        "If you want to achieve greatness, stop asking for permission.",
        "Your limitation—it's only your imagination.",
        "Push yourself because no one else is going to do it for you.",
        "Great things never come from comfort zones.",
        "Dream it. Wish it. Do it.",
        "Success doesn't just find you. You have to go out and get it.",
        "The harder you work for something, the greater you'll feel when you achieve it.",
        "Dream bigger. Do bigger.",
        "Don't stop when you're tired. Stop when you're done.",
        "Wake up with determination. Go to bed with satisfaction.",
        "Do something today that your future self will thank you for.",
        "Little things make big days.",
        "It's going to be hard, but hard does not mean impossible.",
        "Don't wait for opportunity. Create it.",
        "Sometimes we're tested not to show our weaknesses, but to discover our strengths.",
        "The key to success is to focus on goals, not obstacles.",
        "Dream it. Believe it. Build it.",
        "Wake up with determination. Sleep with satisfaction.",
        "The only limit is your mind.",
        "Prove them wrong.",
        "Do it with passion or not at all.",
        "Do your best and forget the rest.",
        "The journey of a thousand miles begins with one step.",
        "It's not about perfect. It's about effort.",
        "You are capable of more than you know.",
        "Believe you can and you're halfway there.",
        "One day or day one. You decide.",
        "The only way to do great work is to love what you do.",
        "Strive for progress, not perfection.",
        "You are enough.",
        "The harder you work, the luckier you get.",
        "Believe in yourself and all that you are.",
        "You don't have to be great to start, but you have to start to be great.",
        "Success is not the key to happiness. Happiness is the key to success.",
        "Do what you love, and the money will follow.",
        "Believe in yourself and you will be unstoppable.",
        "Success is not just about making money. It's about making a difference.",
        "Your attitude determines your direction.",
        "Hard work beats talent when talent doesn't work hard.",
        "If it doesn't challenge you, it won't change you.",
        "The only time you should ever look back is to see how far you've come.",
        "The difference between ordinary and extraordinary is that little extra.",
        "You are stronger than you think.",
        "Success is stumbling from failure to failure with no loss of enthusiasm.",
        "You are never too old to set another goal or to dream a new dream.",
        "A year from now you may wish you had started today.",
        "The only person you should try to be better than is the person you were yesterday.",
        "Don't count the days, make the days count.",
        "The best way to predict the future is to create it.",
        "The only limit to our realization of tomorrow will be our doubts of today.",
        "Success is the sum of small efforts repeated day in and day out.",
        "The future belongs to those who believe in the beauty of their dreams.",
        "Life is 10% what happens to us and 90% how we react to it.",
        "The best revenge is massive success.",
        "The way to get started is to quit talking and begin doing.",
        "Whatever you are, be a good one.",
        "Don't stop until you're proud.",
        "Your only limit is you.",
        "Don't wait for opportunity. Create it.",
        "The only thing that stands between you and your dream is the will to try and the belief that it is actually possible.",
        "Great things never come from comfort zones.",
        "A successful man is one who can lay a firm foundation with the bricks others have thrown at him.",
        "It always seems impossible until it is done.",
        "What you get by achieving your goals is not as important as what you become by achieving your goals.",
        "The only place where success comes before work is in the dictionary.",
        "Success is not in what you have, but who you are.",
        "Don't watch the clock; do what it does. Keep going.",
        "Don't let yesterday take up too much of today.",
        "The only way to do great work is to love what you do.",
        "The best revenge is massive success.",
        "Opportunities don't happen, you create them.",
        "Well done is better than well said.",
        "You don't have to be great to start, but you have to start to be great.",
        "The future belongs to those who believe in the beauty of their dreams.",
        "Don't count the days, make the days count.",
        "The only limit to our realization of tomorrow will be our doubts of today.",
        "The journey of a thousand miles begins with one step.",
        "You are never too old to set another goal or to dream a new dream.",
        "The only person you should try to be better than is the person you were yesterday.",
        "Believe you can and you're halfway there.",
        "Success is not the key to happiness. Happiness is the key to success.",
        "Action is the foundational key to all success.",
        "The only way to do great work is to love what you do.",
        "Strive for progress, not perfection.",
        "You miss 100% of the shots you don't take.",
        "Believe in yourself and all that you are.",
        "Sometimes, the quality of sadness is more poignant than the quality of happiness.",
        "In the silence of solitude, we often find the true quality of our existence.",
        "Quality is often overshadowed by the weight of mediocrity.",
        "Amidst the chaos of life, we search for the quality of peace.",
        "The quality of our sorrow reveals the depth of our love.",
        "In the darkness of despair, we discover the quality of our resilience.",
        "The quality of our regrets often surpasses the quality of our achievements.",
        "Lost in the shadows, we yearn for the quality of light.",
        "In the echo of loneliness, we measure the quality of our connections.",
        "Amidst the ruins of shattered dreams, we seek the quality of hope.",
        "The quality of our tears speaks volumes about the depth of our pain.",
        "In the silence of suffering, we find the true quality of our strength.",
        "The quality of our existence is often defined by the depth of our scars.",
        "In the depths of despair, we search for the quality of redemption.",
        "The quality of life is sometimes measured by the depth of our sorrows.",
        "Amidst the ruins of broken promises, we search for the quality of trust.",
        "In the chill of solitude, we long for the quality of warmth.",
        "The quality of our struggles reveals the depth of our character.",
        "Lost in the wilderness of uncertainty, we yearn for the quality of direction.",
        "In the shadows of doubt, we seek the quality of certainty.",
        "The quality of our fears often outweighs the quality of our hopes.",
        "Amidst the chaos of confusion, we search for the quality of clarity.",
        "In the labyrinth of despair, we seek the quality of purpose.",
        "The quality of our nightmares mirrors the darkness within our souls.",
        "In the void of emptiness, we yearn for the quality of fulfillment.",
        "Lost in the mists of memory, we search for the quality of remembrance.",
        "In the silence of grief, we find the true quality of our tears.",
        "The quality of our failures often shapes the trajectory of our lives.",
        "Amidst the wreckage of shattered dreams, we search for the quality of resilience.",
        "In the depths of despair, we discover the quality of our humanity.",
        "The quality of our pain often exceeds the capacity of our endurance.",
        "Lost in the labyrinth of regret, we yearn for the quality of forgiveness.",
        "In the echoes of abandonment, we seek the quality of acceptance.",
        "The quality of our solitude reveals the depth of our introspection.",
        "Amidst the storm of chaos, we search for the quality of serenity.",
        "In the darkness of despair, we find the true quality of our courage.",
        "The quality of our solitude often mirrors the depth of our introspection.",
        "Lost in the wilderness of despair, we yearn for the quality of hope.",
        "In the depths of sorrow, we seek the quality of solace.",
        "The quality of our losses often shapes the course of our lives.",
        "Amidst the wreckage of shattered dreams, we search for the quality of resilience.",
        "In the silence of grief, we find the true quality of our strength.",
        "The quality of our struggles often reveals the depth of our resilience.",
        "Lost in the darkness of despair, we yearn for the quality of light.",
        "In the depths of sorrow, we seek the quality of healing.",
        "The quality of our pain often exceeds the capacity of our understanding.",
        "Amidst the storm of chaos, we search for the quality of peace.",
        "In the silence of solitude, we find the true quality of our essence.",
        "The quality of our solitude often mirrors the depth of our introspection.",
        "Lost in the wilderness of despair, we yearn for the quality of hope.",
        "In the depths of sorrow, we seek the quality of solace.",
        "The quality of our losses often shapes the course of our lives.",
        "Amidst the wreckage of shattered dreams, we search for the quality of resilience.",
        "In the silence of grief, we find the true quality of our strength.",
        "The quality of our struggles often reveals the depth of our resilience.",
        "Lost in the darkness of despair, we yearn for the quality of light.",
        "In the depths of sorrow, we seek the quality of healing.",
        "The quality of our pain often exceeds the capacity of our understanding.",
        "Amidst the storm of chaos, we search for the quality of peace.",
        "In the silence of solitude, we find the true quality of our essence.",
        "The quality of our solitude often mirrors the depth of our introspection.",
        "Lost in the wilderness of despair, we yearn for the quality of hope.",
        "In the depths of sorrow, we seek the quality of solace.",
        "The quality of our losses often shapes the course of our lives.",
        "Amidst the wreckage of shattered dreams, we search for the quality of resilience.",
        "In the silence of grief, we find the true quality of our strength.",
        "The quality of our struggles often reveals the depth of our resilience.",
        "Lost in the darkness of despair, we yearn for the quality of light.",
        "In the depths of sorrow, we seek the quality of healing.",
        "The quality of our pain often exceeds the capacity of our understanding.",
        "Amidst the storm of chaos, we search for the quality of peace.",
        "In the silence of solitude, we find the true quality of our essence.",
        "The quality of our solitude often mirrors the depth of our introspection.",
        "Lost in the wilderness of despair, we yearn for the quality of hope.",
        "In the depths of sorrow, we seek the quality of solace.",
        "The quality of our losses often shapes the course of our lives.",
        "Amidst the wreckage of shattered dreams, we search for the quality of resilience.",
        "In the silence of grief, we find the true quality of our strength.",
        "The quality of our struggles often reveals the depth of our resilience.",
        "Lost in the darkness of despair, we yearn for the quality of light.",
        "In the depths of sorrow, we seek the quality of healing.",
        "The quality of our pain often exceeds the capacity of our understanding.",
        "Amidst the storm of chaos, we search for the quality of peace.",
        "In the silence of solitude, we find the true quality of our essence.",
        "The quality of our solitude often mirrors the depth of our introspection.",
        "Quality is like underwear; you only notice it when it's not there.",
        "I'm not lazy; I'm just in energy-saving mode.",
        "I'm not clumsy; I'm just embracing my inner gracefulness.",
        "Quality is like Wi-Fi, you only miss it when it's gone.",
        "I'm not arguing; I'm just explaining why I'm right.",
        "Quality is like humor; you can't force it.",
        "I'm not a procrastinator; I'm just on a prolonged coffee break.",
        "I'm not short; I'm just concentrated awesome.",
        "Quality is like a fine wine; it gets better with age.",
        "I'm not a control freak; I just know what you should be doing.",
        "I'm not a morning person; I'm a coffee enthusiast.",
        "Quality is like duct tape; it fixes almost anything.",
        "I'm not late; I'm just fashionably challenged by time.",
        "I'm not forgetful; I'm just creating suspense.",
        "Quality is like chocolate; it makes everything better.",
        "I'm not a mess; I'm just a colorful masterpiece in progress.",
        "I'm not old; I'm just a vintage classic.",
        "Quality is like pizza; even when it's bad, it's still pretty good.",
        "I'm not stubborn; my way is just better.",
        "I'm not indecisive; I'm just exploring all my options.",
        "Quality is like karma; what goes around, comes around.",
        "I'm not weird; I'm just limited edition.",
        "I'm not multitasking; I'm just inefficiently efficient.",
        "I'm not lazy; I'm just conserving energy for future endeavors.",
        "Quality is like music; it's all about the rhythm.",
        "I'm not clumsy; the floor just hates me.",
        "I'm not a control freak; I just have better ideas.",
        "I'm not disorganized; I'm creatively chaotic.",
        "Quality is like a good joke; it's all about the delivery.",
        "I'm not messy; I'm just creatively untidy.",
        "I'm not a perfectionist; I'm just very particular.",
        "Quality is like a hug; it's comforting and always appreciated.",
        "I'm not procrastinating; I'm just prioritizing leisure time.",
        "I'm not lost; I'm just taking the scenic route.",
        "I'm not addicted to chocolate; I'm committed to it.",
        "Quality is like a smile; it's contagious.",
        "I'm not talking to myself; I'm having a private conversation with my inner genius.",
        "I'm not a quitter; I'm just strategically surrendering.",
        "I'm not ignoring you; I'm just giving you the silent treatment.",
        "Quality is like a good joke; it's all about the timing.",
        "I'm not antisocial; I'm selectively social.",
        "I'm not a hoarder; I'm a collector of memories.",
        "I'm not overthinking; I'm just exploring every possible outcome.",
        "I'm not impatient; I'm just very enthusiastic about instant gratification.",
        "Quality is like a good cup of tea; it's soothing for the soul.",
        "I'm not avoiding responsibility; I'm just delegating it to someone else.",
        "I'm not a procrastinator; I'm just on a deadline-driven adrenaline rush.",
        "I'm not forgetful; I'm just selectively remembering.",
        "Quality is like good manners; it never goes out of style.",
        "I'm not a night owl; I'm just nocturnally inclined.",
        "I'm not a control freak; I'm just very persuasive.",
        "I'm not nosy; I'm just highly interested in your business.",
        "I'm not a pessimist; I'm just an optimist with experience.",
        "I'm not ignoring you; I'm just giving you the gift of absence.",
        "I'm not a klutz; I'm just auditioning for a slapstick comedy.",
        "I'm not a couch potato; I'm a leisure enthusiast.",
        "I'm not avoiding work; I'm just enhancing my procrastination skills.",
        "I'm not grumpy; I'm just embracing my inner grouch.",
        "I'm not a know-it-all; I'm just highly knowledgeable.",
        "I'm not high maintenance; I'm just low tolerance for mediocrity.",
        "I'm not a drama queen; I'm a melodrama enthusiast.",
        "I'm not antisocial; I'm just pro-solitude.",
        "I'm not a morning person; I'm a nocturnal warrior.",
        "I'm not paranoid; I'm just highly alert.",
        "I'm not ignoring you; I'm just practicing selective hearing.",
        "I'm not a scatterbrain; I'm just creatively spontaneous.",
        "I'm not avoiding exercise; I'm just procrastinating sweat.",
        "I'm not a shopaholic; I'm just financially enthusiastic.",
        "I'm not a snob; I'm just highly selective.",
        "I'm not a hypochondriac; I'm just healthily cautious.",
        "I'm not a control freak; I'm just a quality enthusiast.",
        "I'm not a procrastinator; I'm just a deadline enthusiast.",
        "I'm not a morning person; I'm just allergic to mornings.",
        "I'm not a social butterfly; I'm a conversation connoisseur.",
        "I'm not indecisive; I'm just exploring all possibilities.",
        "I'm not a klutz; I'm just auditioning for a slapstick comedy.",
        "I'm not a couch potato; I'm a leisure enthusiast.",
        "I'm not avoiding work; I'm just enhancing my procrastination skills.",
        "I'm not grumpy; I'm just embracing my inner grouch.",
        "I'm not a know-it-all; I'm just highly knowledgeable.",
        "I'm not high maintenance; I'm just low tolerance for mediocrity.",
        "I'm not a drama queen; I'm a melodrama enthusiast.",
        "I'm not antisocial; I'm just pro-solitude.",
        "I'm not a morning person; I'm a nocturnal warrior.",
        "I'm not paranoid; I'm just highly alert.",
        "I'm not ignoring you; I'm just practicing selective hearing.",
        "I'm not a scatterbrain; I'm just creatively spontaneous.",
        "I'm not avoiding exercise; I'm just procrastinating sweat.",
        "I'm not a shopaholic; I'm just financially enthusiastic.",
        "I'm not a snob; I'm just highly selective.",
        "I'm not lazy, I'm just on energy-saving mode.",
        "I'm not arguing, I'm just explaining why I'm right.",
        "I'm not clumsy, it's just the floor hates me, the table and chairs are bullies, and the walls get in my way.",
        "I'm not short, I'm just concentrated awesome.",
        "I'm not addicted to chocolate, we're just in a committed relationship.",
        "I'm not a complete idiot, some parts are missing.",
        "I'm not a morning person. I'm a coffee person.",
        "I'm not fat, I'm just easy to see.",
        "I'm not late, everyone else is just early.",
        "I'm not random, I just have many thoughts."
    )
    private var quoteNum by Delegates.notNull<Int>()
    private lateinit var toolBar: androidx.appcompat.widget.Toolbar
    private val liked = R.drawable.ic_heart_black_24dp
    private val unLike = R.drawable.ic_heart_outline_24dp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolBar = findViewById(R.id.toolBar)
        setSupportActionBar(toolBar)
        supportActionBar?.title = "Quick Quality QuoteS App"

        val tvQuote: TextView = findViewById(R.id.tv_quote)
        val btnRefresh: ImageView = findViewById(R.id.im_quote_refresh)
        val btnFav: ImageView = findViewById(R.id.im_quote_like)
        val btnShare: ImageView = findViewById(R.id.im_quote_share)

        val pref = getSharedPreferences("favQ", Context.MODE_PRIVATE)
        var favL: MutableSet<String>? = pref.getStringSet("favI", null)


        tvQuote.text = getRandom()
        randomStyles(tvQuote)
        btnRefresh.visibility = View.VISIBLE
        btnRefresh.setOnClickListener {
            tvQuote.text = getRandom()
            favL = pref.getStringSet("favI", null)
            if (favL?.contains(quoteNum.toString()) == true) {
                btnFav.setImageResource(liked)
                btnFav.tag = "like"
            } else {
                btnFav.setImageResource(unLike)
                btnFav.tag = "unlike"
            }
            randomStyles(tvQuote)
        }

        btnShare.setOnClickListener {
            val iShare = Intent(Intent.ACTION_SEND)
            iShare.setType("text/plain")
            iShare.putExtra(Intent.EXTRA_TEXT, tvQuote.text.toString())
            startActivity(Intent.createChooser(iShare, "Share quote via"))
        }

        if (favL != null && favL!!.contains(quoteNum.toString())) btnFav.setImageResource(liked)
        btnFav.visibility = View.VISIBLE
        btnFav.setOnClickListener {
            if (btnFav.tag == "like") {
                btnFav.setImageResource(unLike)
                btnFav.tag = "unlike"
                favL = pref.getStringSet("favI", null)
                val setL = favL?.toMutableSet()
                setL?.remove(quoteNum.toString())
                val editor = pref.edit()
                editor.putStringSet("favI", setL)
                editor.apply()
            } else {
                btnFav.setImageResource(liked)
                btnFav.tag = "like"
                favL = pref.getStringSet("favI", null)
                if (favL != null) {
                    favL!!.add(quoteNum.toString())
                } else {
                    favL = mutableSetOf(quoteNum.toString())
                }
                val editor = pref.edit()
                editor.putStringSet("favI", favL)
                editor.apply()
            }
        }


        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val backDialog: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
                backDialog.setTitle("Close Quick Quality QuoteS App?")
                backDialog.setIcon(R.drawable.baseline_exit_to_app_24)
                backDialog.setMessage("Are you sure you want to exit?")
                backDialog.setPositiveButton("Yes") { dialog, _ ->
                    dialog.dismiss()
                    finish()
                }
                backDialog.setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }

                backDialog.show()
            }

        }
        onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }


    private fun randomStyles(tvQuote: TextView) {
        val rI = Math.random() < 0.3
        val rB = Math.random() > 0.3
        val rBI = Math.random() < 0.2
        val rCAPS = Math.random() > 0.3 && Math.random() < 0.6

        tvQuote.isAllCaps = rCAPS
        when {
            rI -> tvQuote.setTypeface(null, Typeface.ITALIC)

            rBI -> tvQuote.setTypeface(null, Typeface.BOLD_ITALIC)

            rB -> tvQuote.setTypeface(null, Typeface.BOLD)
        }
    }

    private fun getRandom(): String {
        quoteNum = (0 until quotes.size).random()
        return quotes[quoteNum]
    }

    override fun onResume() {
        val pref = getSharedPreferences("favQ", Context.MODE_PRIVATE)
        val favL = pref.getStringSet("favI", null)
        val btnFav: ImageView = findViewById(R.id.im_quote_like)
        if (favL?.contains(quoteNum.toString()) == true) {
            btnFav.setImageResource(liked)
            btnFav.tag = "like"
        } else if (favL != null) {
            btnFav.setImageResource(unLike)
            btnFav.tag = "unlike"
        }
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.opt_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_fav -> {
                val iNext = Intent(this, FavoritesActivity::class.java)
                iNext.putStringArrayListExtra("Quotes", quotes)
                startActivity(iNext)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
