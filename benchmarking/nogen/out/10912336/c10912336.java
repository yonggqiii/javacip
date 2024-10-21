class c10912336 {

    public void verRecordatorio() {
        try {
            JavaCIPUnknownScope.cantidadArchivos = JavaCIPUnknownScope.obtenerCantidad() + 1;
            boolean existe = false;
            String filenametxt = "";
            String filenamezip = "";
            String hora = "";
            String lugar = "";
            String actividad = "";
            String linea = "";
            int dia = 0;
            int mes = 0;
            int ano = 0;
            for (int i = 1; i < JavaCIPUnknownScope.cantidadArchivos; i++) {
                filenamezip = "recordatorio" + i + ".zip";
                filenametxt = "recordatorio" + i + ".txt";
                BufferedOutputStream dest = null;
                BufferedInputStream is = null;
                ZipEntry entry;
                ZipFile zipfile = new ZipFile(filenamezip);
                Enumeration e = zipfile.entries();
                while (e.hasMoreElements()) {
                    entry = (ZipEntry) e.nextElement();
                    is = new BufferedInputStream(zipfile.getInputStream(entry));
                    int count;
                    byte[] data = new byte[JavaCIPUnknownScope.buffer];
                    FileOutputStream fos = new FileOutputStream(entry.getName());
                    dest = new BufferedOutputStream(fos, JavaCIPUnknownScope.buffer);
                    while ((count = is.read(data, 0, JavaCIPUnknownScope.buffer)) != -1) dest.write(data, 0, count);
                    dest.flush();
                    dest.close();
                    is.close();
                }
                DataInputStream input = new DataInputStream(new FileInputStream(filenametxt));
                dia = Integer.parseInt(input.readLine());
                mes = Integer.parseInt(input.readLine());
                ano = Integer.parseInt(input.readLine());
                if (dia == Integer.parseInt(JavaCIPUnknownScope.identificarDato(JavaCIPUnknownScope.datoSeleccionado))) {
                    existe = true;
                    hora = input.readLine();
                    lugar = input.readLine();
                    while ((linea = input.readLine()) != null) actividad += linea + "\n";
                    JavaCIPUnknownScope.verRecordatorioInterfaz(hora, lugar, actividad);
                    hora = "";
                    lugar = "";
                    actividad = "";
                }
                input.close();
            }
            if (!existe)
                JOptionPane.showMessageDialog(null, "No existe un recordatorio guardado\n" + "para el " + JavaCIPUnknownScope.identificarDato(JavaCIPUnknownScope.datoSeleccionado) + " de " + JavaCIPUnknownScope.meses[JavaCIPUnknownScope.mesTemporal].toLowerCase() + " del a�o " + JavaCIPUnknownScope.anoTemporal, "No existe", JOptionPane.INFORMATION_MESSAGE);
            JavaCIPUnknownScope.table.clearSelection();
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, "Error en: " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
