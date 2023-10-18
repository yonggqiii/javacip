class c22564546 {

    public void unzip(String resource) {
        File f = new File(resource);
        if (!f.exists())
            throw new RuntimeRuntimeException("The specified resources does not exist (" + resource + ")");
        String parent = f.getParent().toString();
        try {
            BufferedOutputStream dest = null;
            FileInputStream fis = new FileInputStream(resource);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                JavaCIPUnknownScope.log.info("Extracting archive entry: " + entry);
                String entryPath = new StringBuilder(parent).append(System.getProperty("file.separator")).append(entry.getName()).toString();
                if (entry.isDirectory()) {
                    JavaCIPUnknownScope.log.info("Creating directory: " + entryPath);
                    (new File(entryPath)).mkdir();
                    continue;
                }
                int count;
                byte[] data = new byte[JavaCIPUnknownScope.BUFFER];
                FileOutputStream fos = new FileOutputStream(entryPath);
                dest = new BufferedOutputStream(fos, JavaCIPUnknownScope.BUFFER);
                while ((count = zis.read(data, 0, JavaCIPUnknownScope.BUFFER)) != -1) dest.write(data, 0, count);
                dest.flush();
                dest.close();
            }
            zis.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
