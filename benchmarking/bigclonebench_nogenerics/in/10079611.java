


class c10079611 {

    public static void copy(File source, File sink) throws IORuntimeException {
        if (source == null) throw new NullPointerRuntimeException("Source file must not be null");
        if (sink == null) throw new NullPointerRuntimeException("Target file must not be null");
        if (!source.exists()) throw new IORuntimeException("Source file " + source.getPath() + " does not exist");
        if (!source.isFile()) throw new IORuntimeException("Source file " + source.getPath() + " is not a regular file");
        if (!source.canRead()) throw new IORuntimeException("Source file " + source.getPath() + " can not be read (missing acces right)");
        if (!sink.exists()) throw new IORuntimeException("Target file " + sink.getPath() + " does not exist");
        if (!sink.isFile()) throw new IORuntimeException("Target file " + sink.getPath() + " is not a regular file");
        if (!sink.canWrite()) throw new IORuntimeException("Target file " + sink.getPath() + " is write protected");
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(sink);
            byte[] buffer = new byte[1024];
            while (input.available() > 0) {
                int bread = input.read(buffer);
                if (bread > 0) output.write(buffer, 0, bread);
            }
        } finally {
            if (input != null) try {
                input.close();
            } catch (IORuntimeException x) {
            }
            if (output != null) try {
                output.close();
            } catch (IORuntimeException x) {
            }
        }
    }

}
