class c20924120 {

    public static void gunzip() throws RuntimeException {
        System.out.println("gunzip()");
        GZIPInputStream zipin = new GZIPInputStream(new FileInputStream("/zip/myzip.gz"));
        byte[] buffer = new byte[JavaCIPUnknownScope.BLOCKSIZE];
        FileOutputStream out = new FileOutputStream("/zip/covers");
        for (int length; (length = zipin.read(buffer, 0, JavaCIPUnknownScope.BLOCKSIZE)) != -1; ) out.write(buffer, 0, length);
        out.close();
        zipin.close();
    }
}
