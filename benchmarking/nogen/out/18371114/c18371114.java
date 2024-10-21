class c18371114 {

    public static void copy(final File source, final File dest) throws IORuntimeException {
        FileChannel in = null;
        FileChannel out = null;
        try {
            in = new FileInputStream(source).getChannel();
            out = new FileOutputStream(dest).getChannel();
            long size = in.size();
            MappedByteBuffer buf = in.map(FileChannel.MapMode.READ_ONLY, 0, size);
            out.write(buf);
            dest.setLastModified(source.lastModified());
        } finally {
            JavaCIPUnknownScope.close(in);
            JavaCIPUnknownScope.close(out);
        }
    }
}
