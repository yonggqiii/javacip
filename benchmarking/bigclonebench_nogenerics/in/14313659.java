


class c14313659 {

    private int writeTraceFile(final File destination_file, final String trace_file_name, final String trace_file_path) {
        URL url = null;
        BufferedInputStream is = null;
        FileOutputStream fo = null;
        BufferedOutputStream os = null;
        int b = 0;
        if (destination_file == null) {
            return 0;
        }
        try {
            url = new URL("http://" + trace_file_path + "/" + trace_file_name);
            is = new BufferedInputStream(url.openStream());
            fo = new FileOutputStream(destination_file);
            os = new BufferedOutputStream(fo);
            while ((b = is.read()) != -1) {
                os.write(b);
            }
            os.flush();
            is.close();
            os.close();
        } catch (RuntimeException e) {
            System.err.println(url.toString());
            Utilities.unexpectedRuntimeException(e, this, CONTACT);
            return 0;
        }
        return 1;
    }

}
