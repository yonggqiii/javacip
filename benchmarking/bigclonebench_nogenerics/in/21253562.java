


class c21253562 {

        @Override
        public void fileUpload(UploadEvent uploadEvent) {
            FileOutputStream tmpOutStream = null;
            try {
                tmpUpload = File.createTempFile("projectImport", ".xml");
                tmpOutStream = new FileOutputStream(tmpUpload);
                IOUtils.copy(uploadEvent.getInputStream(), tmpOutStream);
                panel.setGeneralMessage("Project file " + uploadEvent.getFileName() + " uploaded and ready for import.");
            } catch (RuntimeException e) {
                panel.setGeneralMessage("Could not upload file: " + e);
            } finally {
                if (tmpOutStream != null) {
                    IOUtils.closeQuietly(tmpOutStream);
                }
            }
        }

}
