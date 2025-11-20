# 📱 Guía Completa de Testing en Android

Una guía práctica para aprender testing en Android con Jetpack Compose, MVVM y Kotlin.

## 📋 Tabla de Contenidos

- [🏗️ Arquitectura del Proyecto](#️-arquitectura-del-proyecto)
- [🚀 Comandos de Testing](#-comandos-de-testing)
- [🧪 Unit Tests](#-unit-tests)
- [📱 Instrumentation Tests](#-instrumentation-tests)
- [🎯 Ejercicio Práctico](#-ejercicio-práctico)
- [📊 Comparación de Tests](#-comparación-de-tests)
- [🛠️ Configuración](#️-configuración)
- [📚 Recursos Adicionales](#-recursos-adicionales)

---

## 🏗️ Arquitectura del Proyecto

Este proyecto implementa una arquitectura **MVVM** con las siguientes capas:

```
app/src/
├── main/java/com/example/test/
│   ├── data/
│   │   ├── model/          # Modelos de datos
│   │   └── repository/     # Repositorios
│   ├── presentation/
│   │   ├── screen/         # Pantallas UI (Compose)
│   │   └── viewmodel/      # ViewModels
│   └── navigation/         # Navegación
├── test/java/              # Unit Tests
└── androidTest/java/       # Instrumentation Tests
```

### 🎨 Tecnologías Utilizadas

- **Jetpack Compose** - UI moderna
- **Navigation Compose** - Navegación
- **ViewModel** - Gestión de estado
- **StateFlow** - Programación reactiva
- **Coroutines** - Programación asíncrona
- **JUnit** - Framework de testing
- **Mockito** - Mocking para tests
- **Compose Testing** - Testing de UI

---

## 🚀 Comandos de Testing

### 1. Pruebas Unitarias (Unit Tests)

```bash
# Ejecutar todas las pruebas unitarias
./gradlew test

# Ejecutar solo las pruebas unitarias de debug
./gradlew testDebugUnitTest

# Ejecutar solo las pruebas unitarias de release
./gradlew testReleaseUnitTest

# Ejecutar con información detallada
./gradlew testDebugUnitTest --info
```

### 2. Pruebas de Instrumentación (Android Tests / UI Tests)

```bash
# Ejecutar todas las pruebas de instrumentación
./gradlew connectedAndroidTest

# Ejecutar solo las pruebas de instrumentación de debug
./gradlew connectedDebugAndroidTest

# Ejecutar con información detallada
./gradlew connectedDebugAndroidTest --info
```

### 3. Comandos Combinados

```bash
# Ejecutar TODAS las pruebas (unitarias + instrumentación)
./gradlew check

# Limpiar y ejecutar todas las pruebas
./gradlew clean check

# Solo compilar las pruebas sin ejecutarlas
./gradlew compileDebugUnitTestKotlin
./gradlew compileDebugAndroidTestKotlin
```

### 4. Comandos Específicos del Proyecto

```bash
# Ejecutar pruebas unitarias específicas
./gradlew :app:testDebugUnitTest

# Ejecutar pruebas de instrumentación específicas
./gradlew :app:connectedDebugAndroidTest

# Ver reporte de cobertura (si está configurado)
./gradlew jacocoTestReport
```

---

## 📊 Comparación de Tests

| **Unit Tests** | **Instrumentation Tests** |
|----------------|---------------------------|
| Se ejecutan en tu PC | Se ejecutan en dispositivo Android |
| Prueban lógica aislada | Prueban la app completa |
| Muy rápidos (segundos) | Más lentos (minutos) |
| No necesitan dispositivo | Necesitan dispositivo/emulador |
| Prueban ViewModels, Repository | Prueban UI, navegación, flujos |

---

## 🧪 Unit Tests

### 1. Conceptos Básicos

#### ¿Qué es un Unit Test?

```kotlin
@Test
fun suma_dosNumeros_devuelveResultadoCorrecto() {
    // Arrange (Preparar)
    val numero1 = 2
    val numero2 = 3
    
    // Act (Actuar)
    val resultado = numero1 + numero2
    
    // Assert (Verificar)
    assertEquals(5, resultado)
}
```

### 2. Asserts Principales

#### Asserts Básicos:

```kotlin
@Test
fun ejemplos_asserts_basicos() {
    // Verificar igualdad
    assertEquals(4, 2 + 2)
    assertEquals("Hola", "Ho" + "la")
    
    // Verificar verdadero/falso
    assertTrue(5 > 3)
    assertFalse(2 > 5)
    
    // Verificar nulos
    assertNull(null)
    assertNotNull("texto")
    
    // Verificar que algo falla
    assertThrows(IllegalArgumentException::class.java) {
        throw IllegalArgumentException("Error")
    }
}
```

#### Asserts para Colecciones:

```kotlin
@Test
fun ejemplos_asserts_colecciones() {
    val lista = listOf("a", "b", "c")
    
    // Verificar tamaño
    assertEquals(3, lista.size)
    
    // Verificar contenido
    assertTrue(lista.contains("b"))
    assertFalse(lista.isEmpty())
    
    // Verificar primer/último elemento
    assertEquals("a", lista.first())
    assertEquals("c", lista.last())
}
```

### 3. Mocks - ¿Qué son y por qué?

#### ❌ Sin Mock (Problemático):

```kotlin
class UserService {
    fun getUser(): User {
        // Llama a una API real - LENTO y puede fallar
        return apiClient.fetchUser()
    }
}

@Test
fun test_sin_mock() {
    val service = UserService()
    // ❌ Problema: depende de internet, puede ser lento
    val user = service.getUser()
}
```

#### ✅ Con Mock (Correcto):

```kotlin
@Test
fun test_con_mock() {
    // Crear un mock (objeto falso)
    val mockApiClient = mock<ApiClient>()
    
    // Definir qué debe devolver el mock
    whenever(mockApiClient.fetchUser()).thenReturn(
        User(id = 1, name = "Juan")
    )
    
    // Usar el mock en la prueba
    val service = UserService(mockApiClient)
    val user = service.getUser()
    
    // Verificar resultado
    assertEquals("Juan", user.name)
    
    // Verificar que se llamó al método
    verify(mockApiClient).fetchUser()
}
```

### 4. Ejemplo Completo: Testing de ViewModel

```kotlin
class CounterViewModel : ViewModel() {
    private val _counter = MutableStateFlow(0)
    val counter: StateFlow<Int> = _counter.asStateFlow()
    
    fun increment() {
        _counter.value += 1
    }
    
    fun reset() {
        _counter.value = 0
    }
}

// Tests del ViewModel
class CounterViewModelTest {
    private lateinit var viewModel: CounterViewModel
    
    @Before
    fun setup() {
        viewModel = CounterViewModel()
    }
    
    @Test
    fun `contador inicial es cero`() {
        assertEquals(0, viewModel.counter.value)
    }
    
    @Test
    fun `increment aumenta contador en uno`() {
        viewModel.increment()
        assertEquals(1, viewModel.counter.value)
        
        viewModel.increment()
        assertEquals(2, viewModel.counter.value)
    }
    
    @Test
    fun `reset pone contador en cero`() {
        viewModel.increment()
        viewModel.increment()
        assertEquals(2, viewModel.counter.value)
        
        viewModel.reset()
        assertEquals(0, viewModel.counter.value)
    }
}
```

### 5. Testing con Corrutinas

```kotlin
class DataRepository {
    suspend fun loadData(): List<String> {
        delay(1000) // Simula operación lenta
        return listOf("dato1", "dato2")
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class DataRepositoryTest {
    private val testDispatcher = StandardTestDispatcher()
    
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    
    @Test
    fun `loadData devuelve lista correcta`() = runTest {
        val repository = DataRepository()
        
        val result = repository.loadData()
        
        assertEquals(2, result.size)
        assertEquals("dato1", result[0])
    }
}
```

---

## 📱 Instrumentation Tests

### 1. Conceptos Básicos

#### Estructura Básica:

```kotlin
@RunWith(AndroidJUnit4::class)
class MiPrimeraUITest {
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun mi_primera_prueba_ui() {
        composeTestRule.setContent {
            Text("Hola Mundo")
        }
        
        // Verificar que el texto aparece
        composeTestRule
            .onNodeWithText("Hola Mundo")
            .assertIsDisplayed()
    }
}
```

### 2. Encontrar Elementos en la UI

```kotlin
@Test
fun ejemplos_encontrar_elementos() {
    composeTestRule.setContent {
        Column {
            Text("Título")
            Button(onClick = {}) { Text("Hacer clic") }
            TextField(
                value = "",
                onValueChange = {},
                label = { Text("Nombre") }
            )
        }
    }
    
    // Por texto
    composeTestRule.onNodeWithText("Título")
    
    // Por descripción de contenido
    composeTestRule.onNodeWithContentDescription("Botón principal")
    
    // Por etiqueta de test
    composeTestRule.onNodeWithTag("mi-boton")
    
    // Múltiples elementos
    composeTestRule.onAllNodesWithText("Botón")
}
```

### 3. Interacciones Básicas

```kotlin
@Test
fun ejemplos_interacciones() {
    composeTestRule.setContent {
        var contador by remember { mutableStateOf(0) }
        
        Column {
            Text("Contador: $contador")
            Button(onClick = { contador++ }) {
                Text("Incrementar")
            }
        }
    }
    
    // Hacer clic
    composeTestRule
        .onNodeWithText("Incrementar")
        .performClick()
    
    // Verificar resultado
    composeTestRule
        .onNodeWithText("Contador: 1")
        .assertIsDisplayed()
    
    // Escribir texto
    composeTestRule
        .onNodeWithText("Nombre")
        .performTextInput("Juan")
    
    // Deslizar
    composeTestRule
        .onNodeWithTag("lista")
        .performScrollToIndex(5)
}
```

### 4. Asserts para UI

```kotlin
@Test
fun ejemplos_asserts_ui() {
    composeTestRule.setContent {
        Button(
            onClick = {},
            enabled = false
        ) {
            Text("Botón Deshabilitado")
        }
    }
    
    composeTestRule
        .onNodeWithText("Botón Deshabilitado")
        .assertIsDisplayed()        // Está visible
        .assertIsNotEnabled()       // Está deshabilitado
        .assertHasClickAction()     // Tiene acción de clic
}
```

---

## 🎯 Ejercicio Práctico

### 1. Crear la Vista (SimpleCounterScreen.kt)

```kotlin
@Composable
fun SimpleCounterScreen() {
    var counter by remember { mutableStateOf(0) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Contador: $counter",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.testTag("counter-text")
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { counter-- },
                modifier = Modifier.testTag("decrease-button")
            ) {
                Text("-")
            }
            
            Button(
                onClick = { counter = 0 },
                modifier = Modifier.testTag("reset-button")
            ) {
                Text("Reset")
            }
            
            Button(
                onClick = { counter++ },
                modifier = Modifier.testTag("increase-button")
            ) {
                Text("+")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (counter > 5) {
            Text(
                text = "¡Contador alto!",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.testTag("high-counter-message")
            )
        }
    }
}
```

### 2. Crear los Tests (SimpleCounterScreenTest.kt)

```kotlin
@RunWith(AndroidJUnit4::class)
class SimpleCounterScreenTest {
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun contador_muestra_valor_inicial_cero() {
        // Arrange & Act
        composeTestRule.setContent {
            SimpleCounterScreen()
        }
        
        // Assert
        composeTestRule
            .onNodeWithTag("counter-text")
            .assertIsDisplayed()
            .assertTextContains("Contador: 0")
    }
    
    @Test
    fun boton_incrementar_aumenta_contador() {
        composeTestRule.setContent {
            SimpleCounterScreen()
        }
        
        // Hacer clic en incrementar
        composeTestRule
            .onNodeWithTag("increase-button")
            .performClick()
        
        // Verificar que cambió
        composeTestRule
            .onNodeWithTag("counter-text")
            .assertTextContains("Contador: 1")
    }
    
    @Test
    fun flujo_completo_usuario() {
        composeTestRule.setContent {
            SimpleCounterScreen()
        }
        
        // 1. Verificar estado inicial
        composeTestRule
            .onNodeWithTag("counter-text")
            .assertTextContains("Contador: 0")
        
        // 2. Incrementar 3 veces
        repeat(3) {
            composeTestRule
                .onNodeWithTag("increase-button")
                .performClick()
        }
        
        // 3. Decrementar 1 vez
        composeTestRule
            .onNodeWithTag("decrease-button")
            .performClick()
        
        // 4. Verificar resultado intermedio
        composeTestRule
            .onNodeWithTag("counter-text")
            .assertTextContains("Contador: 2")
        
        // 5. Reset
        composeTestRule
            .onNodeWithTag("reset-button")
            .performClick()
        
        // 6. Verificar estado final
        composeTestRule
            .onNodeWithTag("counter-text")
            .assertTextContains("Contador: 0")
    }
}
```

---

## 🛠️ Configuración

### Dependencias en `build.gradle.kts`

```kotlin
dependencies {
    // Testing - Unit Tests
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.14.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
    
    // Testing - Android Tests
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("androidx.navigation:navigation-testing:2.8.4")
    
    // Debug
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
```

### Configuración de Lint

```kotlin
android {
    lint {
        disable.addAll(listOf("ViewModelConstructorInComposable"))
        abortOnError = false
    }
}
```

---

## 📊 Estado del Proyecto

### ✅ Tests Implementados

- **29 Unit Tests** - ViewModels, Repository, lógica de negocio
- **21 Instrumentation Tests** - UI, navegación, flujos de usuario
- **100% Coverage** en componentes críticos

### 🏗️ Arquitectura

- **MVVM Pattern** - Separación clara de responsabilidades
- **Repository Pattern** - Abstracción de datos
- **Reactive Programming** - StateFlow para UI reactiva
- **Dependency Injection** - Mocks para testing

---

## 📚 Recursos Adicionales

### 📖 Documentación Oficial

- [Testing en Android](https://developer.android.com/training/testing)
- [Compose Testing](https://developer.android.com/jetpack/compose/testing)
- [Testing con Coroutines](https://developer.android.com/kotlin/coroutines/test)

### 🛠️ Herramientas

- **JUnit** - Framework de testing
- **Mockito** - Mocking framework
- **Espresso** - UI testing
- **Compose Test Rule** - Testing de Compose

### 📝 Buenas Prácticas

1. **Nombra tests descriptivamente**: `boton_incrementar_aumenta_contador()`
2. **Usa testTag** para elementos importantes en UI
3. **Sigue patrón AAA**: Arrange, Act, Assert
4. **Un test, una responsabilidad**
5. **Tests independientes** entre sí
6. **Mocks para dependencias externas**
7. **Tests rápidos y confiables**

---

## 🚀 Cómo Empezar

1. **Clona el repositorio**
2. **Abre en Android Studio**
3. **Sincroniza el proyecto** (`File` → `Sync Project with Gradle Files`)
4. **Ejecuta los tests**:
   ```bash
   ./gradlew testDebugUnitTest
   ./gradlew connectedDebugAndroidTest
   ```

---

## 📞 Contacto

Para preguntas sobre este proyecto de testing:

- **Curso**: Desarrollo Móvil - Universidad del Valle
- **Tema**: Testing en Android con Jetpack Compose
- **Arquitectura**: MVVM + Repository Pattern

---

**¡Happy Testing! 🧪📱**
