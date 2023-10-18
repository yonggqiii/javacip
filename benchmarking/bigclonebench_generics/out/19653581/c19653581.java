class c19653581 {

    public List<SuspectFileProcessingStatus> retrieve() throws Exception {
        BufferedOutputStream bos = null;
        try {
            String listFilePath = GeneralUtils.generateAbsolutePath(JavaCIPUnknownScope.getDownloadDirectoryPath(), JavaCIPUnknownScope.getListName(), "/");
            listFilePath = listFilePath.concat(".xml");
            if (!new File(JavaCIPUnknownScope.getDownloadDirectoryPath()).exists()) {
                FileUtils.forceMkdir(new File(JavaCIPUnknownScope.getDownloadDirectoryPath()));
            }
            FileOutputStream listFileOutputStream = new FileOutputStream(listFilePath);
            bos = new BufferedOutputStream(listFileOutputStream);
            InputStream is = null;
            if (JavaCIPUnknownScope.getUseProxy()) {
                is = URLUtils.getResponse(JavaCIPUnknownScope.getUrl(), JavaCIPUnknownScope.getUserName(), JavaCIPUnknownScope.getPassword(), URLUtils.HTTP_GET_METHOD, JavaCIPUnknownScope.getProxyHost(), JavaCIPUnknownScope.getProxyPort());
                IOUtils.copyLarge(is, bos);
            } else {
                URLUtils.getResponse(JavaCIPUnknownScope.getUrl(), JavaCIPUnknownScope.getUserName(), JavaCIPUnknownScope.getPassword(), bos, null);
            }
            bos.flush();
            bos.close();
            File listFile = new File(listFilePath);
            if (!listFile.exists()) {
                throw new IllegalStateException("The list file did not get created");
            }
            if (JavaCIPUnknownScope.isLoggingInfo()) {
                JavaCIPUnknownScope.logInfo("Downloaded list file : " + listFile);
            }
            List<SuspectFileProcessingStatus> sfpsList = new ArrayList<SuspectFileProcessingStatus>();
            String loadType = GeneralConstants.LOAD_TYPE_FULL;
            String feedType = GeneralConstants.EMPTY_TOKEN;
            String listName = JavaCIPUnknownScope.getListName();
            String errorCode = "";
            String description = "";
            SuspectFileProcessingStatus sfps = JavaCIPUnknownScope.getSuspectsLoaderService().storeFileIntoListIncomingDir(listFile, loadType, feedType, listName, errorCode, description);
            sfpsList.add(sfps);
            if (JavaCIPUnknownScope.isLoggingInfo()) {
                JavaCIPUnknownScope.logInfo("Retrieved list file with SuspectFileProcessingStatus: " + sfps);
            }
            return sfpsList;
        } finally {
            if (null != bos) {
                bos.close();
            }
        }
    }
}
