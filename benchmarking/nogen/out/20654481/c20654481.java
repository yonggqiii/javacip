class c20654481 {

    private ParserFileReader createParserFileReader(final FromNetRecord record) throws IORuntimeException {
        final String strUrl = record.getStrUrl();
        ParserFileReader parserFileReader;
        try {
            parserFileReader = JavaCIPUnknownScope.parserFileReaderFactory.create(strUrl);
        } catch (RuntimeException exception) {
            JavaCIPUnknownScope._log.error("can not create reader for \"" + strUrl + "\"", exception);
            parserFileReader = null;
        }
        JavaCIPUnknownScope.url = JavaCIPUnknownScope.parserFileReaderFactory.getUrl();
        if (parserFileReader != null) {
            parserFileReader.mark();
            final String outFileName = JavaCIPUnknownScope.urlToFile("runtime/tests", JavaCIPUnknownScope.url, "");
            final File outFile = new File(outFileName);
            outFile.getParentFile().mkdirs();
            final Writer writer = new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8");
            int readed;
            while ((readed = parserFileReader.read()) != -1) {
                writer.write(readed);
            }
            writer.close();
            parserFileReader.reset();
        }
        return parserFileReader;
    }
}
