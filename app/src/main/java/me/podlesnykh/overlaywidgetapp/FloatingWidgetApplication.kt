package me.podlesnykh.overlaywidgetapp

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import me.podlesnykh.overlaywidgetapp.service.FloatingWidgetService

class FloatingWidgetApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        createWidgetService()
    }

    private fun createWidgetService() {
        if (Settings.canDrawOverlays(this)) {
            val intent = Intent(this, FloatingWidgetService::class.java)
            startService(intent)
        } else {
            askDrawOverAppPermission()
        }
    }

    private fun askDrawOverAppPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivity(intent)
        }
    }
}