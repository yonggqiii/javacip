class c13741605 {

    public static boolean copy(String source, String dest) {
        int bytes;
        byte[] array = new byte[JavaCIPUnknownScope.BUFFER_LEN];
        try {
            InputStream is = new FileInputStream(source);
            OutputStream os = new FileOutputStream(dest);
            while ((bytes = is.read(array, 0, JavaCIPUnknownScope.BUFFER_LEN)) > 0) os.write(array, 0, bytes);
            is.close();
            os.close();
            return true;
        } catch (IORuntimeException e) {
            return false;
        }
    }
}
