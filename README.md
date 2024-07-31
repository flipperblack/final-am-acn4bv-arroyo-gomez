# Primer Parcial ACN4BV

### Arroyo Enrique
### Gomez Marco

Fecha de entrega: 8 de Mayo

---

### Documentación:

Enlace Figma:
https://www.figma.com/file/cCQOzfsbxEKUXRN4Pdj1IG/appmobile?type=design&node-id=0%3A1&mode=design&t=8mgNMV7bbDRfZTSs-1

Enlace Github:
https://github.com/EnriqueArroyo/parcial-1-am-acn4bv-arroyo-gomez

### Datos del Producto:

**Nombre**: Grupo Fácil

**Problema a resolver**:
La organización y asignación de individuos en grupos definidos dentro de una entidad presenta complejidades operativas, específicamente en la asignación rápida y eficiente de individuos (estudiantes, deportistas, empleados, excursionistas, etc.) en equipos.

**Objetivo:**
Facilitar la creación, almacenamiento, visualización, actualización y eliminación (CRUD) de grupos aleatorios configurados por un número predefinido de integrantes, mediante una interfaz intuitiva creada en Android como aplicación móvil.

**Propuesta de valor:**
**Grupo Fácil** simplifica la creación y gestión de grupos dentro de cualquier organización, permitiendo al usuario formar equipos aleatorios y equitativos con rapidez y eficiencia. Ideal para educadores, gestores de recursos humanos y líderes de equipo, **Grupo Fácil** ofrece una solución intuitiva que ahorra tiempo y fomenta la equidad en la formación de grupos sin lugar a la subjetividad.

**Aspectos técnicos:**
Fuente: Poppins
Paleta de colores consideradas para el proyecto:

<img src="/README.RES/Untitled.png" width="300">

### Pantalla elegida para desarrollar:

**Nombre:** Home.

**Orden:** En el flujo de uso sale segunda, tras el login del usuario.

**Propósito:**
Actuar de pantalla principal, sus funciones son:

- Permite crear un grupo nuevo.
- Muestra los grupos ya creados.
- Permite acceder a un grupo ya creado.

**Salidas:**
Tiene dos flujos de salida que derivan en las pantallas:

- Ver grupo (existente).
- Crear grupo nuevo.

**Mockup:**

<img src="/README.RES/Untitled1.png" width="300">



**Maqueta Técnica:**

<img src="/README.RES/Untitled2.png" width="300">



**Desarrollo:**

<img src="/README.RES/Untitled3.png" width="300">

## Implementaciones Obligatorias:

### **ConstraintLayouts:**

TextView:

```jsx
app:layout_constraintBottom_toBottomOf="parent"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintHorizontal_bias="0.047"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintTop_toTopOf="parent"
app:layout_constraintVertical_bias="0.041"41"
```

ScrollView:

```jsx
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintTop_toBottomOf="@+id/textView">
```

Button:

```jsx
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintHorizontal_bias="0.498"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintTop_toTopOf="@+id/scrollView"
```

### **LinearLayouts, Button y Textview:**

<img src="/README.RES/Untitled4.png" width="500">

### **Contenidos reales:**

> “Los contenidos de la aplicación deben ser REALES y estar acompañados correctamente por imágenes para enriquecer el producto.”
>

En nuestro caso la lógica para asignar el nombre del “Grupo Nuevo” esta en otra pantalla que no entra en la consigna de la primera entrega, por lo que el nombre queda hardcodeado hasta la próxima entrega, el resto de los elementos si poseen el nombre real.
Nota: No tenemos imágenes planeadas para nuestras pantallas.

### **Al menos un elemento tiene que tener aplicado un evento:**

```java
MainActivity.java
...
Button addButton = findViewById(R.id.addButton);
        ScrollView scrollView = findViewById(R.id.scrollView);
        final LinearLayout linearLayout = findViewById(R.id.linearLayout);

        //Agregar un botón nuevo al presionar el botón "Agregar botón"
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
...
```

Es el botón “Agregar”

### Debe agregarse al menos un elemento dinámicamente (creado usando Java, fuera del XML).

```java
MainActivity.java
...
addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un nuevo botón
                Button newButton = new Button(getApplicationContext());

                // Configurar el texto y la fuente del botón
                newButton.setText("Grupo nuevo");
                Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.poppins);
                newButton.setTypeface(typeface);

                // Configurar el tamaño y el color de fondo del botón
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, // Ancho MATCH_PARENT
                        LinearLayout.LayoutParams.WRAP_CONTENT); // Alto WRAP_CONTENT
                newButton.setBackgroundResource(R.drawable.button_background); // Fondo del botón

                // Configurar el margen inferior del botón
                layoutParams.setMargins(0, 0, 5, 16); // Margen inferior de 16dp

                // Aplicar los parámetros de diseño al botón
                newButton.setLayoutParams(layoutParams);
                newButton.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

                // Agregar el nuevo botón al LinearLayout dentro del ScrollView
                linearLayout.addView(newButton);

                // Scroll hacia abajo para mostrar el nuevo botón
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });
...
```

Son los botones “Grupo nuevo” que se crean dentro del ScrollView

### Los cambios deben ser aplicados directamente sobre el repositorio, no se admiten entregas con 1 o dos commits.
Y
Los cambios deben ser realizados por ambos miembros del equipo, y visualizarse en el github.

<img src="/README.RES/Untitled5.png" width="400">

Hasta el momento dividimos el proyecto en dos ramas. Master o Main para tener el contenido funcionando y asegurado. Dev para el entorno de desarrollo compartido entre los dos miembros.

NOTA: Captura hasta el momento de la creación de la documentación

## **Agregados para llegar a 10**

### Deberán realizar un informe:

> "Deberán realizar un informe que describa por cada pantalla a realizar con las funcionalidades esperadas, desarrollando el flujo de uso de la misma. La elección de la herramienta para el desarrollo del mock es libre."
>

<img src="/README.RES/Untitled6.png" width="550">

1-
**Nombre:** Login

**Funcionalidades esperadas:**
- Validar que el usuario exista para permitir ingresar
- Crear usuario nuevo

**Flujo:** Direcciona a pantalla 2

2-
**Nombre:** Home

**Funcionalidades esperadas:**
- Crear un grupo nuevo. 
- Mostrar grupos existentes 
- Ver un grupo existente

**Flujo:** Direcciona a pantalla 3 o 4.1


3-
**Nombre:** Ver grupo

**Funcionalidades esperadas:**
- Mostrar datos y contenidos del grupo seleccionado 
- Eliminar grupo 
- Volver a generar → Vuelve a rearmar de forma aleatoria los grupos

**Flujo:**
- Volver atrás → Pantalla 2


4.1-
**Nombre:** Nuevo Grupo

**Funcionalidades esperadas:**
- Primer paso a la creación del grupo.
- Completar el nombre del grupo nuevo.

**Flujo:**
- Volver atrás → Cancelar 
- Pantalla 4.2 → Continuar

4.2-
**Nombre:** Descripción

**Funcionalidades esperadas:**
- Colocar una descripción opcional al grupo nuevo.

**Flujo:**
- Volver atrás 
- Pantalla 4.3 → Continuar

4.3-
**Nombre:** Participantes

**Funcionalidades esperadas:**
- Completar una lista de participantes (Nombre)

**Flujo:**
- Volver atrás 
- Pantalla 4.4 → Continuar

4.4-
**Nombre:** Configuración

**Funcionalidades esperadas:**
- Seleccionar la cantidad de participantes por grupo

**Flujo:**
- Volver atrás 
- Pantalla 4.5 → Continuar

4.5-
**Nombre:** Confirmación

**Funcionalidades esperadas:**
- Calcular y armar los grupos de forma aleatoria en base a la configuración 
- Previsualizar todos los datos del Grupo creado, incluyendo la lista de participantes subdividida por grupos. 
- Guardar datos al Completar.

**Flujo:**
- Volver atrás 
- Pantalla 2 → Completar


### Se valorará la elección de colores/diseño

Se especifica al principio del documento la paleta sugerida y la fuente.


### Deben organizarse apropiadamente:

- Variables de String
- No tenemos al menos para esta pantalla muchas palabras que puedan variar

<img src="/README.RES/Untitled7.png" width="550">

- Variables de Dimensiones 
Estimamos son los drawable

<img src="/README.RES/Untitled8.png" width="550">

<img src="/README.RES/Untitled9.png" width="550">

- Variables de Colores 
También tenemos poquitos de momento

<img src="/README.RES/Untitled10.png" width="550">

### "La aplicación debe tener comportamiento dinámico:

> La aplicación debe tener comportamiento dinámico, es decir, que al interactuar con los elementos responda modificando algo en relación al objetivo de la aplicación, por ejemplo: al hacer click en un botón que sea agregar X cosa, es incluya en una ScrollView, al agregar varios se puedan visualizar cada una.
>
>
> El botón ID: addButton mediante el evento que lo recurre “addButton.setOnClickListener” se encarga de agregar en la misma pantalla (en el ScrollView) un botón nuevo que representa un **Grupo** creado por el usuario tras haber completado el formulario de las pantallas 4.
> Estos grupos se convertirán en clases con sus propias propiedades y atributos pero por el momento solo se crean de forma hardcodeada como un botón mas llamado “Grupo nuevo”."
>

### Mientras más interacciones/comportamientos se incluya, mayor será la valoración.

No tenemos mas interacciones planificadas por el momento con esta pantalla.

### Uso de algún tipo de convención para los commit

Usamos conventional commits (salvo el primer commit que lo creo Github)