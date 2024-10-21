class c10395759 {

    public void run() {
        JavaCIPUnknownScope.btnReintentar.setEnabled(false);
        try {
            JavaCIPUnknownScope.lblEstado.setText("Conectando con servidor...");
            JavaCIPUnknownScope.escribir("\nConectando con servidor...");
            URL url = new URL("http://apeiron.sourceforge.net/version.php");
            JavaCIPUnknownScope.lblEstado.setText("Obteniendo informaci�n de versi�n...");
            JavaCIPUnknownScope.escribir("Ok\n");
            JavaCIPUnknownScope.escribir("Obteniendo informaci�n sobre �ltima versi�n...");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String linea = br.readLine();
            JavaCIPUnknownScope.escribir("Ok\n");
            if (linea != null) {
                JavaCIPUnknownScope.escribir("Versi�n mas reciente: " + linea + "\n");
                if (Principal.version < Double.parseDouble(linea)) {
                    JavaCIPUnknownScope.lblEstado.setText("Hay una nueva versi�n: Apeiron " + linea);
                    JavaCIPUnknownScope.escribir("Puede obtener la actualizaci�n de: http://apeiron.sourceforge.net\n");
                    JavaCIPUnknownScope.btnActualizar.setEnabled(true);
                    JavaCIPUnknownScope.setVisible(true);
                } else {
                    JavaCIPUnknownScope.lblEstado.setText("Usted tiene la �ltima versi�n");
                }
            }
            br.close();
        } catch (MalformedURLRuntimeException e) {
            JavaCIPUnknownScope.escribir("Fall�\n" + e + "\n");
            e.printStackTrace();
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.escribir("Fall�\n" + e + "\n");
            e.printStackTrace();
        }
        JavaCIPUnknownScope.btnReintentar.setEnabled(true);
    }
}
