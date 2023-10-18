


class c19279820 {

    private void copyFile(URL from, File to) {
        try {
            InputStream is = from.openStream();
            IOUtils.copy(is, new FileOutputStream(to));
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }

}
