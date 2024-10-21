class c20006636 {

    public void trimAndWriteNewSff(OutputStream out) throws IORuntimeException {
        TrimParser trimmer = new TrimParser();
        SffParser.parseSFF(JavaCIPUnknownScope.untrimmedSffFile, trimmer);
        JavaCIPUnknownScope.tempOut.close();
        JavaCIPUnknownScope.headerBuilder.withNoIndex().numberOfReads(JavaCIPUnknownScope.numberOfTrimmedReads);
        SffWriter.writeCommonHeader(JavaCIPUnknownScope.headerBuilder.build(), out);
        InputStream in = null;
        try {
            in = new FileInputStream(JavaCIPUnknownScope.tempReadDataFile);
            IOUtils.copyLarge(in, out);
        } finally {
            IOUtil.closeAndIgnoreErrors(in);
        }
    }
}
