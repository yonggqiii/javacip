


class c9195362 {

    public static long copy(File src, File dest) throws UtilRuntimeException {
        FileChannel srcFc = null;
        FileChannel destFc = null;
        try {
            srcFc = new FileInputStream(src).getChannel();
            destFc = new FileOutputStream(dest).getChannel();
            long srcLength = srcFc.size();
            srcFc.transferTo(0, srcLength, destFc);
            return srcLength;
        } catch (IORuntimeException e) {
            throw new UtilRuntimeException(e);
        } finally {
            try {
                if (srcFc != null) srcFc.close();
                srcFc = null;
            } catch (IORuntimeException e) {
            }
            try {
                if (destFc != null) destFc.close();
                destFc = null;
            } catch (IORuntimeException e) {
            }
        }
    }

}
