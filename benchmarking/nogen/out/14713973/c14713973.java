class c14713973 {

    public void run() {
        try {
            File f = new File(JavaCIPUnknownScope.repository + JavaCIPUnknownScope.fileName);
            if (!f.exists()) {
                URL url = new URL(JavaCIPUnknownScope.urlString);
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                InputStream dis = url.openStream();
                File dir = new File(JavaCIPUnknownScope.repository);
                if (!dir.exists())
                    dir.mkdirs();
                f.createNewFile();
                FileOutputStream fos = new FileOutputStream(f);
                byte[] buffer = new byte[4096];
                int len = 0;
                while ((len = dis.read(buffer)) > -1) fos.write(buffer, 0, len);
                fos.close();
                dis.close();
            }
            JavaCIPUnknownScope.fireFileDownloadedListener(JavaCIPUnknownScope.fileName);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
