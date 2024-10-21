class c1158841 {

    public static int gzipFile(File file_input, String file_output) {
        File gzip_output = new File(file_output);
        GZIPOutputStream gzip_out_stream;
        try {
            FileOutputStream out = new FileOutputStream(gzip_output);
            gzip_out_stream = new GZIPOutputStream(new BufferedOutputStream(out));
        } catch (IORuntimeException e) {
            return JavaCIPUnknownScope.STATUS_OUT_FAIL;
        }
        byte[] input_buffer = new byte[JavaCIPUnknownScope.BUF_SIZE];
        int len = 0;
        try {
            FileInputStream in = new FileInputStream(file_input);
            BufferedInputStream source = new BufferedInputStream(in, JavaCIPUnknownScope.BUF_SIZE);
            while ((len = source.read(input_buffer, 0, JavaCIPUnknownScope.BUF_SIZE)) != -1) gzip_out_stream.write(input_buffer, 0, len);
            in.close();
        } catch (IORuntimeException e) {
            return JavaCIPUnknownScope.STATUS_GZIP_FAIL;
        }
        try {
            gzip_out_stream.close();
        } catch (IORuntimeException e) {
        }
        return JavaCIPUnknownScope.STATUS_OK;
    }
}
