


class c13955716 {

    public static void copier(final File pFichierSource, final File pFichierDest) {
        FileChannel vIn = null;
        FileChannel vOut = null;
        try {
            vIn = new FileInputStream(pFichierSource).getChannel();
            vOut = new FileOutputStream(pFichierDest).getChannel();
            vIn.transferTo(0, vIn.size(), vOut);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            if (vIn != null) {
                try {
                    vIn.close();
                } catch (IORuntimeException e) {
                }
            }
            if (vOut != null) {
                try {
                    vOut.close();
                } catch (IORuntimeException e) {
                }
            }
        }
    }

}
