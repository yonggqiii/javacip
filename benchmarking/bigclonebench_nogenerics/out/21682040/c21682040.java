class c21682040 {

    public void extractFrinika() throws RuntimeException {
        FileInputStream fis = new FileInputStream(JavaCIPUnknownScope.frinikaFile);
        JavaCIPUnknownScope.progressBar.setIndeterminate(true);
        ZipInputStream zis = new ZipInputStream(fis);
        ZipEntry ze = zis.getNextEntry();
        while (ze != null) {
            JavaCIPUnknownScope.showMessage("Extracting: " + ze.getName());
            File file = new File(JavaCIPUnknownScope.installDirName + "/" + ze.getName());
            if (ze.isDirectory())
                file.mkdir();
            else {
                FileOutputStream fos = new FileOutputStream(file);
                byte[] b = new byte[JavaCIPUnknownScope.BUFSIZE];
                int c;
                while ((c = zis.read(b)) != -1) fos.write(b, 0, c);
                fos.close();
            }
            ze = zis.getNextEntry();
        }
    }
}
