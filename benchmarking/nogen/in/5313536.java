


class c5313536 {

    public void merge(VMImage image, VMSnapShot another) throws VMRuntimeException {
        if (path == null || another.getPath() == null) throw new VMRuntimeException("EmuVMSnapShot is NULL!");
        logging.debug(LOG_NAME, "merge images  " + path + " and " + another.getPath());
        File target = new File(path);
        File src = new File(another.getPath());
        if (target.isDirectory() || src.isDirectory()) return;
        try {
            FileInputStream in = new FileInputStream(another.getPath());
            FileChannel inChannel = in.getChannel();
            FileOutputStream out = new FileOutputStream(path, true);
            FileChannel outChannel = out.getChannel();
            outChannel.transferFrom(inChannel, 0, inChannel.size());
            outChannel.close();
            inChannel.close();
        } catch (IORuntimeException e) {
            throw new VMRuntimeException(e);
        }
    }

}
