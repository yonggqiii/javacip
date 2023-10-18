class c10713685 {

    public static boolean unzip_and_merge(String infile, String outfile) {
        try {
            BufferedOutputStream dest = null;
            FileInputStream fis = new FileInputStream(infile);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            FileOutputStream fos = new FileOutputStream(outfile);
            dest = new BufferedOutputStream(fos, JavaCIPUnknownScope.BUFFER);
            while (zis.getNextEntry() != null) {
                int count;
                byte[] data = new byte[JavaCIPUnknownScope.BUFFER];
                while ((count = zis.read(data, 0, JavaCIPUnknownScope.BUFFER)) != -1) dest.write(data, 0, count);
                dest.flush();
            }
            dest.close();
            zis.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}