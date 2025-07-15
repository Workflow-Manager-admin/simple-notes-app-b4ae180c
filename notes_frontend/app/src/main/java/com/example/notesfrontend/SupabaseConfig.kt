package com.example.notesfrontend

import android.content.Context
import com.example.notesfrontend.utils.EnvUtils

// PUBLIC_INTERFACE
object SupabaseConfig {
    fun getSupabaseUrl(context: Context): String? = EnvUtils.getSupabaseUrl(context)
    fun getSupabaseKey(context: Context): String? = EnvUtils.getSupabaseKey(context)
}
