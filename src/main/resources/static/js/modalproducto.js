

function abrirModalProducto(element) {
        const productoId = element.getAttribute("data-id");
        const modalContainer = document.getElementById("modalContainer");

        // Llamada para cargar el contenido de modalproducto.html
        fetch(`/modalproducto.html?idProducto=${productoId}`)
            .then(response => response.text())
            .then(html => {
                modalContainer.innerHTML = html;
                modalContainer.style.display = "block";
            });
    }

    // Cierra el modal
    function cerrarModal() {
        document.getElementById("modalContainer").style.display = "none";
    }

