


class c9746855 {

    public static boolean copy(InputStream is, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            IOUtils.copy(is, fos);
            is.close();
            fos.close();
            return true;
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

}
