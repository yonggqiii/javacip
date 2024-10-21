class c2069219 {

    public static void copieFichier(File fichier1, File fichier2) {
        FileChannel in = null;
        FileChannel out = null;
        try {
            in = new FileInputStream(fichier1).getChannel();
            out = new FileOutputStream(fichier2).getChannel();
            in.transferTo(0, in.size(), out);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IORuntimeException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IORuntimeException e) {
                }
            }
        }
    }
}
