package me.podlesnykh.overlaywidgetapp.presentation.activity_main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import me.podlesnykh.overlaywidgetapp.R
import me.podlesnykh.overlaywidgetapp.service.FloatingWidgetService


class MainActivity : AppCompatActivity() {

    companion object {
        private const val DRAW_OVER_OTHER_APP_PERMISSION = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        finish()
    }

    private fun askDrawOverAppPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, DRAW_OVER_OTHER_APP_PERMISSION)
        }
    }

    override fun onPause() {
        super.onPause()
        // чтобы сервис не стартовал без разрешения
        if (Settings.canDrawOverlays(this)) {
            val intent = Intent(this, FloatingWidgetService::class.java)
                .putExtra("activity_background", true)
            startService(intent)
            finish()
        } else {
            errorToast()
        }
    }

    private fun errorToast() {
        Toast.makeText(this, "Draw over permission required", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == DRAW_OVER_OTHER_APP_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    // если разрешение не получено
                    errorToast()
                    finish()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}