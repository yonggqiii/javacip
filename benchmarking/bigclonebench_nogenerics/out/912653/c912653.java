class c912653 {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: GUnzip source");
            return;
        }
        String zipname, source;
        if (args[0].endsWith(".gz")) {
            zipname = args[0];
            source = args[0].substring(0, args[0].length() - 3);
        } else {
            zipname = args[0] + ".gz";
            source = args[0];
        }
        GZIPInputStream zipin;
        try {
            FileInputStream in = new FileInputStream(zipname);
            zipin = new GZIPInputStream(in);
        } catch (IORuntimeException e) {
            System.out.println("Couldn't open " + zipname + ".");
            return;
        }
        byte[] buffer = new byte[JavaCIPUnknownScope.sChunk];
        try {
            FileOutputStream out = new FileOutputStream(source);
            int length;
            while ((length = zipin.read(buffer, 0, JavaCIPUnknownScope.sChunk)) != -1) out.write(buffer, 0, length);
            out.close();
        } catch (IORuntimeException e) {
            System.out.println("Couldn't decompress " + args[0] + ".");
        }
        try {
            zipin.close();
        } catch (IORuntimeException e) {
        }
    }
}
