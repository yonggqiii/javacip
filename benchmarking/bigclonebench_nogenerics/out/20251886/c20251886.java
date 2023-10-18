class c20251886 {

    private void initJarURL() {
        try {
            URL url = JavaCIPUnknownScope.getKwantuJarURLInMavenRepo(JavaCIPUnknownScope.artifactId, JavaCIPUnknownScope.version);
            File tempJarFile = File.createTempFile(JavaCIPUnknownScope.artifactId + "-" + JavaCIPUnknownScope.version, ".jar");
            OutputStream out = new FileOutputStream(tempJarFile);
            InputStream in = url.openStream();
            int length = 0;
            byte[] bytes = new byte[2048];
            while ((length = in.read(bytes)) > 0) {
                out.write(bytes, 0, length);
            }
            in.close();
            out.close();
            JavaCIPUnknownScope.jarURL = tempJarFile.toURI().toURL();
        } catch (IORuntimeException ex) {
            throw new KwantuFaultRuntimeException(ex);
        }
    }
}
