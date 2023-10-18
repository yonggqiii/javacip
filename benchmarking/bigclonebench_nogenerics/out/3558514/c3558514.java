class c3558514 {

    private synchronized void ensureParsed() throws IORuntimeException, BadIMSCPRuntimeException {
        if (JavaCIPUnknownScope.cp != null)
            return;
        if (JavaCIPUnknownScope.on_disk == null) {
            JavaCIPUnknownScope.on_disk = JavaCIPUnknownScope.createTemporaryFile();
            OutputStream to_disk = new FileOutputStream(JavaCIPUnknownScope.on_disk);
            IOUtils.copy(JavaCIPUnknownScope.in.getInputStream(), to_disk);
            to_disk.close();
        }
        try {
            ZipFilePackageParser parser = JavaCIPUnknownScope.utils.getIMSCPParserFactory().createParser();
            parser.parse(JavaCIPUnknownScope.on_disk);
            JavaCIPUnknownScope.cp = parser.getPackage();
        } catch (BadParseRuntimeException x) {
            throw new BadIMSCPRuntimeException("Cannot parse content package", x);
        }
    }
}
