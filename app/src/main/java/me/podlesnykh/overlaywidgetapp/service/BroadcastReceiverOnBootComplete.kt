package me.podlesnykh.overlaywidgetapp.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BroadcastReceiverOnBootComplete : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action?.equals(Intent.ACTION_BOOT_COMPLETED) == true) {
            val serviceIntent = Intent(context, FloatingWidgetService::class.java)
            context?.startService(serviceIntent)
        }
    }
}