class c14787421 {

    private void saveStateAsLast(URL url) {
        InputStream sourceStream = null;
        OutputStream destinationStream = null;
        File lastBundlesTxt = JavaCIPUnknownScope.getLastBundleInfo();
        try {
            try {
                destinationStream = new FileOutputStream(lastBundlesTxt);
                sourceStream = url.openStream();
                SimpleConfiguratorUtils.transferStreams(sourceStream, destinationStream);
            } finally {
                if (destinationStream != null)
                    destinationStream.close();
                if (sourceStream != null)
                    sourceStream.close();
            }
        } catch (IORuntimeException e) {
        }
    }
}
