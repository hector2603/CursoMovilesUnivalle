# ✅ Checklist de Configuración

Usa esta lista para verificar que todo esté configurado correctamente.

## 📋 Antes de Ejecutar la App

### 1. Configuración de Firebase
- [ ] Creaste un proyecto en [Firebase Console](https://console.firebase.google.com/)
- [ ] Agregaste una app Android con package name `com.example.notifications`
- [ ] Descargaste el archivo `google-services.json`
- [ ] Colocaste `google-services.json` en la carpeta `app/` (no en la raíz)
- [ ] El archivo está en la ubicación correcta: `Notifications/app/google-services.json`

### 2. Configuración del Proyecto
- [ ] Abriste el proyecto en Android Studio
- [ ] Sincronizaste Gradle (Sync Project with Gradle Files)
- [ ] No hay errores de compilación
- [ ] Las dependencias de Firebase se descargaron correctamente

### 3. Dispositivo/Emulador
- [ ] Tienes un dispositivo físico conectado O un emulador iniciado
- [ ] El dispositivo/emulador tiene Google Play Services instalado
- [ ] El dispositivo/emulador tiene conexión a Internet

## 🚀 Al Ejecutar la App

### Primera Ejecución
- [ ] La app se instaló correctamente
- [ ] Apareció el diálogo de permisos de notificación
- [ ] Aceptaste los permisos de notificación
- [ ] La app muestra la pantalla principal

### Verificación de Funcionalidad
- [ ] Se muestra el token FCM (no dice "Error al obtener el token")
- [ ] El token es una cadena larga de caracteres
- [ ] El botón "Copiar Token" funciona
- [ ] El estado de permisos muestra "✅ Permisos concedidos"

### Prueba de Notificación Local
- [ ] Hiciste clic en "Enviar Notificación Local"
- [ ] Apareció una notificación en la barra de notificaciones
- [ ] Al tocar la notificación, se abre la app

### Prueba de Notificación FCM
- [ ] Copiaste el token FCM
- [ ] Fuiste a Firebase Console → Cloud Messaging
- [ ] Enviaste un mensaje de prueba con el token
- [ ] Recibiste la notificación en el dispositivo

## 🔍 Verificación de Archivos

### Archivos de Código
- [ ] `MainActivity.kt` - Actividad principal ✅
- [ ] `MyFirebaseMessagingService.kt` - Servicio FCM ✅
- [ ] `NotificationHelper.kt` - Utilidades ✅
- [ ] `AndroidManifest.xml` - Configurado con permisos y servicio ✅

### Archivos de Configuración
- [ ] `build.gradle.kts` (proyecto) - Plugin de Google Services ✅
- [ ] `build.gradle.kts` (app) - Dependencias de Firebase ✅
- [ ] `libs.versions.toml` - Versiones de Firebase ✅
- [ ] `google-services.json` - Archivo de configuración de Firebase ⚠️ (debes agregarlo)

### Archivos de Documentación
- [ ] `README.md` - Documentación principal ✅
- [ ] `FIREBASE_SETUP.md` - Guía de configuración detallada ✅
- [ ] `INICIO_RAPIDO.md` - Guía de inicio rápido ✅
- [ ] `EJEMPLOS_ENVIO.md` - Ejemplos de código ✅
- [ ] `CHECKLIST.md` - Este archivo ✅

## 🐛 Solución de Problemas

### ❌ No aparece el token FCM

**Posibles causas:**
- [ ] `google-services.json` no está en la ubicación correcta
- [ ] No hay conexión a Internet
- [ ] Google Play Services no está actualizado
- [ ] El package name no coincide

**Soluciones:**
1. Verifica que `google-services.json` esté en `app/`
2. Verifica la conexión a Internet
3. Actualiza Google Play Services
4. Verifica que el package name sea `com.example.notifications`

### ❌ No recibo notificaciones

**Posibles causas:**
- [ ] Permisos de notificación no concedidos
- [ ] Token incorrecto o con espacios
- [ ] App cerrada completamente (en algunos dispositivos)
- [ ] Optimización de batería activa

**Soluciones:**
1. Ve a Configuración → Apps → Notifications → Permisos → Notificaciones
2. Copia el token de nuevo sin espacios
3. Deja la app en segundo plano (no la cierres completamente)
4. Desactiva la optimización de batería para la app

### ❌ Error al compilar

**Posibles causas:**
- [ ] Gradle no sincronizado
- [ ] Caché corrupto
- [ ] Dependencias no descargadas

**Soluciones:**
1. File → Sync Project with Gradle Files
2. Build → Clean Project
3. Build → Rebuild Project
4. File → Invalidate Caches → Invalidate and Restart

### ❌ "google-services.json not found"

**Soluciones:**
1. Verifica la ubicación: debe estar en `app/google-services.json`
2. NO debe estar en la raíz del proyecto
3. Verifica que el nombre sea exactamente `google-services.json`
4. Sincroniza Gradle después de agregarlo

## 📊 Verificación de Logs

### Ver logs en Android Studio
1. Abre la pestaña "Logcat"
2. Filtra por "FCM" o "MainActivity"
3. Busca mensajes como:
   - "Token FCM: ..." (indica que el token se obtuvo)
   - "Mensaje recibido de: ..." (indica que llegó una notificación)
   - "Notificación mostrada: ..." (indica que se mostró la notificación)

### Ver logs en Terminal
```bash
# Ver todos los logs relacionados con FCM
adb logcat | grep -E "FCM|MainActivity|FirebaseMessaging"

# Ver solo errores
adb logcat *:E | grep -E "FCM|Firebase"
```

## 🎯 Checklist de Funcionalidades

### Funcionalidades Básicas
- [ ] Obtención del token FCM
- [ ] Visualización del token en la UI
- [ ] Copia del token al portapapeles
- [ ] Notificaciones locales de prueba
- [ ] Recepción de notificaciones FCM
- [ ] Manejo de permisos de Android 13+

### Funcionalidades Avanzadas (Opcional)
- [ ] Notificaciones con datos personalizados
- [ ] Notificaciones con imágenes
- [ ] Notificaciones con acciones (botones)
- [ ] Notificaciones agrupadas
- [ ] Notificaciones programadas
- [ ] Integración con backend

## 📱 Pruebas Recomendadas

### Pruebas Básicas
1. [ ] Notificación local desde la app
2. [ ] Notificación FCM desde Firebase Console
3. [ ] Notificación con la app en primer plano
4. [ ] Notificación con la app en segundo plano
5. [ ] Notificación con la app cerrada

### Pruebas Avanzadas
1. [ ] Notificación con datos personalizados
2. [ ] Múltiples notificaciones
3. [ ] Notificación mientras el dispositivo está bloqueado
4. [ ] Tocar la notificación para abrir la app
5. [ ] Descartar la notificación

## 🔐 Seguridad

### Verificaciones de Seguridad
- [ ] `google-services.json` está en `.gitignore`
- [ ] No compartiste tu Server Key públicamente
- [ ] No hardcodeaste credenciales en el código
- [ ] Usas HTTPS para comunicaciones con tu backend

## 📚 Recursos Consultados

- [ ] Leí el [README.md](README.md)
- [ ] Leí el [FIREBASE_SETUP.md](FIREBASE_SETUP.md)
- [ ] Leí el [INICIO_RAPIDO.md](INICIO_RAPIDO.md)
- [ ] Revisé los [EJEMPLOS_ENVIO.md](EJEMPLOS_ENVIO.md)
- [ ] Consulté la [documentación oficial de FCM](https://firebase.google.com/docs/cloud-messaging)

## ✨ Próximos Pasos

Una vez que todo funcione:
- [ ] Personaliza el icono de notificación
- [ ] Cambia los colores del tema
- [ ] Agrega sonidos personalizados
- [ ] Implementa notificaciones con imágenes
- [ ] Agrega botones de acción a las notificaciones
- [ ] Integra con tu propio backend
- [ ] Implementa análisis de notificaciones
- [ ] Agrega notificaciones programadas

## 🎓 Aprendizaje

### Conceptos Aprendidos
- [ ] Qué es Firebase Cloud Messaging
- [ ] Cómo funcionan las notificaciones push
- [ ] Diferencia entre notificaciones y datos
- [ ] Manejo de permisos en Android
- [ ] Canales de notificación
- [ ] Ciclo de vida de las notificaciones

### Habilidades Desarrolladas
- [ ] Configuración de Firebase en Android
- [ ] Implementación de servicios en Android
- [ ] Manejo de notificaciones
- [ ] UI con Jetpack Compose
- [ ] Debugging de apps Android
- [ ] Integración de SDKs externos

---

## 📝 Notas

Usa este espacio para anotar problemas encontrados o soluciones:

```
[Escribe aquí tus notas]
```

---

**Estado del Proyecto:** 
- [ ] ✅ Todo funciona correctamente
- [ ] ⚠️ Funciona parcialmente (especifica qué falta)
- [ ] ❌ No funciona (revisa la sección de solución de problemas)

**Fecha de última verificación:** _____________

**Dispositivo de prueba:** _____________

**Versión de Android:** _____________

---

¿Completaste todos los checks? ¡Felicidades! 🎉 Tu app de notificaciones está lista.

¿Tienes problemas? Revisa la sección de solución de problemas o consulta la documentación.
