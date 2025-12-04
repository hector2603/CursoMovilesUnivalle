# 🚀 Inicio Rápido - Notificaciones Push con Firebase

## ⚡ Pasos Mínimos para Empezar

### 1️⃣ Configurar Firebase (5 minutos)

1. **Ve a Firebase Console:**
   - Abre [https://console.firebase.google.com/](https://console.firebase.google.com/)
   - Inicia sesión con tu cuenta de Google

2. **Crea un Proyecto:**
   - Clic en "Agregar proyecto"
   - Nombre: `Notifications` (o el que prefieras)
   - Desactiva Google Analytics si quieres ir más rápido
   - Clic en "Crear proyecto"

3. **Agrega tu App Android:**
   - Clic en el ícono de Android (robot verde)
   - Package name: `com.example.notifications`
   - Apodo: `Notifications App`
   - Clic en "Registrar app"

4. **Descarga google-services.json:**
   - Descarga el archivo
   - Colócalo en: `Notifications/app/google-services.json`
   - **IMPORTANTE:** Debe estar dentro de la carpeta `app/`

5. **Listo con Firebase:**
   - Puedes omitir los siguientes pasos de Firebase
   - El proyecto ya tiene todo configurado

### 2️⃣ Ejecutar la App (2 minutos)

1. **Abre el proyecto en Android Studio**

2. **Sincroniza Gradle:**
   - Android Studio mostrará una barra amarilla
   - Clic en "Sync Now"
   - Espera a que termine

3. **Ejecuta la app:**
   - Conecta un dispositivo físico o inicia un emulador
   - Clic en el botón ▶️ (Run)
   - Acepta los permisos de notificación

### 3️⃣ Probar Notificaciones (1 minuto)

#### Opción A: Notificación Local (Sin Firebase)
1. Abre la app
2. Clic en "Enviar Notificación Local"
3. ¡Verás una notificación! 🎉

#### Opción B: Notificación desde Firebase
1. En la app, clic en "Copiar Token"
2. Ve a Firebase Console → Cloud Messaging
3. Clic en "Enviar tu primer mensaje"
4. Escribe un título y mensaje
5. Clic en "Enviar mensaje de prueba"
6. Pega el token que copiaste
7. Clic en "Probar"
8. ¡Recibirás la notificación! 🎉

## 📱 Funcionalidades de la App

La app muestra:
- ✅ Tu token FCM (para enviar notificaciones)
- ✅ Botón para copiar el token
- ✅ Botón para probar notificaciones locales
- ✅ Estado de permisos
- ✅ Instrucciones de uso

## 🔔 Enviar Notificaciones

### Desde Firebase Console (Más Fácil)
1. Firebase Console → Cloud Messaging
2. "Enviar tu primer mensaje"
3. Completa título y mensaje
4. "Enviar mensaje de prueba"
5. Pega el token de tu dispositivo
6. "Probar"

### Con cURL (Para Desarrolladores)
```bash
# Obtén tu Server Key de Firebase Console → Configuración → Cloud Messaging

curl -X POST https://fcm.googleapis.com/fcm/send \
  -H "Authorization: key=TU_SERVER_KEY_AQUI" \
  -H "Content-Type: application/json" \
  -d '{
    "to": "TOKEN_DEL_DISPOSITIVO",
    "notification": {
      "title": "¡Hola!",
      "body": "Esta es una notificación de prueba"
    }
  }'
```

### Con Postman (Para Desarrolladores)
**POST** `https://fcm.googleapis.com/fcm/send`

**Headers:**
```
Authorization: key=TU_SERVER_KEY
Content-Type: application/json
```

**Body:**
```json
{
  "to": "TOKEN_DEL_DISPOSITIVO",
  "notification": {
    "title": "¡Hola desde Postman!",
    "body": "Notificación de prueba"
  }
}
```

## ❓ Problemas Comunes

### ❌ "No aparece el token FCM"
**Solución:**
- Verifica que `google-services.json` esté en `app/`
- Asegúrate de tener Internet
- Sincroniza Gradle de nuevo

### ❌ "No recibo notificaciones"
**Solución:**
- Acepta los permisos cuando la app lo pida
- Verifica que el token sea correcto (sin espacios)
- Asegúrate de que la app tenga Internet

### ❌ "Error al compilar"
**Solución:**
1. File → Invalidate Caches → Invalidate and Restart
2. Build → Clean Project
3. Build → Rebuild Project

### ❌ "google-services.json not found"
**Solución:**
- El archivo debe estar en `app/google-services.json`
- NO en la raíz del proyecto
- Verifica la ubicación exacta

## 📚 ¿Quieres Aprender Más?

- **Guía Completa:** Lee [FIREBASE_SETUP.md](FIREBASE_SETUP.md)
- **Documentación:** [README.md](README.md)
- **Firebase Docs:** [firebase.google.com/docs/cloud-messaging](https://firebase.google.com/docs/cloud-messaging)

## 🎯 Próximos Pasos

Una vez que funcione, puedes:
1. ✨ Personalizar el icono de notificación
2. 🎨 Cambiar los colores
3. 🔊 Agregar sonidos personalizados
4. 📸 Agregar imágenes a las notificaciones
5. 🔘 Agregar botones de acción
6. 🌐 Conectar con tu propio backend

## 💡 Consejo Pro

**Para desarrollo rápido:**
1. Usa "Enviar Notificación Local" para probar la UI
2. Usa Firebase Console para probar notificaciones reales
3. Usa Postman/cURL cuando integres con tu backend

---

**¿Listo?** ¡Empieza con el paso 1! 🚀

**¿Problemas?** Revisa la sección de problemas comunes arriba ⬆️
