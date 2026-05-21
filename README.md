#  Nodo Cívico

Aplicación Android para la gestión de reportes ciudadanos. Permite guardar y consultar información de forma local usando Room Database, funcionando completamente sin internet.

---

Aplicación Android para reportes ciudadanos.

##  Entregable 1: Base funcional y navegación

Este primer entregable debe demostrar que el grupo definió correctamente el alcance y ya construyó la
estructura base de la aplicación

---

## Funcionalidades actuales

- Splash Screen
- Login
- Home
- Lista de reportes
- Crear reporte
- Perfil usuario

## Tecnologías

- Kotlin
- Android Studio
- Navigation Component
- Material Design

## Entregable 2: Persistencia y lógica offline

Este proyecto cumple con el segundo entregable, implementando almacenamiento local con Room y una arquitectura MVVM.

---

## Funcionalidades

###  Base de datos local
- Implementación de Room Database
- Almacenamiento de reportes en el dispositivo
- Funcionamiento offline


###  Arquitectura del proyecto
- Entity: Reporte
- DAO: consultas a la base de datos
- Repository: manejo de datos
- ViewModel: conexión con la interfaz


###  CRUD
- Crear reportes
- Consultar lista de reportes
- Eliminar reportes (si está implementado)
- Edición (si está en desarrollo)


###  Formularios
- Formulario para crear reportes
- Campos:
    - Título
    - Descripción
    - Ubicación
- Validación de campos obligatorios


### Listado de reportes
- Muestra los datos guardados en Room
- Actualización automática con LiveData


### Estados de la app
- Estado vacío cuando no hay datos
- Actualización en tiempo real


###  Funcionamiento offline
- No requiere internet
- Todos los datos se guardan localmente


##  Tecnologías utilizadas
- Kotlin
- Android Studio
- Room Database
- LiveData
- ViewModel
- Navigation Component
- Material Design


##  Autor
Pedro Rodriguez

---