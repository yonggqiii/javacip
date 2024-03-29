class c20273405 {

    public static void decompress(final File file, final File folder, final boolean deleteZipAfter) throws IORuntimeException {
        final ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(file.getCanonicalFile())));
        ZipEntry ze;
        try {
            while (null != (ze = zis.getNextEntry())) {
                final File f = new File(folder.getCanonicalPath(), ze.getName());
                if (f.exists())
                    f.delete();
                if (ze.isDirectory()) {
                    f.mkdirs();
                    continue;
                }
                f.getParentFile().mkdirs();
                final OutputStream fos = new BufferedOutputStream(new FileOutputStream(f));
                try {
                    try {
                        final byte[] buf = new byte[8192];
                        int bytesRead;
                        while (-1 != (bytesRead = zis.read(buf))) fos.write(buf, 0, bytesRead);
                    } finally {
                        fos.close();
                    }
                } catch (final IORuntimeException ioe) {
                    f.delete();
                    throw ioe;
                }
            }
        } finally {
            zis.close();
        }
        if (deleteZipAfter)
            file.delete();
    }
}
