class c18976770 {

    public void compressImage(InputStream input, String output, DjatokaEncodeParam params) throws DjatokaRuntimeException {
        if (params == null)
            params = new DjatokaEncodeParam();
        File inputFile;
        try {
            inputFile = File.createTempFile("tmp", ".tif");
            inputFile.deleteOnExit();
            IOUtils.copyStream(input, new FileOutputStream(inputFile));
            if (params.getLevels() == 0) {
                ImageRecord dim = ImageRecordUtils.getImageDimensions(inputFile.getAbsolutePath());
                params.setLevels(ImageProcessingUtils.getLevelCount(dim.getWidth(), dim.getHeight()));
                dim = null;
            }
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.logger.error(e, e);
            throw new DjatokaRuntimeException(e);
        }
        compressImage(inputFile.getAbsolutePath(), output, params);
        if (inputFile != null)
            inputFile.delete();
    }
}
