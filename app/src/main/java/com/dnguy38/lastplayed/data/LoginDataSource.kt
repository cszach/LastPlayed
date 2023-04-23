package com.dnguy38.lastplayed.data

import android.util.Log
import androidx.core.content.edit
import com.dnguy38.lastplayed.data.model.LoggedInUser
import com.dnguy38.lastplayed.LastFmApplication.Companion as Application
import retrofit2.Response
import java.io.IOException
import java.math.BigInteger
import java.security.MessageDigest

private val TAG = "com.dnguy38.lastplayed.data.LoginDataSource"

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        // val fakeUser = LoggedInUser("john_doe")
        // return Result.Success(fakeUser)

        val applicationSharedPreferences = Application.instance.sharedPreferences

        val apiKey = applicationSharedPreferences.getString("api_key", null)
        val secret = applicationSharedPreferences.getString("shared_secret", null)

        if (apiKey == null || secret == null) {
            return Result.Error(java.lang.IllegalStateException("API key not found in shared preferences"))
        }

        val request = Application.api.getMobileSession(
            username,
            password,
            apiKey,
            apiSignature(username, password, apiKey, secret)
        )

        lateinit var response: Response<MobileSessionResponse>
        val networkThread = Thread {
            response = request.execute()
        }

        networkThread.start()
        networkThread.join()

        Log.d(TAG, response.toString())

        if (response.isSuccessful) {
            val body = response.body()
            val lastFmMobileSession: MobileSession =
                body?.session ?: return Result.Error(IOException("Error logging in"))

            applicationSharedPreferences.edit {
                putString("session_key", lastFmMobileSession.key)
            }

            return Result.Success(LoggedInUser(lastFmMobileSession.name))
        } else {
            return Result.Error(IOException("Error logging in"))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }

    private fun apiSignature(
        username: String,
        password: String,
        apiKey: String,
        secret: String
    ): String {
        val message =
            "api_key${apiKey}methodauth.getMobileSessionpassword${password}username${username}${secret}"
        return md5(message)
    }

    private fun md5(message: String): String {
        val md5 = MessageDigest.getInstance("MD5")
        val digest = md5.digest(message.toByteArray())

        return String.format("%032x", BigInteger(1, digest))
    }
}