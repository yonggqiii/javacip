class c6171405 {

    public static File createGzip(File inputFile) {
        File targetFile = new File(inputFile.getParentFile(), inputFile.getName() + ".gz");
        if (targetFile.exists()) {
            JavaCIPUnknownScope.log.warn("The target file '" + targetFile + "' already exists. Will overwrite");
        }
        FileInputStream in = null;
        GZIPOutputStream out = null;
        try {
            int read = 0;
            byte[] data = new byte[JavaCIPUnknownScope.BUFFER_SIZE];
            in = new FileInputStream(inputFile);
            out = new GZIPOutputStream(new FileOutputStream(targetFile));
            while ((read = in.read(data, 0, JavaCIPUnknownScope.BUFFER_SIZE)) != -1) {
                out.write(data, 0, read);
            }
            in.close();
            out.close();
            boolean deleteSuccess = inputFile.delete();
            if (!deleteSuccess) {
                JavaCIPUnknownScope.log.warn("Could not delete file '" + inputFile + "'");
            }
            JavaCIPUnknownScope.log.info("Successfully created gzip file '" + targetFile + "'.");
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.log.error("RuntimeException while creating GZIP.", e);
        } finally {
            StreamUtil.tryCloseStream(in);
            StreamUtil.tryCloseStream(out);
        }
        return targetFile;
    }
}
