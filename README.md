# RetoBit: Estim 🏴‍☠️ Login

Tenemos una estupenda aplicación llamada **Estim**, no creo que el nombre se parezca a nada existente... Esta app es un sistema de gestión de videojuegos. La base de datos que hay conectada es de pruebas, así que no debes preocuparte por su configuración. 

Tu tarea consiste en realizar el login de la aplicación. Deberemos devolver un token de acceso que nos permita realizar peticiones a la API más adelante. De momento la seguridad no está implementada. 

## Fork del repositorio:

Haz un fork del repo.

Pulsa el botón Fork en github:

![fork](public/img1.png)

Esto creará una copia del repositorio en tu perfil de Github. 

## Clona el repo en tu computadora

Ahora hay que descargarse el proyecto en tu computadora.

### 1. Asegúrate de que estás en la URL de TU copia del repositorio
   

Si la URL es esta: https://github.com/curso-java-marshall-bits/estim-login **NO ES CORRECTO**.
    

Donde aparece 'curso-java-marshall-bits' debería aparecer tu nombre de usuario. Haz click en tu perfil y mira tus repositorios para comprobar si hiciste el fork. En caso contrario vuelve al paso anterior. 


### 2. Pulsa en "code" para ver la URL del repositorio y cópiala

![clone](public/img2.png)

Para ello deberás abrir una terminal y navegar a la carpeta donde quieras añadir este retobit.

Utiliza el siguiente comando:

```commandline
git clone https://aqui.debes.pegar.la.url
```

**Nota: Después del 'git clone' debes pegar la url del repositorio. No pongas la que he puesto yo en el ejemplo 🤣*

Ahora se va a crear un nuevo directorio con el nombre del RetoBit.

### 3. ¡Ya puedes abrir este reto en IntelliJ!

# Instrucciones

## Tarea 1: Revisar el código existente

Esta app contiene muchas cosas, así que te recomiendo que abras el proyecto y mires un poco por encima qué es lo que hay. 

Ejecuta el servidor y abre *Postman* para testear la API. La URL del servidor es: `http://localhost:8080/`, como siempre.

## Tarea 2: Implementar el hash de la contraseña

El signup está implementado, podemos hacer una llamada POST a la ruta `auth/signup` con un body como el siguiente:

```json
{
  "username": "test",
  "email": "test@testing.com",
  "password": "Hello1234"
}
```

Esto creará un usuario con todos los datos, pero la contraseña se guardará en texto plano. Esto no es seguro. Así que antes de lanzar la aplicación a producción debemos implementar un hash de la contraseña.

## Tarea 3: Oculta el password en la respuesta del signup

¡Ojo! A pesar de que el password pasa por un hash, no es buena idea que se muestre en la respuesta del signup. Así que debes eliminar el campo password de la respuesta.

Genera un DTO llamado `SignupResponse` que contenga los campos `username`, `email` y `id`. Este DTO se usará para devolver la respuesta del signup sin el campo password.

Ejemplo de respuesta:

```json
{
  "username": "test",
  "email": "test@testing.com",
  "id": 1
}
```

## Testing

Para comprobar si has realizado bien el ejercicio ejecuta los tests ubicados en **src/test**. 
El test te indicará si has pasado con un tick verde ✅. En caso contrario verás el error.

Ejemplo:

![img.png](public/img3.png)

Pulsa en cualquiera de los tests que has fallado y mira el mensaje de la derecha.

- *Expected*: es el valor que el test estaba esperando.
- *Actual*: es el valor que tu reto está retornando. 

## Solución

Si quieres ver una posible solución para el retobit que pasa todos los tests puedes mirar la rama *solution* dentro del repositorio.

![rama solution](public/img4.png)

Ten en cuenta que hay muchas formas de resolver los ejercicios y la tuya puede pasar los tests iguales, pero ser completamente distinta a la solución. No significa que la tuya esté mal. Compara los resultados y decide cuál te gusta más o te parece mas legible.

## Entrega

Debes realizar una pull request para entregar el ejercicio. Abre el link del repositorio en github y haz click en la pestaña *pull requests*.

Selecciona *New pull request*, *Create pull request*. Esto hará que yo pueda verlo y revisarlo en caso de que haya fallado algún test para poder darte feedback.

Mucha suerte con el reto. Te mando un abrazo y ¡Sigamos desarrollando! 🫂

[marshall-bits.dev](http://marshall-bits.dev)

*Nota: Estos retos pertenecen al curso de Marcel Bosch de Java para desarrolladores junior. Cualquier uso fuera de este contexto debe estar autorizado explícitamente. Si quieres usar estos ejercicios ponte en contacto conmigo a través de mis redes sociales (visita mi página para [más información](http://marshall-bits.dev)).* 