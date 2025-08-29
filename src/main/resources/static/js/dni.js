// Selecciona el elemento HTML con el id "boton" y lo asigna a la variable boton.
var boton = document.getElementById("boton");

// Define una función llamada traer que se encargará de obtener los datos del DNI.
function traer() {
    
    // Obtener el valor del input con el id "dni" y asignarlo a la variable dni.
    var dni = document.getElementById("dni").value;

    // Realizar una solicitud HTTP GET a la API de apiperu.dev para obtener los datos del DNI.
    fetch(
        "https://apiperu.dev/api/dni/" +
        dni +
        "?api_token=5374cc314f74f8d7193c53d6299c6b24a33afec5502bd3fec6869b38029ee5fa"
    )

        // Convertir la respuesta a JSON.
        .then((res) => res.json())
        // Procesar los datos obtenidos.
        .then((data => {
            // Asignar el número del DNI a un input con el id "doc".
            document.getElementById("doc").value = data.data.numero;
            // Asignar los nombres a un input con el id "nombre".
            document.getElementById("nombre").value = data.data.nombres;
            // Asignar los apellidos a un input con el id "apellido", concatenando el apellido paterno y materno.
            document.getElementById("apellido").value = 
                data.data.apellido_paterno + " " + data.data.apellido_materno;
        }));

}

// Añadir un escuchador de eventos al botón para que cuando se haga clic, se llame a la función traer.
boton.addEventListener("click", traer);