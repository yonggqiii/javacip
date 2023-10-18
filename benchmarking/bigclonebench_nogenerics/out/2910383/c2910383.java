class c2910383 {

    public String downloadAndOpen(JProgressBar bar) {
        long size = 0;
        try {
            size = JavaCIPUnknownScope.photo.getSize();
        } catch (ServiceRuntimeException ex) {
            ex.printStackTrace();
        }
        try {
            bar.setMaximum((int) size);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        bar.setValue(0);
        JavaCIPUnknownScope.image = new File("TMP/" + JavaCIPUnknownScope.photo.getTitle().getPlainText());
        try {
            if (!JavaCIPUnknownScope.image.exists()) {
                JavaCIPUnknownScope.image.createNewFile();
                JavaCIPUnknownScope.image.deleteOnExit();
                URL url = null;
                BufferedOutputStream fOut = null;
                try {
                    url = new URL(JavaCIPUnknownScope.photo.getMediaContents().get(0).getUrl());
                    InputStream html = null;
                    html = url.openStream();
                    fOut = new BufferedOutputStream(new FileOutputStream(JavaCIPUnknownScope.image));
                    byte[] buffer = new byte[32 * 1024];
                    int bytesRead = 0;
                    int in = 0;
                    while ((bytesRead = html.read(buffer)) != -1) {
                        in += bytesRead;
                        bar.setValue(in);
                        fOut.write(buffer, 0, bytesRead);
                    }
                    html.close();
                    fOut.close();
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        return JavaCIPUnknownScope.image.getAbsolutePath();
    }
}
