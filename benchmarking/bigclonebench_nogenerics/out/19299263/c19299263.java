class c19299263 {

    String extractTiffFile(String path) throws IORuntimeException {
        ZipInputStream in = new ZipInputStream(new FileInputStream(path));
        OutputStream out = new FileOutputStream(JavaCIPUnknownScope.dir + JavaCIPUnknownScope.TEMP_NAME);
        byte[] buf = new byte[1024];
        int len;
        ZipEntry entry = in.getNextEntry();
        if (entry == null)
            return null;
        String name = entry.getName();
        if (!name.endsWith(".tif"))
            throw new IORuntimeException("This ZIP archive does not appear to contain a TIFF file");
        while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
        out.close();
        in.close();
        return name;
    }
}
