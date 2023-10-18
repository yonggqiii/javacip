class c4612220 {

    private void copy(File source, File destination) throws PackageRuntimeException {
        try {
            FileInputStream in = new FileInputStream(source);
            FileOutputStream out = new FileOutputStream(destination);
            byte[] buff = new byte[1024];
            int len;
            while ((len = in.read(buff)) > 0) out.write(buff, 0, len);
            in.close();
            out.close();
        } catch (IORuntimeException e) {
            throw new PackageRuntimeException("Unable to copy " + source.getPath() + " to " + destination.getPath() + " :: " + e.toString());
        }
    }
}
