


class c13351384 {

    public void copieFichier(String fileIn, String fileOut) {
        FileChannel in = null;
        FileChannel out = null;
        try {
            in = new FileInputStream(fileIn).getChannel();
            out = new FileOutputStream(fileOut).getChannel();
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
