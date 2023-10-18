class c1158843 {

    public static int gunzipFile(File file_input, File dir_output) {
        GZIPInputStream gzip_in_stream;
        try {
            FileInputStream in = new FileInputStream(file_input);
            BufferedInputStream source = new BufferedInputStream(in);
            gzip_in_stream = new GZIPInputStream(source);
        } catch (IORuntimeException e) {
            return JavaCIPUnknownScope.STATUS_IN_FAIL;
        }
        String file_input_name = file_input.getName();
        String file_output_name = file_input_name.substring(0, file_input_name.length() - 3);
        File output_file = new File(dir_output, file_output_name);
        byte[] input_buffer = new byte[JavaCIPUnknownScope.BUF_SIZE];
        int len = 0;
        try {
            FileOutputStream out = new FileOutputStream(output_file);
            BufferedOutputStream destination = new BufferedOutputStream(out, JavaCIPUnknownScope.BUF_SIZE);
            while ((len = gzip_in_stream.read(input_buffer, 0, JavaCIPUnknownScope.BUF_SIZE)) != -1) destination.write(input_buffer, 0, len);
            destination.flush();
            out.close();
        } catch (IORuntimeException e) {
            return JavaCIPUnknownScope.STATUS_GUNZIP_FAIL;
        }
        try {
            gzip_in_stream.close();
        } catch (IORuntimeException e) {
        }
        return JavaCIPUnknownScope.STATUS_OK;
    }
}
