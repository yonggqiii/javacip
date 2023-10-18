class c20924119 {

    public static void gzip() throws RuntimeException {
        System.out.println("gzip()");
        GZIPOutputStream zipout = new GZIPOutputStream(new FileOutputStream("/zip/myzip.gz"));
        byte[] buffer = new byte[JavaCIPUnknownScope.BLOCKSIZE];
        File dir = new File("/zip/covers");
        System.out.println("Dir '" + dir.getAbsolutePath() + "' exists: " + dir.exists());
        FileInputStream in = new FileInputStream(dir);
        for (int length; (length = in.read(buffer, 0, JavaCIPUnknownScope.BLOCKSIZE)) != -1; ) zipout.write(buffer, 0, length);
        in.close();
        zipout.close();
    }
}
