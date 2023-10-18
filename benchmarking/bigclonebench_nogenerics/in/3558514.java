


class c3558514 {

    private synchronized void ensureParsed() throws IORuntimeException, BadIMSCPRuntimeException {
        if (cp != null) return;
        if (on_disk == null) {
            on_disk = createTemporaryFile();
            OutputStream to_disk = new FileOutputStream(on_disk);
            IOUtils.copy(in.getInputStream(), to_disk);
            to_disk.close();
        }
        try {
            ZipFilePackageParser parser = utils.getIMSCPParserFactory().createParser();
            parser.parse(on_disk);
            cp = parser.getPackage();
        } catch (BadParseRuntimeException x) {
            throw new BadIMSCPRuntimeException("Cannot parse content package", x);
        }
    }

}
