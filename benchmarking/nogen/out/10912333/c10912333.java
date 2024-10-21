class c10912333 {

    public void guardarRecordatorio() {
        try {
            if (JavaCIPUnknownScope.espaciosLlenos()) {
                JavaCIPUnknownScope.guardarCantidad();
                String dat = "";
                String filenametxt = String.valueOf("recordatorio" + JavaCIPUnknownScope.cantidadArchivos + ".txt");
                String filenamezip = String.valueOf("recordatorio" + JavaCIPUnknownScope.cantidadArchivos + ".zip");
                JavaCIPUnknownScope.cantidadArchivos++;
                dat += JavaCIPUnknownScope.identificarDato(JavaCIPUnknownScope.datoSeleccionado) + "\n";
                dat += String.valueOf(JavaCIPUnknownScope.mesTemporal) + "\n";
                dat += String.valueOf(JavaCIPUnknownScope.anoTemporal) + "\n";
                dat += JavaCIPUnknownScope.horaT.getText() + "\n";
                dat += JavaCIPUnknownScope.lugarT.getText() + "\n";
                dat += JavaCIPUnknownScope.actividadT.getText() + "\n";
                File archivo = new File(filenametxt);
                FileWriter fw = new FileWriter(archivo);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter salida = new PrintWriter(bw);
                salida.print(dat);
                salida.close();
                BufferedInputStream origin = null;
                FileOutputStream dest = new FileOutputStream(filenamezip);
                ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
                byte[] data = new byte[JavaCIPUnknownScope.buffer];
                File f = new File(filenametxt);
                FileInputStream fi = new FileInputStream(f);
                origin = new BufferedInputStream(fi, JavaCIPUnknownScope.buffer);
                ZipEntry entry = new ZipEntry(filenametxt);
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, JavaCIPUnknownScope.buffer)) != -1) out.write(data, 0, count);
                out.close();
                JOptionPane.showMessageDialog(null, "El recordatorio ha sido guardado con exito", "Recordatorio Guardado", JOptionPane.INFORMATION_MESSAGE);
                JavaCIPUnknownScope.marco.hide();
                JavaCIPUnknownScope.marco.dispose();
                JavaCIPUnknownScope.establecerMarca();
                JavaCIPUnknownScope.table.clearSelection();
            } else
                JOptionPane.showMessageDialog(null, "Debe llenar los espacios de Hora, Lugar y Actividad", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, "Error en: " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
