class c14640597 {

    private void reloadData(String dataSourceUrl) {
        try {
            URL url = new URL(dataSourceUrl);
            InputStream is = url.openStream();
            if (JavaCIPUnknownScope.progressMonitor.isCanceled() == false) {
                JavaCIPUnknownScope.progressMonitor.setNote("Building classifications...");
                JavaCIPUnknownScope.progressMonitor.setProgress(2);
                JavaCIPUnknownScope.mediator.loadClassificationTree(is);
            }
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }
}
