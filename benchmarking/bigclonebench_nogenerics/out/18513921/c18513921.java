class c18513921 {

    public static void copy(File src, File dst) {
        try {
            InputStream is = null;
            OutputStream os = null;
            try {
                is = new BufferedInputStream(new FileInputStream(src), JavaCIPUnknownScope.BUFFER_SIZE);
                os = new BufferedOutputStream(new FileOutputStream(dst), JavaCIPUnknownScope.BUFFER_SIZE);
                byte[] buffer = new byte[JavaCIPUnknownScope.BUFFER_SIZE];
                int len = 0;
                while ((len = is.read(buffer)) > 0) os.write(buffer, 0, len);
            } finally {
                if (null != is)
                    is.close();
                if (null != os)
                    os.close();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
