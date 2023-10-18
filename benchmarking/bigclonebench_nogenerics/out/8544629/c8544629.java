class c8544629 {

    protected void copyContent(String filename) throws IORuntimeException {
        InputStream in = null;
        try {
            String resourceDir = System.getProperty("resourceDir");
            File resource = new File(resourceDir, filename);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            if (resource.exists()) {
                in = new FileInputStream(resource);
            } else {
                in = JavaCIPUnknownScope.LOADER.getResourceAsStream(JavaCIPUnknownScope.RES_PKG + filename);
            }
            IOUtils.copy(in, out);
            JavaCIPUnknownScope.setResponseData(out.toByteArray());
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }
}
