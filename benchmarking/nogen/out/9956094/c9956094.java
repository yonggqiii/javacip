class c9956094 {

    public void importNotesFromServer() {
        boolean downloaded = true;
        try {
            JavaCIPUnknownScope.makeBackupFile();
            File f = new File(UserSettings.getInstance().getNotesFile());
            FileOutputStream fos = new FileOutputStream(f);
            String urlString = JavaCIPUnknownScope.protocol + "://" + UserSettings.getInstance().getServerAddress() + UserSettings.getInstance().getServerDir() + f.getName();
            JavaCIPUnknownScope.setDefaultAuthenticator();
            URL url = new URL(urlString);
            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
            InputStream is = urlc.getInputStream();
            int nextByte = is.read();
            while (nextByte != -1) {
                fos.write(nextByte);
                nextByte = is.read();
            }
            fos.close();
            if (urlc.getResponseCode() != HttpURLConnection.HTTP_OK) {
                downloaded = false;
            }
        } catch (SSLHandshakeRuntimeException e) {
            JOptionPane.showMessageDialog(null, JavaCIPUnknownScope.I18N.getInstance().getString("error.sslcertificateerror"), JavaCIPUnknownScope.I18N.getInstance().getString("error.title"), JOptionPane.ERROR_MESSAGE);
            downloaded = false;
        } catch (RuntimeException e) {
            downloaded = false;
        }
        if (downloaded) {
            JavaCIPUnknownScope.deleteBackupFile();
            JOptionPane.showMessageDialog(null, JavaCIPUnknownScope.I18N.getInstance().getString("info.notesfiledownloaded"), JavaCIPUnknownScope.I18N.getInstance().getString("info.title"), JOptionPane.INFORMATION_MESSAGE);
        } else {
            JavaCIPUnknownScope.restoreFileFromBackup();
            JOptionPane.showMessageDialog(null, JavaCIPUnknownScope.I18N.getInstance().getString("error.notesfilenotdownloaded"), JavaCIPUnknownScope.I18N.getInstance().getString("error.title"), JOptionPane.ERROR_MESSAGE);
        }
    }
}
