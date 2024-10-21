class c8983115 {

    public void run() {
        ShareFolder part = (ShareFolder) ObjectClone.clone(JavaCIPUnknownScope.readers[JavaCIPUnknownScope.j]);
        ShareFileReader reader = new ShareFileReader(JavaCIPUnknownScope.readers[JavaCIPUnknownScope.j], JavaCIPUnknownScope.files[0]);
        ShareFileWriter writer = new ShareFileWriter(part, new File("Downloads/" + JavaCIPUnknownScope.readers[JavaCIPUnknownScope.j].getName()));
        long tot = 0;
        byte[] b = new byte[(int) (Math.random() * 10000)];
        while (tot < JavaCIPUnknownScope.readers[JavaCIPUnknownScope.j].getSize()) {
            reader.read(b);
            byte[] bwrite = new byte[(int) (Math.random() * 10000) + b.length];
            System.arraycopy(b, 0, bwrite, 0, b.length);
            writer.write(bwrite, b.length);
            tot += b.length;
        }
        JavaCIPUnknownScope.done++;
        System.out.println((int) (JavaCIPUnknownScope.done * 100.0 / JavaCIPUnknownScope.PARTS) + "% Complete");
    }
}
