class c20953665 {

    private void packageDestZip(File tmpFile) throws FileNotFoundRuntimeException, IORuntimeException {
        JavaCIPUnknownScope.log("Creating launch profile package " + JavaCIPUnknownScope.destfile);
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(JavaCIPUnknownScope.destfile)));
        ZipEntry e = new ZipEntry(JavaCIPUnknownScope.RESOURCE_JAR_FILENAME);
        e.setMethod(ZipEntry.STORED);
        e.setSize(tmpFile.length());
        e.setCompressedSize(tmpFile.length());
        e.setCrc(JavaCIPUnknownScope.calcChecksum(tmpFile, new CRC32()));
        out.putNextEntry(e);
        InputStream in = new BufferedInputStream(new FileInputStream(tmpFile));
        int c;
        while ((c = in.read()) != -1) out.write(c);
        in.close();
        out.closeEntry();
        out.finish();
        out.close();
    }
}
