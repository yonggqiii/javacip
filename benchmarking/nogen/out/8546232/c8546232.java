class c8546232 {

    public void execute(File sourceFile, File destinationFile, Properties htmlCleanerConfig) {
        FileReader reader = null;
        Writer writer = null;
        try {
            reader = new FileReader(sourceFile);
            JavaCIPUnknownScope.logger.info("Using source file: " + JavaCIPUnknownScope.trimPath(JavaCIPUnknownScope.userDir, sourceFile));
            if (!destinationFile.getParentFile().exists()) {
                JavaCIPUnknownScope.createDirectory(destinationFile.getParentFile());
            }
            writer = new FileWriter(destinationFile);
            JavaCIPUnknownScope.logger.info("Destination file:  " + JavaCIPUnknownScope.trimPath(JavaCIPUnknownScope.userDir, destinationFile));
            execute(reader, writer, htmlCleanerConfig);
        } catch (FileNotFoundRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                    writer = null;
                } catch (IORuntimeException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                    reader = null;
                } catch (IORuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
