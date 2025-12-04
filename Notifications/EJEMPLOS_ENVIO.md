# 📤 Ejemplos de Envío de Notificaciones

Esta guía contiene ejemplos prácticos para enviar notificaciones push desde diferentes plataformas y lenguajes.

## 🔑 Obtener Credenciales

### Server Key (Legacy)
1. Firebase Console → Configuración del proyecto (⚙️)
2. Cloud Messaging
3. Copia la "Clave del servidor" (Server Key)

### Service Account (Recomendado)
1. Firebase Console → Configuración del proyecto (⚙️)
2. Cuentas de servicio
3. Generar nueva clave privada
4. Descarga el archivo JSON

## 📱 Obtener el Token del Dispositivo

El token se muestra en la pantalla principal de la app. Usa el botón "Copiar Token" para copiarlo al portapapeles.

---

## 🌐 Ejemplos por Plataforma

### 1. cURL (Terminal)

#### Notificación Simple
```bash
curl -X POST https://fcm.googleapis.com/fcm/send \
  -H "Authorization: key=TU_SERVER_KEY" \
  -H "Content-Type: application/json" \
  -d '{
    "to": "TOKEN_DEL_DISPOSITIVO",
    "notification": {
      "title": "Título de la notificación",
      "body": "Mensaje de la notificación"
    }
  }'
```

#### Con Datos Personalizados
```bash
curl -X POST https://fcm.googleapis.com/fcm/send \
  -H "Authorization: key=TU_SERVER_KEY" \
  -H "Content-Type: application/json" \
  -d '{
    "to": "TOKEN_DEL_DISPOSITIVO",
    "notification": {
      "title": "Nueva Orden",
      "body": "Tienes una nueva orden #12345"
    },
    "data": {
      "order_id": "12345",
      "screen": "order_details",
      "priority": "high"
    }
  }'
```

#### Notificación a Múltiples Dispositivos
```bash
curl -X POST https://fcm.googleapis.com/fcm/send \
  -H "Authorization: key=TU_SERVER_KEY" \
  -H "Content-Type: application/json" \
  -d '{
    "registration_ids": [
      "TOKEN_DISPOSITIVO_1",
      "TOKEN_DISPOSITIVO_2",
      "TOKEN_DISPOSITIVO_3"
    ],
    "notification": {
      "title": "Mensaje Grupal",
      "body": "Este mensaje va a varios dispositivos"
    }
  }'
```

---

### 2. Node.js

#### Instalación
```bash
npm install firebase-admin
```

#### Código
```javascript
const admin = require('firebase-admin');

// Inicializar Firebase Admin SDK
const serviceAccount = require('./path/to/serviceAccountKey.json');

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

// Función para enviar notificación
async function sendNotification(token, title, body, data = {}) {
  const message = {
    notification: {
      title: title,
      body: body
    },
    data: data,
    token: token
  };

  try {
    const response = await admin.messaging().send(message);
    console.log('Notificación enviada exitosamente:', response);
    return response;
  } catch (error) {
    console.error('Error al enviar notificación:', error);
    throw error;
  }
}

// Ejemplo de uso
const deviceToken = 'TOKEN_DEL_DISPOSITIVO';
sendNotification(
  deviceToken,
  '¡Hola desde Node.js!',
  'Esta es una notificación de prueba',
  { custom_key: 'custom_value' }
);
```

#### Con Express.js (API REST)
```javascript
const express = require('express');
const admin = require('firebase-admin');

const app = express();
app.use(express.json());

// Inicializar Firebase
const serviceAccount = require('./serviceAccountKey.json');
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

// Endpoint para enviar notificación
app.post('/send-notification', async (req, res) => {
  const { token, title, body, data } = req.body;

  const message = {
    notification: { title, body },
    data: data || {},
    token: token
  };

  try {
    const response = await admin.messaging().send(message);
    res.json({ success: true, messageId: response });
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});

app.listen(3000, () => {
  console.log('Servidor corriendo en puerto 3000');
});
```

---

### 3. Python

#### Instalación
```bash
pip install firebase-admin
```

#### Código
```python
import firebase_admin
from firebase_admin import credentials, messaging

# Inicializar Firebase Admin SDK
cred = credentials.Certificate('path/to/serviceAccountKey.json')
firebase_admin.initialize_app(cred)

def send_notification(token, title, body, data=None):
    """Envía una notificación push a un dispositivo específico"""
    
    message = messaging.Message(
        notification=messaging.Notification(
            title=title,
            body=body,
        ),
        data=data or {},
        token=token,
    )
    
    try:
        response = messaging.send(message)
        print(f'Notificación enviada exitosamente: {response}')
        return response
    except Exception as e:
        print(f'Error al enviar notificación: {e}')
        raise

# Ejemplo de uso
device_token = 'TOKEN_DEL_DISPOSITIVO'
send_notification(
    device_token,
    '¡Hola desde Python!',
    'Esta es una notificación de prueba',
    {'custom_key': 'custom_value'}
)
```

#### Con Flask (API REST)
```python
from flask import Flask, request, jsonify
import firebase_admin
from firebase_admin import credentials, messaging

app = Flask(__name__)

# Inicializar Firebase
cred = credentials.Certificate('serviceAccountKey.json')
firebase_admin.initialize_app(cred)

@app.route('/send-notification', methods=['POST'])
def send_notification():
    data = request.json
    token = data.get('token')
    title = data.get('title')
    body = data.get('body')
    custom_data = data.get('data', {})
    
    message = messaging.Message(
        notification=messaging.Notification(title=title, body=body),
        data=custom_data,
        token=token,
    )
    
    try:
        response = messaging.send(message)
        return jsonify({'success': True, 'messageId': response})
    except Exception as e:
        return jsonify({'success': False, 'error': str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True, port=3000)
```

---

### 4. PHP

#### Instalación
```bash
composer require kreait/firebase-php
```

#### Código
```php
<?php
require 'vendor/autoload.php';

use Kreait\Firebase\Factory;
use Kreait\Firebase\Messaging\CloudMessage;

// Inicializar Firebase
$factory = (new Factory)->withServiceAccount('path/to/serviceAccountKey.json');
$messaging = $factory->createMessaging();

// Función para enviar notificación
function sendNotification($token, $title, $body, $data = []) {
    global $messaging;
    
    $message = CloudMessage::withTarget('token', $token)
        ->withNotification([
            'title' => $title,
            'body' => $body,
        ])
        ->withData($data);
    
    try {
        $response = $messaging->send($message);
        echo "Notificación enviada exitosamente: " . $response . "\n";
        return $response;
    } catch (Exception $e) {
        echo "Error al enviar notificación: " . $e->getMessage() . "\n";
        throw $e;
    }
}

// Ejemplo de uso
$deviceToken = 'TOKEN_DEL_DISPOSITIVO';
sendNotification(
    $deviceToken,
    '¡Hola desde PHP!',
    'Esta es una notificación de prueba',
    ['custom_key' => 'custom_value']
);
?>
```

---

### 5. Java (Spring Boot)

#### Dependencias (pom.xml)
```xml
<dependency>
    <groupId>com.google.firebase</groupId>
    <artifactId>firebase-admin</artifactId>
    <version>9.2.0</version>
</dependency>
```

#### Código
```java
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {

    public NotificationService() throws IOException {
        // Inicializar Firebase
        FileInputStream serviceAccount = 
            new FileInputStream("path/to/serviceAccountKey.json");

        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }

    public String sendNotification(String token, String title, 
                                   String body, Map<String, String> data) {
        try {
            Message message = Message.builder()
                .setNotification(Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build())
                .putAllData(data != null ? data : new HashMap<>())
                .setToken(token)
                .build();

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Notificación enviada: " + response);
            return response;
        } catch (FirebaseMessagingException e) {
            System.err.println("Error al enviar notificación: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
```

#### Controller REST
```java
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public Map<String, Object> sendNotification(@RequestBody NotificationRequest request) {
        try {
            String messageId = notificationService.sendNotification(
                request.getToken(),
                request.getTitle(),
                request.getBody(),
                request.getData()
            );
            return Map.of("success", true, "messageId", messageId);
        } catch (Exception e) {
            return Map.of("success", false, "error", e.getMessage());
        }
    }
}
```

---

### 6. Postman

#### Configuración
1. **Método:** POST
2. **URL:** `https://fcm.googleapis.com/fcm/send`
3. **Headers:**
   - `Authorization: key=TU_SERVER_KEY`
   - `Content-Type: application/json`

#### Body (Notificación Simple)
```json
{
  "to": "TOKEN_DEL_DISPOSITIVO",
  "notification": {
    "title": "Título desde Postman",
    "body": "Mensaje desde Postman"
  }
}
```

#### Body (Con Datos y Prioridad)
```json
{
  "to": "TOKEN_DEL_DISPOSITIVO",
  "priority": "high",
  "notification": {
    "title": "Notificación Importante",
    "body": "Este mensaje tiene alta prioridad",
    "sound": "default",
    "badge": "1"
  },
  "data": {
    "screen": "profile",
    "user_id": "12345",
    "action": "view_profile"
  }
}
```

---

## 🎯 Tipos de Notificaciones

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
- Se muestra automáticamente en la barra de notificaciones
- Funciona cuando la app está en segundo plano

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
- Siempre pasa por `onMessageReceived()`
- Útil para lógica personalizada

### 3. Notificación + Datos
```json
{
  "to": "TOKEN",
  "notification": {
    "title": "Título",
    "body": "Mensaje"
  },
  "data": {
    "screen": "details",
    "id": "123"
  }
}
```
- Combina ambos enfoques
- Recomendado para la mayoría de casos

---

## 🔔 Opciones Avanzadas

### Notificación con Imagen
```json
{
  "to": "TOKEN",
  "notification": {
    "title": "Nueva Foto",
    "body": "Alguien compartió una foto",
    "image": "https://ejemplo.com/imagen.jpg"
  }
}
```

### Notificación Silenciosa
```json
{
  "to": "TOKEN",
  "data": {
    "silent": "true",
    "action": "sync_data"
  },
  "priority": "high"
}
```

### Con Tiempo de Vida (TTL)
```json
{
  "to": "TOKEN",
  "notification": {
    "title": "Oferta Limitada",
    "body": "Solo por 1 hora"
  },
  "time_to_live": 3600
}
```

---

## 📊 Respuestas del Servidor

### Éxito
```json
{
  "multicast_id": 123456789,
  "success": 1,
  "failure": 0,
  "canonical_ids": 0,
  "results": [
    {
      "message_id": "0:1234567890123456%abcdef"
    }
  ]
}
```

### Error
```json
{
  "multicast_id": 123456789,
  "success": 0,
  "failure": 1,
  "canonical_ids": 0,
  "results": [
    {
      "error": "InvalidRegistration"
    }
  ]
}
```

### Errores Comunes
- `InvalidRegistration`: Token inválido
- `NotRegistered`: Token ya no es válido
- `MismatchSenderId`: Token no pertenece a este proyecto
- `MessageTooBig`: Mensaje excede 4KB

---

## 💡 Mejores Prácticas

1. **Almacena los tokens de forma segura** en tu base de datos
2. **Maneja tokens expirados** eliminándolos de tu BD
3. **Usa prioridad alta** solo cuando sea necesario
4. **Limita el tamaño** de los datos (máx 4KB)
5. **Implementa reintentos** con backoff exponencial
6. **Monitorea las tasas de entrega** en Firebase Console

---

## 🧪 Herramientas de Prueba

- **Firebase Console:** Interfaz gráfica fácil de usar
- **Postman:** Ideal para pruebas manuales
- **cURL:** Rápido para pruebas en terminal
- **FCM Toolbox:** App Android para pruebas

---

¿Necesitas más ejemplos? Revisa la [documentación oficial de FCM](https://firebase.google.com/docs/cloud-messaging).
