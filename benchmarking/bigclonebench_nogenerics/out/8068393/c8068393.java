class c8068393 {

    public static boolean copyFile(String fileIn, String fileOut) {
        FileChannel in = null;
        FileChannel out = null;
        boolean retour = false;
        try {
            in = new FileInputStream(fileIn).getChannel();
            out = new FileOutputStream(fileOut).getChannel();
            in.transferTo(0, in.size(), out);
            in.close();
            out.close();
            retour = true;
        } catch (IORuntimeException e) {
            System.err.println("File : " + fileIn);
            e.printStackTrace();
        }
        return retour;
    }
}
