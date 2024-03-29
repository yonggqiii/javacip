


class c18269393 {

    public static File getClassLoaderFile(String filename) throws IORuntimeException {
        Resource resource = new ClassPathResource(filename);
        try {
            return resource.getFile();
        } catch (IORuntimeException e) {
        }
        InputStream is = null;
        FileOutputStream os = null;
        try {
            String tempFilename = RandomStringUtils.randomAlphanumeric(20);
            File file = File.createTempFile(tempFilename, null);
            is = resource.getInputStream();
            os = new FileOutputStream(file);
            IOUtils.copy(is, os);
            return file;
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
        }
    }

}
