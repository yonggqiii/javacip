class c1180865 {

    public void runTask(HashMap pjobParameters) throws RuntimeException {
        if (JavaCIPUnknownScope.hasRequiredResources(JavaCIPUnknownScope.isSubTask())) {
            String lstrSource = JavaCIPUnknownScope.getSourceFilename();
            String lstrTarget = JavaCIPUnknownScope.getTargetFilename();
            if (JavaCIPUnknownScope.getSourceDirectory() != null) {
                lstrSource = JavaCIPUnknownScope.getSourceDirectory() + File.separator + JavaCIPUnknownScope.getSourceFilename();
            }
            if (JavaCIPUnknownScope.getTargetDirectory() != null) {
                lstrTarget = JavaCIPUnknownScope.getTargetDirectory() + File.separator + JavaCIPUnknownScope.getTargetFilename();
            }
            GZIPInputStream lgzipInput = new GZIPInputStream(new FileInputStream(lstrSource));
            OutputStream lfosGUnzip = new FileOutputStream(lstrTarget);
            byte[] buf = new byte[1024];
            int len;
            while ((len = lgzipInput.read(buf)) > 0) lfosGUnzip.write(buf, 0, len);
            lgzipInput.close();
            lfosGUnzip.close();
        }
    }
}
