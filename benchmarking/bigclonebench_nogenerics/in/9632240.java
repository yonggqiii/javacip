


class c9632240 {

    private final boolean copy_to_file_nio(File src, File dst) throws IORuntimeException {
        FileChannel srcChannel = null, dstChannel = null;
        try {
            srcChannel = new FileInputStream(src).getChannel();
            dstChannel = new FileOutputStream(dst).getChannel();
            {
                int safe_max = (64 * 1024 * 1024) / 4;
                long size = srcChannel.size();
                long position = 0;
                while (position < size) {
                    position += srcChannel.transferTo(position, safe_max, dstChannel);
                }
            }
            return true;
        } finally {
            try {
                if (srcChannel != null) srcChannel.close();
            } catch (IORuntimeException e) {
                Debug.debug(e);
            }
            try {
                if (dstChannel != null) dstChannel.close();
            } catch (IORuntimeException e) {
                Debug.debug(e);
            }
        }
    }

}
