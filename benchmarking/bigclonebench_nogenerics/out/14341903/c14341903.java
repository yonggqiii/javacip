class c14341903 {

    private static void processFile(String file) throws IORuntimeException {
        FileInputStream in = new FileInputStream(file);
        int read = 0;
        byte[] buf = new byte[2048];
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        while ((read = in.read(buf)) > 0) bout.write(buf, 0, read);
        in.close();
        String converted = bout.toString().replaceAll("@project.name@", JavaCIPUnknownScope.projectNameS).replaceAll("@base.package@", JavaCIPUnknownScope.basePackageS).replaceAll("@base.dir@", JavaCIPUnknownScope.baseDir).replaceAll("@webapp.dir@", JavaCIPUnknownScope.webAppDir).replaceAll("path=\"target/classes\"", "path=\"src/main/webapp/WEB-INF/classes\"");
        FileOutputStream out = new FileOutputStream(file);
        out.write(converted.getBytes());
        out.close();
    }
}
