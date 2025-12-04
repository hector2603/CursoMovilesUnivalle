# 🔥 Configuración de Firebase Cloud Messaging (FCM)

Esta guía te ayudará a configurar Firebase Cloud Messaging para recibir notificaciones push en tu aplicación Android.

## 📋 Requisitos Previos

- Android Studio instalado
- Una cuenta de Google
- Acceso a la [Consola de Firebase](https://console.firebase.google.com/)

## 🚀 Pasos de Configuración

### 1. Crear un Proyecto en Firebase

1. Ve a [Firebase Console](https://console.firebase.google.com/)
2. Haz clic en "Agregar proyecto" o "Add project"
3. Ingresa el nombre del proyecto: `Notifications` (o el que prefieras)
4. Acepta los términos y condiciones
5. Opcionalmente habilita Google Analytics
6. Haz clic en "Crear proyecto"

### 2. Agregar tu App Android al Proyecto Firebase

1. En la consola de Firebase, haz clic en el ícono de Android
2. Ingresa el nombre del paquete: `com.example.notifications`
3. (Opcional) Ingresa un apodo para la app: `Notifications App`
4. (Opcional) Ingresa el SHA-1 de certificado de depuración:
   ```bash
   # En macOS/Linux:
   keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
   
   # En Windows:
   keytool -list -v -keystore "%USERPROFILE%\.android\debug.keystore" -alias androiddebugkey -storepass android -keypass android
   ```
5. Haz clic en "Registrar app"

### 3. Descargar el Archivo google-services.json

1. Descarga el archivo `google-services.json`
2. Coloca el archivo en la carpeta `app/` de tu proyecto:
   ```
   Notifications/
   └── app/
       └── google-services.json  ← Aquí
   ```
3. **IMPORTANTE**: Este archivo contiene información sensible. Asegúrate de que esté en `.gitignore`

### 4. Verificar las Dependencias (Ya Configuradas)

Las siguientes dependencias ya están configuradas en el proyecto:

**build.gradle.kts (Project level):**
```kotlin
plugins {
    alias(libs.plugins.google.services) apply false
}
```

**build.gradle.kts (App level):**
```kotlin
plugins {
    alias(libs.plugins.google.services)
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.analytics)
}
```

### 5. Sincronizar el Proyecto

1. En Android Studio, haz clic en "Sync Now" o "Sync Project with Gradle Files"
2. Espera a que se descarguen todas las dependencias

## 📱 Probar las Notificaciones

### Opción 1: Desde la App (Notificaciones Locales)

1. Ejecuta la aplicación en un dispositivo físico o emulador
2. Acepta los permisos de notificación cuando se soliciten
3. Haz clic en "Enviar Notificación Local" para probar notificaciones locales

### Opción 2: Desde la Consola de Firebase

1. Ve a Firebase Console → Cloud Messaging
2. Haz clic en "Enviar tu primer mensaje" o "Send your first message"
3. Ingresa el título y texto de la notificación
4. Haz clic en "Enviar mensaje de prueba"
5. Copia el token FCM de tu app (se muestra en la pantalla principal)
6. Pega el token en el campo "Agregar un token de registro FCM"
7. Haz clic en "Probar"

### Opción 3: Usando Postman o cURL

**Obtén tu Server Key:**
1. Ve a Firebase Console → Configuración del proyecto → Cloud Messaging
2. Copia la "Clave del servidor" (Server Key)

**Ejemplo con cURL:**
```bash
curl -X POST https://fcm.googleapis.com/fcm/send \
  -H "Authorization: key=TU_SERVER_KEY_AQUI" \
  -H "Content-Type: application/json" \
  -d '{
    "to": "TOKEN_FCM_DEL_DISPOSITIVO",
    "notification": {
      "title": "Hola desde cURL",
      "body": "Esta es una notificación de prueba",
      "icon": "ic_launcher"
    },
    "data": {
      "custom_key": "custom_value"
    }
  }'
```

**Ejemplo con Postman:**
- **Método:** POST
- **URL:** `https://fcm.googleapis.com/fcm/send`
- **Headers:**
  - `Authorization: key=TU_SERVER_KEY_AQUI`
  - `Content-Type: application/json`
- **Body (raw JSON):**
```json
{
  "to": "TOKEN_FCM_DEL_DISPOSITIVO",
  "notification": {
    "title": "Hola desde Postman",
    "body": "Esta es una notificación de prueba",
    "icon": "ic_launcher"
  },
  "data": {
    "custom_key": "custom_value",
    "screen": "home"
  }
}
```

## 📂 Estructura del Proyecto

```
app/src/main/java/com/example/notifications/
├── MainActivity.kt                          # Pantalla principal con UI
├── service/
│   └── MyFirebaseMessagingService.kt       # Servicio FCM
└── utils/
    └── NotificationHelper.kt                # Utilidades para notificaciones
```

## 🔔 Tipos de Notificaciones

### 1. Notificaciones de Solo Notificación (Notification-only)
```json
{
  "to": "TOKEN",
  "notification": {
    "title": "Título",
    "body": "Mensaje"
  }
}
```
- Se muestran automáticamente cuando la app está en segundo plano
- Manejadas por el sistema

### 2. Notificaciones de Solo Datos (Data-only)
```json
{
  "to": "TOKEN",
  "data": {
    "title": "Título",
    "body": "Mensaje",
    "custom_field": "valor"
  }
}
```
- Siempre pasan por `onMessageReceived()`
- Útiles para lógica personalizada

### 3. Notificaciones Combinadas
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

## 🎯 Características Implementadas

- ✅ Recepción de notificaciones push
- ✅ Manejo de notificaciones en primer plano y segundo plano
- ✅ Notificaciones locales de prueba
- ✅ Obtención y visualización del token FCM
- ✅ Canales de notificación (Android O+)
- ✅ Permisos de notificación (Android 13+)
- ✅ UI moderna con Jetpack Compose
- ✅ Copia del token al portapapeles
- ✅ Manejo de datos personalizados en notificaciones

## 🔧 Personalización

### Cambiar el Icono de Notificación

1. Crea un icono en `res/drawable/ic_notification.xml`
2. Actualiza en `MyFirebaseMessagingService.kt`:
```kotlin
.setSmallIcon(R.drawable.ic_notification)
```

### Cambiar el Color de Notificación

Actualiza en `AndroidManifest.xml`:
```xml
<meta-data
    android:name="com.google.firebase.messaging.default_notification_color"
    android:resource="@color/tu_color" />
```

### Agregar Sonido Personalizado

1. Coloca el archivo de sonido en `res/raw/`
2. Actualiza el canal de notificación:
```kotlin
channel.setSound(
    Uri.parse("android.resource://${packageName}/raw/tu_sonido"),
    null
)
```

## 🐛 Solución de Problemas

### No recibo notificaciones

1. **Verifica el token FCM:**
   - Asegúrate de que el token se muestra correctamente en la app
   - Copia el token exacto sin espacios adicionales

2. **Verifica los permisos:**
   - Android 13+: Asegúrate de aceptar el permiso de notificaciones
   - Verifica en Configuración → Apps → Notifications → Notificaciones

3. **Verifica google-services.json:**
   - El archivo debe estar en `app/google-services.json`
   - El package name debe coincidir: `com.example.notifications`

4. **Verifica la conexión a Internet:**
   - FCM requiere conexión a Internet para funcionar

5. **Revisa los logs:**
   ```bash
   adb logcat | grep -E "FCMService|MainActivity"
   ```

### La app se cierra al recibir notificaciones

- Verifica que el icono de notificación existe
- Revisa los logs de Android Studio para ver el error específico

### No aparece el token FCM

- Verifica que `google-services.json` esté correctamente configurado
- Asegúrate de tener conexión a Internet
- Revisa los logs para ver errores de Firebase

## 📚 Recursos Adicionales

- [Documentación oficial de FCM](https://firebase.google.com/docs/cloud-messaging)
- [Guía de Android](https://firebase.google.com/docs/cloud-messaging/android/client)
- [Mejores prácticas](https://firebase.google.com/docs/cloud-messaging/concept-options)
- [Consola de Firebase](https://console.firebase.google.com/)

## 📝 Notas Importantes

- **Seguridad:** Nunca compartas tu Server Key públicamente
- **Tokens:** Los tokens FCM pueden cambiar, implementa lógica para actualizarlos en tu backend
- **Batería:** Las notificaciones frecuentes pueden afectar la batería del dispositivo
- **Límites:** FCM tiene límites de tasa, revisa la documentación para producción

## 🎓 Para Aprender Más

1. Implementa notificaciones programadas con WorkManager
2. Agrega acciones a las notificaciones (botones)
3. Implementa notificaciones expandibles con imágenes
4. Crea grupos de notificaciones
5. Implementa notificaciones de progreso
6. Integra con un backend real para gestionar tokens

---

**¡Listo!** 🎉 Ahora tienes una implementación completa de Firebase Cloud Messaging en tu app Android.
