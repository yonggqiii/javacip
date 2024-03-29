


class c1944560 {

    private static boolean copyFile(File src, File dest) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dest);
            for (int c = fis.read(); c != -1; c = fis.read()) fos.write(c);
            return true;
        } catch (FileNotFoundRuntimeException e) {
            e.printStackTrace();
            return false;
        } catch (IORuntimeException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fis != null) try {
                fis.close();
            } catch (IORuntimeException e) {
                e.printStackTrace();
            }
            if (fos != null) try {
                fos.close();
            } catch (IORuntimeException e) {
                e.printStackTrace();
            }
        }
    }

}
