package iser.apiOrion.email;


public class EmailConstant {

    /**
     * Metodo que retorna el contenido del correo de bienvenida
     * @param nombre nombre del usuario
     * @return contenido del correo
     */
    public static String formularioRegistrado(String nombre) {
        StringBuilder content = new StringBuilder();
        content.append("<!DOCTYPE html>")
                .append("<html lang='es'>")
                .append("<head>")
                .append("    <meta charset='UTF-8'>")
                .append("    <style>")
                .append("        main :is(p){")
                .append("            line-height: 0px;")
                .append("        }")
                .append("    </style>")
                .append("</head>")
                .append("<body>")
                .append("    <main>")
                .append("         <p> <strong>Estimado(a) Usuario</strong>, </p> ")
                .append("         <p> Se informa que se ha recibido un nuevo formulario para hacer parte de AgricultorIoT de <strong>")
                .append(            nombre )
                .append("           </strong> por favor revisa el formulario y decide si quieres que haga parte del proyecto. </p>")
                .append("         <br> ")
                .append("    </main>")
                .append("</body>")
                .append("</html>");
        return content.toString();
    }

    /**
     * Metodo que retorna el contenido del correo de usuario aceptado
     * @return contenido del correo
     */
    public static String usuarioAceptado() {
        StringBuilder content = new StringBuilder();
        content.append("<!DOCTYPE html>")
                .append("<html lang='es'>")
                .append("<head>")
                .append("    <meta charset='UTF-8'>")
                .append("    <style>")
                .append("        main :is(p){")
                .append("            line-height: 0px;")
                .append("        }")
                .append("    </style>")
                .append("</head>")
                .append("<body>")
                .append("    <main>")
                .append("         <p> <strong>Estimado(a) Usuario</strong>, </p> ")
                .append("         <p> Se informa que se ha aceptado tu solicitud para hacer parte de AgricultorIoT ")
                .append("            por favor ve e inicia sesion y has parte de este sistema. </p>")
                .append("         <br> ")
                .append("    </main>")
                .append("</body>")
                .append("</html>");
        return content.toString();
    }

}
