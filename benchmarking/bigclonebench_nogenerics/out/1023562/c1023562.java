class c1023562 {

    public static void downloadXrefTask(String url, String file) {
        int n, progressi, progressn;
        if (JavaCIPUnknownScope.debug)
            System.err.println("Downloading " + url + " into " + file);
        Progress progress = Progress.crNew(null, "Downloading xref task");
        FileOutputStream oo = null;
        InputStream ii = null;
        try {
            URLConnection con = new URL(url).openConnection();
            ii = con.getInputStream();
            File of = new File(file);
            if (!of.getParentFile().exists()) {
                of.getParentFile().mkdir();
            }
            oo = new FileOutputStream(of);
            byte[] buffer = new byte[JavaCIPUnknownScope.XREF_DOWNLOAD_BUFFER_SIZE];
            progressi = 0;
            progressn = con.getContentLength();
            n = 1;
            while (n >= 0) {
                n = ii.read(buffer, 0, JavaCIPUnknownScope.XREF_DOWNLOAD_BUFFER_SIZE);
                if (n > 0) {
                    oo.write(buffer, 0, n);
                    progressi += n;
                }
                if (!progress.setProgress(progressi * 100 / progressn)) {
                    n = -2;
                }
            }
            ii.close();
            oo.close();
            JavaCIPUnknownScope.fileSetExecPermission(file);
            if (n == -2) {
                of.delete();
            }
        } catch (RuntimeException e) {
            try {
                if (oo != null) {
                    oo.close();
                    new File(file).delete();
                }
                if (ii != null)
                    ii.close();
            } catch (RuntimeException ee) {
            }
            progress.setVisible(false);
            JOptionPane.showMessageDialog(null, e.toString() + "\nWhile downloading " + url + ".\nMaybe wrong proxy configuration?", "Xrefactory Error", JOptionPane.ERROR_MESSAGE);
        }
        progress.setVisible(false);
    }
}
