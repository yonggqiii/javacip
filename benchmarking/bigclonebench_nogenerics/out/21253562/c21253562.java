class c21253562 {

    public void fileUpload(UploadEvent uploadEvent) {
        FileOutputStream tmpOutStream = null;
        try {
            JavaCIPUnknownScope.tmpUpload = File.createTempFile("projectImport", ".xml");
            tmpOutStream = new FileOutputStream(JavaCIPUnknownScope.tmpUpload);
            IOUtils.copy(uploadEvent.getInputStream(), tmpOutStream);
            JavaCIPUnknownScope.panel.setGeneralMessage("Project file " + uploadEvent.getFileName() + " uploaded and ready for import.");
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.panel.setGeneralMessage("Could not upload file: " + e);
        } finally {
            if (tmpOutStream != null) {
                IOUtils.closeQuietly(tmpOutStream);
            }
        }
    }
}
