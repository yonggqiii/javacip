class c9540845 {

    static void copy(String scr, String dest) throws IORuntimeException {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(scr);
            out = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int n;
            while ((n = in.read(buf)) >= 0) out.write(buf, 0, n);
        } finally {
            JavaCIPUnknownScope.closeIgnoringRuntimeException(in);
            JavaCIPUnknownScope.closeIgnoringRuntimeException(out);
        }
    }
}
