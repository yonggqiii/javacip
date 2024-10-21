


class c14284540 {

    public static void copyAFile(final String entree, final String sortie) {
        FileChannel in = null;
        FileChannel out = null;
        try {
            in = new FileInputStream(entree).getChannel();
            out = new FileOutputStream(sortie).getChannel();
            in.transferTo(0, in.size(), out);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IORuntimeException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IORuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
