class c16822378 {

    public String descargarArchivo(String miArchivo, String nUsuario) {
        try {
            URL url = new URL(JavaCIPUnknownScope.conf.Conf.descarga + nUsuario + "/" + miArchivo);
            URLConnection urlCon = url.openConnection();
            System.out.println(urlCon.getContentType());
            InputStream is = urlCon.getInputStream();
            FileOutputStream fos = new FileOutputStream("D:/" + miArchivo);
            byte[] array = new byte[1000];
            int leido = is.read(array);
            while (leido > 0) {
                fos.write(array, 0, leido);
                leido = is.read(array);
            }
            is.close();
            fos.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return "llego";
    }
}
