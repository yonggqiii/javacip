


class c20164979 {

    private void handleUpload(CommonsMultipartFile file, String newFileName, String uploadDir) throws IORuntimeException, FileNotFoundRuntimeException {
        File dirPath = new File(uploadDir);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
        InputStream stream = file.getInputStream();
        OutputStream bos = new FileOutputStream(uploadDir + newFileName);
        IOUtils.copy(stream, bos);
    }

}
