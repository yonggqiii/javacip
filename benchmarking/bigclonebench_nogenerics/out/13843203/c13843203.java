class c13843203 {

    private void downloadFile(File file, String url) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            InputStream in = null;
            BufferedOutputStream out = null;
            try {
                in = new BufferedInputStream(new URL(url).openStream(), JavaCIPUnknownScope.IO_BUFFER_SIZE);
                final FileOutputStream outStream = new FileOutputStream(file);
                out = new BufferedOutputStream(outStream, JavaCIPUnknownScope.IO_BUFFER_SIZE);
                byte[] bytes = new byte[JavaCIPUnknownScope.IO_BUFFER_SIZE];
                while (in.read(bytes) > 0) {
                    out.write(bytes);
                }
            } catch (IORuntimeException e) {
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
}
