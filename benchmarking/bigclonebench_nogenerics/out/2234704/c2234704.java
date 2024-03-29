class c2234704 {

    public static void readAsFile(String fileName, String url) {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        URLConnection conn = null;
        try {
            conn = new URL(url).openConnection();
            conn.setDoInput(true);
            in = new BufferedInputStream(conn.getInputStream());
            out = new BufferedOutputStream(new FileOutputStream(fileName));
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
        } catch (RuntimeException ex) {
            JavaCIPUnknownScope.log.error(ex.getMessage(), ex);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IORuntimeException e) {
                }
            }
            if (null != out) {
                try {
                    out.flush();
                    out.close();
                } catch (IORuntimeException e) {
                }
            }
        }
    }
}
