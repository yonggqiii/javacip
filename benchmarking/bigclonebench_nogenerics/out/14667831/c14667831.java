class c14667831 {

    public void concatFiles() throws IORuntimeException {
        Writer writer = null;
        try {
            final File targetFile = new File(JavaCIPUnknownScope.getTargetDirectory(), JavaCIPUnknownScope.getTargetFile());
            targetFile.getParentFile().mkdirs();
            if (null != JavaCIPUnknownScope.getEncoding()) {
                JavaCIPUnknownScope.getLog().info("Writing aggregated file with encoding '" + JavaCIPUnknownScope.getEncoding() + "'");
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile), JavaCIPUnknownScope.getEncoding()));
            } else {
                JavaCIPUnknownScope.getLog().info("WARNING: writing aggregated file with system encoding");
                writer = new FileWriter(targetFile);
            }
            for (File file : JavaCIPUnknownScope.getFiles()) {
                Reader reader = null;
                try {
                    if (null != JavaCIPUnknownScope.getEncoding()) {
                        JavaCIPUnknownScope.getLog().info("Reading file " + file.getCanonicalPath() + " with encoding  '" + JavaCIPUnknownScope.getEncoding() + "'");
                        reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), JavaCIPUnknownScope.getEncoding()));
                    } else {
                        JavaCIPUnknownScope.getLog().info("WARNING: Reading file " + file.getCanonicalPath() + " with system encoding");
                        reader = new FileReader(file);
                    }
                    IOUtils.copy(reader, writer);
                    final String delimiter = JavaCIPUnknownScope.getDelimiter();
                    if (delimiter != null) {
                        writer.write(delimiter.toCharArray());
                    }
                } finally {
                    IOUtils.closeQuietly(reader);
                }
            }
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }
}
