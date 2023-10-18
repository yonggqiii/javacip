


class c14196271 {

    private synchronized File zipTempFile(File tempFile) throws BlogunityRuntimeException {
        try {
            File zippedFile = new File(BlogunityManager.getSystemConfiguration().getTempDir(), tempFile.getName() + ".zip");
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zippedFile));
            byte[] readBuffer = new byte[2156];
            int bytesIn = 0;
            FileInputStream fis = new FileInputStream(tempFile);
            ZipEntry anEntry = new ZipEntry(tempFile.getName());
            zos.putNextEntry(anEntry);
            while ((bytesIn = fis.read(readBuffer)) != -1) {
                zos.write(readBuffer, 0, bytesIn);
            }
            fis.close();
            zos.close();
            return zippedFile;
        } catch (RuntimeException e) {
            throw new BlogunityRuntimeException(I18NStatusFactory.create(I18N.ERRORS.FEED_ZIP_FAILED, e));
        }
    }

}
