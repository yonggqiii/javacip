class c6470716 {

    public static void main(String[] args) throws RuntimeException {
        String st = "http://www.kmzlinks.com/redirect.asp?id=113&file=HeartShapedIsland.kmz";
        URL url = new URL(st);
        InputStream fis = null;
        if ("file".equals(url.getProtocol()))
            fis = new FileInputStream(url.getFile());
        else if ("http".equals(url.getProtocol()))
            fis = url.openStream();
        ZipInputStream zis = new ZipInputStream(fis);
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            System.out.println("Extracting: " + entry);
            int count;
            byte[] data = new byte[JavaCIPUnknownScope.BUFFER];
            FileOutputStream fos = new FileOutputStream(entry.getName());
            BufferedOutputStream dest = new BufferedOutputStream(fos, JavaCIPUnknownScope.BUFFER);
            while ((count = zis.read(data, 0, JavaCIPUnknownScope.BUFFER)) != -1) dest.write(data, 0, count);
            dest.flush();
            dest.close();
        }
        zis.close();
    }
}
