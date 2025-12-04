package com.example.notifications.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.notifications.MainActivity
import com.example.notifications.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "FCMService"
        private const val CHANNEL_ID = "fcm_default_channel"
        private const val CHANNEL_NAME = "FCM Notifications"
        private const val CHANNEL_DESCRIPTION = "Notificaciones push de Firebase"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Nuevo token FCM: $token")
        
        // Aquí puedes enviar el token a tu servidor backend
        sendTokenToServer(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        
        Log.d(TAG, "Mensaje recibido de: ${message.from}")
        
        // Verificar si el mensaje contiene datos
        if (message.data.isNotEmpty()) {
            Log.d(TAG, "Datos del mensaje: ${message.data}")
            handleDataMessage(message.data)
        }
        
        // Verificar si el mensaje contiene una notificación
        message.notification?.let {
            Log.d(TAG, "Título: ${it.title}")
            Log.d(TAG, "Cuerpo: ${it.body}")
            
            showNotification(
                title = it.title ?: "Nueva notificación",
                body = it.body ?: "",
                data = message.data
            )
        }
    }

    private fun handleDataMessage(data: Map<String, String>) {
        // Procesar mensajes de datos personalizados
        val title = data["title"] ?: "Notificación"
        val body = data["body"] ?: "Tienes un nuevo mensaje"
        
        showNotification(title, body, data)
    }

    private fun showNotification(
        title: String,
        body: String,
        data: Map<String, String> = emptyMap()
    ) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        // Crear canal de notificación para Android O y superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESCRIPTION
                enableLights(true)
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }
        
        // Intent para abrir la app al tocar la notificación
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            // Pasar datos adicionales si es necesario
            data.forEach { (key, value) ->
                putExtra(key, value)
            }
        }
        
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        
        // Construir la notificación
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .build()
        
        // Mostrar la notificación
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    private fun sendTokenToServer(token: String) {
        // TODO: Implementar el envío del token a tu servidor backend
        // Ejemplo:
        // api.sendToken(token)
        Log.d(TAG, "Token enviado al servidor: $token")
    }
}
