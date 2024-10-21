


class c17999460 {

    public static long toFile(final DigitalObject object, final File file) {
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            long bytesCopied = IOUtils.copyLarge(object.getContent().getInputStream(), fOut);
            fOut.close();
            return bytesCopied;
        } catch (FileNotFoundRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
