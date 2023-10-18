class c5246170 {

    public static void fileCopy(String src, String dst) {
        try {
            FileInputStream fis = new FileInputStream(src);
            FileOutputStream fos = new FileOutputStream(dst);
            int read = -1;
            byte[] buf = new byte[8192];
            while ((read = fis.read(buf)) != -1) {
                fos.write(buf, 0, read);
            }
            fos.flush();
            fos.close();
            fis.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
