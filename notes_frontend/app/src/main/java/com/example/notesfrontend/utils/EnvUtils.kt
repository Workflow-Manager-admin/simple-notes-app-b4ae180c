package com.example.notesfrontend.utils

import android.content.Context

// PUBLIC_INTERFACE
object EnvUtils {
    /** These functions reference environment variables for SUPABASE_URL and SUPABASE_KEY. */
    fun getSupabaseUrl(context: Context): String? {
        return System.getenv("SUPABASE_URL")
            ?: context.getSharedPreferences("env", Context.MODE_PRIVATE)
                .getString("SUPABASE_URL", null)
    }

    fun getSupabaseKey(context: Context): String? {
        return System.getenv("SUPABASE_KEY")
            ?: context.getSharedPreferences("env", Context.MODE_PRIVATE)
                .getString("SUPABASE_KEY", null)
    }
}
