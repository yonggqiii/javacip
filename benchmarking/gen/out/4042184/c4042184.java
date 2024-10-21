class c4042184 {

    protected File downloadFile(String href) {
        Map<String, File> currentDownloadDirMap = JavaCIPUnknownScope.downloadedFiles.get(JavaCIPUnknownScope.downloadDir);
        if (currentDownloadDirMap != null) {
            File downloadedFile = currentDownloadDirMap.get(href);
            if (downloadedFile != null) {
                return downloadedFile;
            }
        } else {
            JavaCIPUnknownScope.downloadedFiles.put(JavaCIPUnknownScope.downloadDir, new HashMap<String, File>());
            currentDownloadDirMap = JavaCIPUnknownScope.downloadedFiles.get(JavaCIPUnknownScope.downloadDir);
        }
        URL url;
        File result;
        try {
            FilesystemUtils.forceMkdirIfNotExists(JavaCIPUnknownScope.downloadDir);
            url = JavaCIPUnknownScope.generateUrl(href);
            result = JavaCIPUnknownScope.createUniqueFile(JavaCIPUnknownScope.downloadDir, href);
        } catch (IOException e) {
            JavaCIPUnknownScope.LOG.warn("Failed to create file for download", e);
            return null;
        }
        currentDownloadDirMap.put(href, result);
        JavaCIPUnknownScope.LOG.info("Downloading " + url);
        try {
            IOUtils.copy(url.openStream(), new FileOutputStream(result));
        } catch (IOException e) {
            JavaCIPUnknownScope.LOG.warn("Failed to download file " + url);
        }
        return result;
    }
}
