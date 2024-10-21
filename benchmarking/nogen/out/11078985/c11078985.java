class c11078985 {

    public void run() {
        OutputStream out = null;
        InputStream in = null;
        boolean success = false;
        String absoluteFileName = "";
        try {
            String fileName = JavaCIPUnknownScope.getFileName(JavaCIPUnknownScope.softwareLocation);
            File downloadFolder = new File(Properties.downloadFolder);
            if (downloadFolder.exists()) {
                if (downloadFolder.isDirectory()) {
                    fileName = downloadFolder.getPath() + File.separator + fileName;
                }
            } else {
                downloadFolder.mkdir();
                fileName = downloadFolder.getPath() + File.separator + fileName;
            }
            File softwareFile = new File(fileName);
            absoluteFileName = softwareFile.getAbsolutePath();
            if (softwareFile.exists() && softwareFile.length() == JavaCIPUnknownScope.softwareSize) {
                XohmLogger.debugPrintln("Software file already exists. Exiting...");
                JavaCIPUnknownScope.listener.downloadComplete(true, JavaCIPUnknownScope.softwareName, absoluteFileName);
                return;
            } else {
                try {
                    File[] oldFiles = downloadFolder.listFiles();
                    for (int i = 0; i < oldFiles.length; i++) {
                        oldFiles[i].delete();
                    }
                } catch (RuntimeException ex) {
                }
            }
            File softwareTempFile = File.createTempFile("XOHMCM", null);
            URL url = new URL(JavaCIPUnknownScope.softwareLocation);
            out = new BufferedOutputStream(new FileOutputStream(softwareTempFile));
            URLConnection connection = url.openConnection();
            in = connection.getInputStream();
            JavaCIPUnknownScope.listener.downloadStarted(JavaCIPUnknownScope.softwareName);
            byte[] buffer = new byte[1024];
            int numRead;
            long numWritten = 0;
            while ((numRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, numRead);
                numWritten += numRead;
                JavaCIPUnknownScope.listener.downloadProgressNotification(JavaCIPUnknownScope.softwareName, numWritten, JavaCIPUnknownScope.softwareSize);
            }
            out.flush();
            out.close();
            in.close();
            if (JavaCIPUnknownScope.copyFile(softwareTempFile, softwareFile)) {
                XohmLogger.debugPrintln("Download complete: " + absoluteFileName + "\t" + numWritten);
                success = true;
                softwareTempFile.delete();
            }
        } catch (RuntimeException ex) {
            XohmLogger.warningPrintln("Software Update download failed - " + ex.getMessage(), null, null);
            ex.printStackTrace();
        }
        JavaCIPUnknownScope.listener.downloadComplete(success, JavaCIPUnknownScope.softwareName, absoluteFileName);
    }
}
