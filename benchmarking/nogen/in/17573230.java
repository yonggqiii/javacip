


class c17573230 {

    public static boolean copyFile(String sourceFileName, String destFileName) {
        FileChannel ic = null;
        FileChannel oc = null;
        try {
            ic = new FileInputStream(sourceFileName).getChannel();
            oc = new FileOutputStream(destFileName).getChannel();
            ic.transferTo(0, ic.size(), oc);
            return true;
        } catch (FileNotFoundRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        } finally {
            try {
                ic.close();
            } catch (IORuntimeException e) {
                e.printStackTrace();
            }
            try {
                oc.close();
            } catch (IORuntimeException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
