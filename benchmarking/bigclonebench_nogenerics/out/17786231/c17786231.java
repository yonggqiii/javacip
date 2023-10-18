class c17786231 {

    private void downloadFile(final String downloadUrl, final String destinationFile) throws IORuntimeException {
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(downloadUrl);
        final File outputFile = new File(destinationFile);
        JavaCIPUnknownScope.createParentDirectories(outputFile);
        FileOutputStream outputStream;
        outputStream = new FileOutputStream(outputFile);
        final HttpResponse response = client.execute(httpGet);
        if (JavaCIPUnknownScope.isInterrupted()) {
            outputStream.close();
            return;
        }
        final HttpEntity entity = response.getEntity();
        InputStream inputStream = null;
        try {
            if (entity != null) {
                inputStream = entity.getContent();
                CopyStreamStatusCallback callback = new CopyStreamStatusCallback() {

                    public long getSkipBetweenUpdates() {
                        return entity.getContentLength() * 2 / JavaCIPUnknownScope.PERCENTAGE_BASE;
                    }

                    public void onUpdate(final long copiedLength) {
                        int percentage = (int) (copiedLength * JavaCIPUnknownScope.PERCENTAGE_BASE / entity.getContentLength());
                        JavaCIPUnknownScope.handleUpdate(JavaCIPUnknownScope.STATUS_DOWNLOADING, percentage);
                    }
                };
                JavaCIPUnknownScope.copyStreams(inputStream, outputStream, callback);
            }
        } finally {
            try {
                outputStream.close();
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IORuntimeException e) {
                Log.v(DictionaryForMIDs.LOG_TAG, "RuntimeException while closing stream: " + e);
            }
        }
    }
}
