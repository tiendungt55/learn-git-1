package com.example.notification1_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class NotificationDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_detail)
        val textView=findViewById<TextView>(R.id.text_view)
        textView.text=intent.getStringExtra(EXTRA_NOTIFICATION_CONTENT)
    }
    companion object{
        const val EXTRA_NOTIFICATION_CONTENT="EXTRA_NOTIFICATION_CONTENT"
    }
}