class c11452667 {

    private void processData(InputStream raw) {
        String fileName = JavaCIPUnknownScope.remoteName;
        if (JavaCIPUnknownScope.localName != null) {
            fileName = JavaCIPUnknownScope.localName;
        }
        try {
            FileOutputStream fos = new FileOutputStream(new File(fileName), true);
            IOUtils.copy(raw, fos);
            JavaCIPUnknownScope.LOG.info("ok");
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.LOG.error("error writing file", e);
        }
    }
}
