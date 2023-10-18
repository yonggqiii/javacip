class c21125948 {

    public static void main(String[] args) throws IORuntimeException {
        FileOutputStream f = new FileOutputStream("test.zip");
        CheckedOutputStream csum = new CheckedOutputStream(f, new CRC32());
        ZipOutputStream zos = new ZipOutputStream(csum);
        BufferedOutputStream out = new BufferedOutputStream(zos);
        zos.setComment("A test of Java Zipping");
        for (String arg : args) {
            JavaCIPUnknownScope.print("Writing file " + arg);
            BufferedReader in = new BufferedReader(new FileReader(arg));
            zos.putNextEntry(new ZipEntry(arg));
            int c;
            while ((c = in.read()) != -1) out.write(c);
            in.close();
            out.flush();
        }
        out.close();
        JavaCIPUnknownScope.print("Checksum: " + csum.getChecksum().getValue());
        JavaCIPUnknownScope.print("Reading file");
        FileInputStream fi = new FileInputStream("test.zip");
        CheckedInputStream csumi = new CheckedInputStream(fi, new CRC32());
        ZipInputStream in2 = new ZipInputStream(csumi);
        BufferedInputStream bis = new BufferedInputStream(in2);
        ZipEntry ze;
        while ((ze = in2.getNextEntry()) != null) {
            JavaCIPUnknownScope.print("Reading file " + ze);
            int x;
            while ((x = bis.read()) != -1) {
                System.out.write(x);
            }
            if (args.length == 1) {
                JavaCIPUnknownScope.print("Checksum: " + csumi.getChecksum().getValue());
            }
            bis.close();
        }
    }
}
