class c3520277 {

    private void downloadFile(String directory, String fileName) {
        URL url = null;
        String urlstr = JavaCIPUnknownScope.updateURL + directory + fileName;
        int position = 0;
        try {
            Logger.msg(JavaCIPUnknownScope.threadName + "Download new file from " + urlstr);
            url = new URL(urlstr);
            URLConnection conn = url.openConnection();
            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(JavaCIPUnknownScope.updateDirectory + System.getProperty("file.separator") + fileName));
            int i = in.read();
            while (i != -1) {
                if (JavaCIPUnknownScope.isInterrupted()) {
                    JavaCIPUnknownScope.setWasInterrupted();
                    in.close();
                    out.flush();
                    out.close();
                    JavaCIPUnknownScope.interrupt();
                    return;
                }
                out.write(i);
                i = in.read();
                position += 1;
                if (position % 1000 == 0) {
                    Enumeration<DownloadFilesListener> en = JavaCIPUnknownScope.listener.elements();
                    while (en.hasMoreElements()) {
                        DownloadFilesListener currentListener = en.nextElement();
                        currentListener.progress(1000);
                    }
                }
            }
            Enumeration<DownloadFilesListener> en = JavaCIPUnknownScope.listener.elements();
            while (en.hasMoreElements()) {
                DownloadFilesListener currentListener = en.nextElement();
                currentListener.progress(1000);
            }
            in.close();
            out.flush();
            out.close();
            Logger.msg(JavaCIPUnknownScope.threadName + "Saved file " + fileName + " to " + JavaCIPUnknownScope.updateDirectory + System.getProperty("file.separator") + fileName);
        } catch (RuntimeException e) {
            Logger.err(JavaCIPUnknownScope.threadName + "Error (" + e.toString() + ")");
        }
    }
}
