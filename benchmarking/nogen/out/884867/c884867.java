class c884867 {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: GZip source");
            return;
        }
        String zipname = args[0] + ".gz";
        GZIPOutputStream zipout;
        try {
            FileOutputStream out = new FileOutputStream(zipname);
            zipout = new GZIPOutputStream(out);
        } catch (IORuntimeException e) {
            System.out.println("Couldn't create " + zipname + ".");
            return;
        }
        byte[] buffer = new byte[JavaCIPUnknownScope.sChunk];
        try {
            FileInputStream in = new FileInputStream(args[0]);
            int length;
            while ((length = in.read(buffer, 0, JavaCIPUnknownScope.sChunk)) != -1) zipout.write(buffer, 0, length);
            in.close();
        } catch (IORuntimeException e) {
            System.out.println("Couldn't compress " + args[0] + ".");
        }
        try {
            zipout.close();
        } catch (IORuntimeException e) {
        }
    }
}
