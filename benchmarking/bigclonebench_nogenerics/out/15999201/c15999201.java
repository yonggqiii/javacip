class c15999201 {

    public void downloadByUUID(final UUID uuid, final HttpServletRequest request, final HttpServletResponse response) throws IORuntimeException {
        if (!JavaCIPUnknownScope.exportsInProgress.containsKey(uuid)) {
            throw new IllegalStateRuntimeException("No download with UUID: " + uuid);
        }
        final File compressedFile = JavaCIPUnknownScope.exportsInProgress.get(uuid).file;
        JavaCIPUnknownScope.logger.debug("File size: " + compressedFile.length());
        OutputStream output = null;
        InputStream fileInputStream = null;
        try {
            output = response.getOutputStream();
            JavaCIPUnknownScope.prepareResponse(request, response, compressedFile);
            fileInputStream = new FileInputStream(compressedFile);
            IOUtils.copy(fileInputStream, output);
            output.flush();
        } finally {
            IOUtils.closeQuietly(fileInputStream);
            IOUtils.closeQuietly(output);
        }
    }
}
