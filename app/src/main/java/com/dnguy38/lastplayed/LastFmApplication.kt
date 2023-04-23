package com.dnguy38.lastplayed

import android.app.Application
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.dnguy38.lastplayed.data.LastFmApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LastFmApplication: Application() {
    lateinit var sharedPreferences: SharedPreferences

    companion object {
        lateinit var instance: LastFmApplication

        val api: LastFmApi by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://ws.audioscrobbler.com/2.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return@lazy retrofit.create(LastFmApi::class.java)
        }
    }

    override fun onCreate() {
        instance = this
        super.onCreate()

        val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        sharedPreferences = EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKey,
            applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        sharedPreferences.edit {
            putString("api_key", "d2cb043e580b552e0475d4fcd429d197")
            putString("shared_secret", "b8378a9fd9ea048193f8f1f85ebb4ff5")
        }
    }
}