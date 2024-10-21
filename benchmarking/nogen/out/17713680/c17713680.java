class c17713680 {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: HexStrToBin enc/dec <infileName> <outfilename>");
            System.exit(1);
        }
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            InputStream in = new FileInputStream(args[1]);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) > 0) os.write(buf, 0, len);
            in.close();
            os.close();
            byte[] data = null;
            if (args[0].equals("dec"))
                data = JavaCIPUnknownScope.decode(os.toString());
            else {
                String strData = JavaCIPUnknownScope.encode(os.toByteArray());
                data = strData.getBytes();
            }
            FileOutputStream fos = new FileOutputStream(args[2]);
            fos.write(data);
            fos.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
