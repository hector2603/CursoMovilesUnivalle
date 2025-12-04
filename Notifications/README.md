# 📱 Notifications App - Firebase Cloud Messaging Demo

Aplicación de ejemplo para demostrar la implementación de notificaciones push con Firebase Cloud Messaging (FCM) en Android usando Kotlin y Jetpack Compose.

## 🎯 Características

- ✅ **Firebase Cloud Messaging (FCM)** - Notificaciones push en tiempo real
- ✅ **Jetpack Compose** - UI moderna y declarativa
- ✅ **Material Design 3** - Diseño moderno y consistente
- ✅ **Notificaciones Locales** - Prueba notificaciones sin servidor
- ✅ **Gestión de Permisos** - Manejo de permisos de Android 13+
- ✅ **Token FCM** - Visualización y copia del token del dispositivo
- ✅ **Canales de Notificación** - Soporte para Android O+

## 🚀 Inicio Rápido

### 1. Clonar el Repositorio
```bash
git clone <tu-repositorio>
cd Notifications
```

### 2. Configurar Firebase

**⚠️ IMPORTANTE:** Antes de ejecutar la app, debes configurar Firebase:

1. Ve a [Firebase Console](https://console.firebase.google.com/)
2. Crea un nuevo proyecto o usa uno existente
3. Agrega una app Android con el package name: `com.example.notifications`
4. Descarga el archivo `google-services.json`
5. Coloca `google-services.json` en la carpeta `app/`

📖 **Guía detallada:** Lee [FIREBASE_SETUP.md](FIREBASE_SETUP.md) para instrucciones paso a paso.

### 3. Ejecutar la App

1. Abre el proyecto en Android Studio
2. Sincroniza Gradle (Sync Project with Gradle Files)
3. Ejecuta la app en un dispositivo físico o emulador
4. Acepta los permisos de notificación cuando se soliciten

## 📂 Estructura del Proyecto

```
app/src/main/java/com/example/notifications/
├── MainActivity.kt                          # Actividad principal con UI
├── service/
│   └── MyFirebaseMessagingService.kt       # Servicio para recibir notificaciones FCM
├── utils/
│   └── NotificationHelper.kt                # Utilidades para notificaciones locales
└── ui/theme/                                # Tema de la aplicación
```

## 🧪 Probar Notificaciones

### Opción 1: Notificación Local (Sin Firebase)

1. Abre la app
2. Haz clic en "Enviar Notificación Local"
3. Verás una notificación de prueba

### Opción 2: Desde Firebase Console

1. Copia el token FCM desde la app (botón "Copiar Token")
2. Ve a Firebase Console → Cloud Messaging
3. Haz clic en "Enviar mensaje de prueba"
4. Pega el token y envía

### Opción 3: Con cURL

```bash
curl -X POST https://fcm.googleapis.com/fcm/send \
  -H "Authorization: key=TU_SERVER_KEY" \
  -H "Content-Type: application/json" \
  -d '{
    "to": "TOKEN_DEL_DISPOSITIVO",
    "notification": {
      "title": "Hola",
      "body": "Notificación de prueba"
    }
  }'
```

## 🔔 Tipos de Mensajes FCM

### 1. Solo Notificación
```json
{
  "to": "TOKEN",
  "notification": {
    "title": "Título",
    "body": "Mensaje"
  }
}
```

### 2. Solo Datos
```json
{
  "to": "TOKEN",
  "data": {
    "title": "Título",
    "body": "Mensaje",
    "custom_key": "valor"
  }
}
```

### 3. Combinado
```json
{
  "to": "TOKEN",
  "notification": {
    "title": "Título",
    "body": "Mensaje"
  },
  "data": {
    "screen": "profile",
    "user_id": "123"
  }
}
```

## 🛠️ Tecnologías Utilizadas

- **Kotlin** - Lenguaje de programación
- **Jetpack Compose** - Framework de UI
- **Firebase Cloud Messaging** - Notificaciones push
- **Material Design 3** - Sistema de diseño
- **Android SDK 24+** - Plataforma Android

## 📋 Requisitos

- Android Studio Hedgehog o superior
- Android SDK 24 (Android 7.0) o superior
- Cuenta de Google para Firebase
- Dispositivo físico o emulador con Google Play Services

## 🔧 Configuración de Gradle

### Versiones Principales

```kotlin
compileSdk = 36
minSdk = 24
targetSdk = 36

// Firebase
firebaseBom = "33.5.1"
googleServices = "4.4.2"
```

### Dependencias

```kotlin
// Firebase
implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
implementation("com.google.firebase:firebase-messaging-ktx")
implementation("com.google.firebase:firebase-analytics-ktx")
```

## 📱 Capturas de Pantalla

La app muestra:
- Token FCM del dispositivo
- Botón para copiar el token
- Botón para enviar notificación local de prueba
- Estado de permisos de notificación
- Instrucciones de uso

## 🐛 Solución de Problemas

### No aparece el token FCM
- Verifica que `google-services.json` esté en `app/`
- Asegúrate de tener conexión a Internet
- Revisa los logs: `adb logcat | grep FCM`

### No recibo notificaciones
- Verifica los permisos de notificación
- Asegúrate de que la app tenga acceso a Internet
- Revisa que el token sea correcto

### Error al compilar
- Sincroniza Gradle: File → Sync Project with Gradle Files
- Limpia el proyecto: Build → Clean Project
- Reconstruye: Build → Rebuild Project

## 📚 Recursos Adicionales

- [Documentación de FCM](https://firebase.google.com/docs/cloud-messaging)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
- [Guía de Configuración Detallada](FIREBASE_SETUP.md)

## 🤝 Contribuir

Las contribuciones son bienvenidas. Por favor:

1. Haz fork del proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto es de código abierto y está disponible para fines educativos.

## 👨‍💻 Autor

Proyecto creado para el curso de Desarrollo Móvil - Universidad del Valle

## 🎓 Propósito Educativo

Esta aplicación fue creada con fines educativos para demostrar:
- Implementación de Firebase Cloud Messaging
- Uso de Jetpack Compose
- Manejo de permisos en Android
- Arquitectura de servicios en Android
- Mejores prácticas de desarrollo Android

---

**¿Necesitas ayuda?** Revisa [FIREBASE_SETUP.md](FIREBASE_SETUP.md) para una guía detallada de configuración.

**¡Disfruta programando!** 🚀

---

## 📚 Explicación Completa: Notificaciones Push con Firebase

### 🔔 1. ¿Qué son las Notificaciones Push?

#### Concepto Básico

Las **notificaciones push** son mensajes que una aplicación puede enviar a los dispositivos de los usuarios **incluso cuando la app no está abierta**. Es como recibir un mensaje de texto, pero desde una aplicación.

#### ¿Cómo Funcionan?

```
[Tu Servidor] → [Firebase Cloud Messaging] → [Dispositivo del Usuario]
     ↓                    ↓                           ↓
  Envía mensaje    Procesa y enruta          Muestra notificación
```

**Flujo completo:**

1. **Registro:** La app se registra en FCM y obtiene un token único
2. **Almacenamiento:** Tu servidor guarda ese token en una base de datos
3. **Envío:** Cuando quieres notificar al usuario, envías un mensaje a FCM con el token
4. **Entrega:** FCM se encarga de entregar el mensaje al dispositivo correcto
5. **Visualización:** El dispositivo muestra la notificación

#### Ventajas

- ✅ Funcionan con la app cerrada
- ✅ No consumen batería constantemente (no hay polling)
- ✅ Entrega garantizada por Google
- ✅ Escalable a millones de dispositivos

### 🔥 2. Firebase Cloud Messaging (FCM)

#### ¿Qué es FCM?

**Firebase Cloud Messaging** es el servicio de Google que actúa como **intermediario** entre tu servidor y los dispositivos de los usuarios.

#### Arquitectura de FCM

```
┌─────────────────────────────────────────────────────────┐
│                    TU BACKEND/SERVIDOR                   │
│  (Node.js, Python, PHP, Java, etc.)                     │
└────────────────────┬────────────────────────────────────┘
                     │ Envía mensaje con token
                     ↓
┌─────────────────────────────────────────────────────────┐
│            FIREBASE CLOUD MESSAGING (FCM)                │
│  • Valida el token                                       │
│  • Enruta el mensaje                                     │
│  • Maneja reintentos                                     │
│  • Gestiona prioridades                                  │
└────────────────────┬────────────────────────────────────┘
                     │ Entrega a través de Google Play Services
                     ↓
┌─────────────────────────────────────────────────────────┐
│                  DISPOSITIVO ANDROID                     │
│  • Google Play Services recibe el mensaje                │
│  • Despierta tu app si está dormida                      │
│  • Llama a MyFirebaseMessagingService                    │
│  • Tu app procesa y muestra la notificación             │
└─────────────────────────────────────────────────────────┘
```

#### Componentes Clave

- **FCM Server:** Infraestructura de Google que maneja millones de mensajes
- **Google Play Services:** En el dispositivo, mantiene conexión con FCM
- **Tu App:** Recibe y procesa los mensajes

### 🎫 3. El Token FCM

#### ¿Qué es el Token FCM?

El **token FCM** es un **identificador único** que Firebase asigna a cada instalación de tu app en un dispositivo específico.

**Ejemplo de token:**
```
dXpL8F9xQR2:APA91bH7mKjN3vX2wY4zB5cD6eF7gH8iJ9kL0mN1oP2qR3sT4uV5wX6yZ7aB8cD9eF0gH1iJ2kL3mN4oP5qR6sT7uV8wX9yZ0aB1cD2eF3gH4iJ5kL6mN7oP8qR9sT0uV1wX2yZ3aB4cD5eF6gH7iJ8kL9mN0oP1qR2sT3uV4wX5yZ6aB7cD8eF9gH0iJ1kL2mN3oP4qR5sT6uV7wX8yZ9
```

#### Características del Token

- **✅ Es Único:** Cada instalación de tu app tiene un token diferente (2 dispositivos = 2 tokens)
- **🔄 Puede Cambiar:** Se regenera si se desinstala la app, se borran datos o se restaura en otro dispositivo
- **🔐 Es Seguro:** No contiene datos personales, solo sirve para enviar notificaciones a ESE dispositivo

#### Ciclo de Vida del Token

```kotlin
// 1. La app se instala
// 2. Firebase genera un token automáticamente
// 3. onNewToken() se llama

override fun onNewToken(token: String) {
    Log.d("FCM", "Nuevo token: $token")
    // 4. Debes enviar este token a tu servidor
    sendTokenToServer(token)
}

// 5. Tu servidor guarda el token en la base de datos
// 6. Cuando quieres notificar al usuario, usas ese token
```


### 🛠️ 4. Implementación Paso a Paso

#### Paso 1: Configuración de Dependencias

**Archivo:** `gradle/libs.versions.toml`

```toml
[versions]
firebaseBom = "33.5.1"
googleServices = "4.4.2"

[libraries]
firebase-bom = { group = "com.google.firebase", name = "firebase-bom", version.ref = "firebaseBom" }
firebase-messaging = { group = "com.google.firebase", name = "firebase-messaging-ktx" }
firebase-analytics = { group = "com.google.firebase", name = "firebase-analytics-ktx" }

[plugins]
google-services = { id = "com.google.gms.google-services", version.ref = "googleServices" }
```

**¿Por qué?**

- **Firebase BOM:** Gestiona versiones compatibles automáticamente
- **firebase-messaging-ktx:** La librería principal de FCM
- **google-services plugin:** Procesa el `google-services.json` y conecta tu app

#### Paso 2: Servicio de Mensajería

**Archivo:** `MyFirebaseMessagingService.kt`

```kotlin
class MyFirebaseMessagingService : FirebaseMessagingService() {
    
    // Se llama cuando Firebase genera un nuevo token
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Nuevo token FCM: $token")
        sendTokenToServer(token)
    }
    
    // Se llama cuando llega un mensaje
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        
        // Procesar el mensaje
        message.notification?.let {
            showNotification(it.title ?: "Notificación", it.body ?: "")
        }
    }
}
```

**Tipos de Mensajes:**

| Tipo | Comportamiento App Abierta | Comportamiento App Cerrada |
|------|---------------------------|---------------------------|
| **Notification** | `onMessageReceived()` | Sistema muestra notificación |
| **Data** | `onMessageReceived()` | `onMessageReceived()` |
| **Combinado** | `onMessageReceived()` | Notificación visible + datos en intent |

#### Paso 3: Configuración del Manifest

**Archivo:** `AndroidManifest.xml`

```xml
<!-- Permisos -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />

<!-- Servicio FCM -->
<service
    android:name=".service.MyFirebaseMessagingService"
    android:exported="false">
    <intent-filter>
        <action android:name="com.google.firebase.MESSAGING_EVENT" />
    </intent-filter>
</service>

<!-- Metadata (Canal por defecto) -->
<meta-data
    android:name="com.google.firebase.messaging.default_notification_channel_id"
    android:value="fcm_default_channel" />
```


#### Paso 4: Canales de Notificación (Android 8+)

```kotlin
fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            CHANNEL_ID,                          // ID único
            CHANNEL_NAME,                        // Nombre visible
            NotificationManager.IMPORTANCE_HIGH  // Importancia
        ).apply {
            description = CHANNEL_DESCRIPTION
            enableLights(true)
            enableVibration(true)
        }
        
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}
```


#### Paso 5: Mostrar Notificaciones

```kotlin
private fun showNotification(title: String, body: String) {
    // 1. Crear intent para abrir la app al tocar
    val intent = Intent(this, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        this, 0, intent, 
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
    
    // 2. Construir la notificación
    val notification = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle(title)
        .setContentText(body)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setAutoCancel(true)                    // Se cierra al tocar
        .setContentIntent(pendingIntent)        // Abre la app
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .build()
    
    // 3. Mostrar
    notificationManager.notify(notificationId, notification)
}
```


#### Paso 6: UI con Jetpack Compose y Obtención de Token

**Archivo:** `MainActivity.kt`

```kotlin
@Composable
fun NotificationScreen() {
    var fcmToken by remember { mutableStateOf("Obteniendo...") }
    
    LaunchedEffect(Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                fcmToken = task.result  // Aquí está el token
            }
        }
    }
    
    // UI para mostrar el token
    Text(text = fcmToken)
}
```


### 🔄 5. Flujo Completo de una Notificación

#### 1. REGISTRO (Una vez)
```
┌─────────────────────────────────────────┐
│ Usuario instala la app                  │
│ ↓                                        │
│ Firebase genera token único             │
│ ↓                                        │
│ onNewToken() se llama                   │
│ ↓                                        │
│ App envía token a tu servidor           │
│ ↓                                        │
│ Servidor guarda token en BD             │
└─────────────────────────────────────────┘
```

#### 2. ENVÍO (Cada vez que quieres notificar)
```
┌─────────────────────────────────────────┐
│ Tu servidor decide enviar notificación  │
│ ↓                                        │
│ Busca el token del usuario en BD        │
│ ↓                                        │
│ Envía petición HTTP a FCM con:          │
│   - Token del usuario                   │
│   - Título y mensaje                    │
│   - Datos adicionales (opcional)        │
└─────────────────────────────────────────┘
```

#### 3. PROCESAMIENTO (FCM)
```
┌─────────────────────────────────────────┐
│ FCM recibe la petición                  │
│ ↓                                        │
│ Valida el token                         │
│ ↓                                        │
│ Encuentra el dispositivo correcto       │
│ ↓                                        │
│ Envía el mensaje a través de            │
│ Google Play Services                    │
└─────────────────────────────────────────┘
```

#### 4. RECEPCIÓN (Dispositivo)
```
┌─────────────────────────────────────────┐
│ Google Play Services recibe mensaje     │
│ ↓                                        │
│ Despierta tu app si está dormida        │
│ ↓                                        │
│ Llama a onMessageReceived()             │
│ ↓                                        │
│ Tu código procesa el mensaje            │
│ ↓                                        │
│ Muestra la notificación                 │
│ ↓                                        │
│ Usuario ve la notificación              │
└─────────────────────────────────────────┘
```


### 📊 6. Resumen de Archivos Creados

| Archivo | Propósito |
|---------|-----------|
| `MyFirebaseMessagingService.kt` | Recibe y procesa mensajes FCM |
| `NotificationHelper.kt` | Utilidades para notificaciones locales (Canales) |
| `MainActivity.kt` | UI para mostrar token y probar permisos |
| `AndroidManifest.xml` | Registra servicio y permisos obligatorios |
| `libs.versions.toml` | Gestión centralizada de dependencias |

### 🎯 7. Conceptos Clave para Recordar

#### Token FCM = Dirección del dispositivo

- **Único por instalación:** Cada instalación tiene su propio token
- **Puede cambiar:** Se debe manejar en `onNewToken()`
- **Crucial:** Debes guardarlo en tu servidor backend

#### FirebaseMessagingService

- Es el **"oído"** de tu app
- `onMessageReceived()`: Se dispara cuando llega un mensaje y la app está activa (o es un mensaje de datos)

#### Permisos y Canales

- **Android 13+:** Requiere permiso `POST_NOTIFICATIONS` en tiempo de ejecución
- **Android 8+:** Requiere crear `NotificationChannel` para mostrar alertas

---

**¿Tienes preguntas?** Revisa la documentación completa en [FIREBASE_SETUP.md](FIREBASE_SETUP.md) o [EJEMPLOS_ENVIO.md](EJEMPLOS_ENVIO.md)
