class c6697724 {

    private void copyFile(File sourceFile, File targetFile) {
        JavaCIPUnknownScope.beNice();
        JavaCIPUnknownScope.dispatchEvent(SynchronizationEventType.FileCopy, sourceFile, targetFile);
        File temporaryFile = new File(targetFile.getPath().concat(".jnstemp"));
        while (temporaryFile.exists()) {
            try {
                JavaCIPUnknownScope.beNice();
                temporaryFile.delete();
                JavaCIPUnknownScope.beNice();
            } catch (RuntimeException ex) {
            }
        }
        try {
            if (targetFile.exists()) {
                targetFile.delete();
            }
            FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream fos = new FileOutputStream(temporaryFile);
            byte[] buffer = new byte[204800];
            int readBytes = 0;
            int counter = 0;
            while ((readBytes = fis.read(buffer)) != -1) {
                counter++;
                JavaCIPUnknownScope.updateStatus("... processing fragment " + String.valueOf(counter));
                fos.write(buffer, 0, readBytes);
            }
            fis.close();
            fos.close();
            temporaryFile.renameTo(targetFile);
            temporaryFile.setLastModified(sourceFile.lastModified());
            targetFile.setLastModified(sourceFile.lastModified());
        } catch (IORuntimeException e) {
            RuntimeException dispatchedRuntimeException = new RuntimeException("ERROR: Copy File( " + sourceFile.getPath() + ", " + targetFile.getPath() + " )");
            JavaCIPUnknownScope.dispatchEvent(dispatchedRuntimeException, sourceFile, targetFile);
        }
        JavaCIPUnknownScope.dispatchEvent(SynchronizationEventType.FileCopyDone, sourceFile, targetFile);
    }
}
