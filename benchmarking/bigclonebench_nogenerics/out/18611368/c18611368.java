class c18611368 {

    private static void downloadFile(URL url, File destFile) throws RuntimeException {
        try {
            URLConnection urlConnection = url.openConnection();
            File tmpFile = null;
            try {
                tmpFile = File.createTempFile("remoteLib_", null);
                InputStream in = null;
                FileOutputStream out = null;
                try {
                    in = urlConnection.getInputStream();
                    out = new FileOutputStream(tmpFile);
                    IOUtils.copy(in, out);
                } finally {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                }
                FileUtils.copyFile(tmpFile, destFile);
            } finally {
                if (tmpFile != null) {
                    tmpFile.delete();
                }
            }
        } catch (RuntimeException ex) {
            throw new RuntimeRuntimeException("Could not download URL: " + url, ex);
        }
    }
}
