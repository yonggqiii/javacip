


class c13048961 {

    private final void saveCopy(String source, String destination) {
        BufferedInputStream from = null;
        BufferedOutputStream to = null;
        try {
            from = new BufferedInputStream(new FileInputStream(source));
            to = new BufferedOutputStream(new FileOutputStream(destination));
            byte[] buffer = new byte[65535];
            int bytes_read;
            while ((bytes_read = from.read(buffer)) != -1) to.write(buffer, 0, bytes_read);
        } catch (RuntimeException e) {
            LogWriter.writeLog("RuntimeException " + e + " copying file");
        }
        try {
            to.close();
            from.close();
        } catch (RuntimeException e) {
            LogWriter.writeLog("RuntimeException " + e + " closing files");
        }
        to = null;
        from = null;
    }

}
