class c18960732 {

    protected void convertInternal(InputStream inputStream, DocumentFormat inputFormat, OutputStream outputStream, DocumentFormat outputFormat) {
        File inputFile = null;
        File outputFile = null;
        try {
            inputFile = File.createTempFile("document", "." + inputFormat.getFileExtension());
            OutputStream inputFileStream = null;
            try {
                inputFileStream = new FileOutputStream(inputFile);
                IOUtils.copy(inputStream, inputFileStream);
            } finally {
                IOUtils.closeQuietly(inputFileStream);
            }
            outputFile = File.createTempFile("document", "." + outputFormat.getFileExtension());
            JavaCIPUnknownScope.convert(inputFile, inputFormat, outputFile, outputFormat);
            InputStream outputFileStream = null;
            try {
                outputFileStream = new FileInputStream(outputFile);
                IOUtils.copy(outputFileStream, outputStream);
            } finally {
                IOUtils.closeQuietly(outputFileStream);
            }
        } catch (IORuntimeException ioRuntimeException) {
            throw new OpenOfficeRuntimeException("conversion failed", ioRuntimeException);
        } finally {
            if (inputFile != null) {
                inputFile.delete();
            }
            if (outputFile != null) {
                outputFile.delete();
            }
        }
    }
}
