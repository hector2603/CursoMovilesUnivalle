package com.example.notifications

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.notifications.ui.theme.NotificationsTheme
import com.example.notifications.utils.NotificationHelper
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Permiso de notificaciones concedido", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Permiso de notificaciones denegado", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Crear canal de notificación
        NotificationHelper.createNotificationChannel(this)
        
        // Solicitar permiso de notificaciones en Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
        
        setContent {
            NotificationsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NotificationScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun NotificationScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var fcmToken by remember { mutableStateOf("Obteniendo token...") }
    var isLoading by remember { mutableStateOf(true) }
    
    // Obtener el token FCM al iniciar
    LaunchedEffect(Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            isLoading = false
            if (task.isSuccessful) {
                fcmToken = task.result
                Log.d("MainActivity", "Token FCM: $fcmToken")
            } else {
                fcmToken = "Error al obtener el token"
                Log.e("MainActivity", "Error al obtener token", task.exception)
            }
        }
    }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Título
        Text(
            text = "Firebase Push Notifications",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        
        // Card con el token FCM
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Token FCM:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else {
                    Text(
                        text = fcmToken,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Button(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("FCM Token", fcmToken)
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(context, "Token copiado al portapapeles", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Copiar Token")
                    }
                }
            }
        }
        
        Divider()
        
        // Sección de notificaciones de prueba
        Text(
            text = "Notificaciones de Prueba",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        
        // Botón para notificación local simple
        Button(
            onClick = {
                NotificationHelper.showSimpleNotification(
                    context = context,
                    title = "Notificación Local",
                    message = "Esta es una notificación de prueba generada localmente"
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar Notificación Local")
        }
        
        // Botón para refrescar token
        OutlinedButton(
            onClick = {
                isLoading = true
                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    isLoading = false
                    if (task.isSuccessful) {
                        fcmToken = task.result
                        Toast.makeText(context, "Token actualizado", Toast.LENGTH_SHORT).show()
                    } else {
                        fcmToken = "Error al obtener el token"
                        Toast.makeText(context, "Error al actualizar token", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Refrescar Token FCM")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Información adicional
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "📱 Instrucciones",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "1. Copia el token FCM usando el botón de arriba\n" +
                            "2. Usa la consola de Firebase o una herramienta como Postman para enviar notificaciones\n" +
                            "3. Las notificaciones aparecerán incluso cuando la app esté en segundo plano",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        
        // Estado de permisos
        val hasPermission = NotificationHelper.hasNotificationPermission(context)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = if (hasPermission) 
                    MaterialTheme.colorScheme.tertiaryContainer 
                else 
                    MaterialTheme.colorScheme.errorContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Estado de Permisos",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (hasPermission) 
                        "✅ Permisos de notificación concedidos" 
                    else 
                        "❌ Permisos de notificación no concedidos",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}