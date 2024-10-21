class c13595251 {

    public void descargarArchivo() {
        try {
            FileInputStream fis = new FileInputStream(JavaCIPUnknownScope.resultados.elementAt(JavaCIPUnknownScope.materialSelccionado).getRuta());
            FileOutputStream fos = new FileOutputStream(JavaCIPUnknownScope.rutaDestinoDescarga);
            FileChannel inChannel = fis.getChannel();
            FileChannel outChannel = fos.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            fis.close();
            fos.close();
        } catch (IORuntimeException ioe) {
            System.err.println("Error al Generar Copia del Material\n" + ioe);
        }
    }
}
