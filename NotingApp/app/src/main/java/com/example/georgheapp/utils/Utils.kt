package com.example.georgheapp.utils

import android.content.Context
import android.widget.Toast

fun Context.toast(message: Any?) {
    message?.let {
        Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
    }
}
