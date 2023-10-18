class c4975451 {

    public void performUpdates(List<PackageDescriptor> downloadList, ProgressListener progressListener) throws IOException, UpdateServiceException_Exception {
        int i = 0;
        int len = downloadList.size();
        try {
            for (PackageDescriptor desc : downloadList) {
                int pid = desc.getPackageId();
                int version = desc.getVersion();
                String platformName = desc.getPlatformName();
                String urlString = JavaCIPUnknownScope.service.getDownloadURL(pid, version, platformName);
                String name = desc.getName();
                String licenseName = desc.getLicenseName();
                String typeName = desc.getPackageTypeName();
                int minProgress = 20 + 80 * i / len;
                int maxProgress = 20 + 80 * (i + 1) / len;
                boolean incremental = UpdateManager.isIncrementalUpdate();
                String withBaseVersion = "";
                LogService root = LogService.getRoot();
                if (typeName.equals("RAPIDMINER_PLUGIN")) {
                    ManagedExtension extension = ManagedExtension.getOrCreate(pid, name, licenseName);
                    String baseVersion = extension.getLatestInstalledVersionBefore(version);
                    incremental &= baseVersion != null;
                    String baseVersionURIEncoded = URLEncoder.encode(baseVersion, "UTF-8");
                    if (incremental)
                        withBaseVersion = "?baseVersion=" + baseVersionURIEncoded;
                    URI uri1 = UpdateManager.getUpdateServerURI(urlString + withBaseVersion);
                    URL url = uri1.toURL();
                    if (incremental) {
                        root.info("Updating " + pid + " incrementally.");
                        try {
                            Stream s = JavaCIPUnknownScope.openStream(url, progressListener, minProgress, maxProgress);
                            JavaCIPUnknownScope.updatePluginIncrementally(extension, s, baseVersion, version);
                        } catch (IOException e) {
                            root.warning("Incremental Update failed. Trying to fall back on non incremental Update...");
                            incremental = false;
                        }
                    }
                    if (!incremental) {
                        root.info("Updating " + pid + ".");
                        Stream s = JavaCIPUnknownScope.openStream(url, progressListener, minProgress, maxProgress);
                        JavaCIPUnknownScope.updatePlugin(extension, s, version);
                    }
                    extension.addAndSelectVersion(version);
                } else {
                    String longVersion = RapidMiner.getLongVersion();
                    String longVersionURIEncoded = URLEncoder.encode(longVersion, "UTF-8");
                    if (incremental)
                        withBaseVersion = "?baseVersion=" + longVersionURIEncoded;
                    URI uri2 = UpdateManager.getUpdateServerURI(urlString + withBaseVersion);
                    URL url = uri2.toURL();
                    root.info("Updating RapidMiner core.");
                    Stream s = JavaCIPUnknownScope.openStream(url, progressListener, minProgress, maxProgress);
                    JavaCIPUnknownScope.updateRapidMiner(s, version);
                }
                i++;
                progressListener.setCompleted(20 + 80 * i / downloadList.size());
            }
        } catch (URISyntaxException e) {
            throw new IOException(e);
        } finally {
            progressListener.complete();
        }
    }
}
