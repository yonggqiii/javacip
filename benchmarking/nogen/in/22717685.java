


class c22717685 {

    public static final void copy(File src, File dest) throws IORuntimeException {
        FileInputStream source = null;
        FileOutputStream destination = null;
        byte[] buffer;
        int bytes_read;
        if (!src.exists()) {
            throw new IORuntimeException("Source not found: " + src);
        }
        if (!src.canRead()) {
            throw new IORuntimeException("Source is unreadable: " + src);
        }
        if (src.isFile()) {
            if (!dest.exists()) {
                File parentdir = parent(dest);
                if (!parentdir.exists()) {
                    parentdir.mkdir();
                }
            } else if (dest.isDirectory()) {
                dest = new File(dest + File.separator + src);
            }
        } else if (src.isDirectory()) {
            if (dest.isFile()) {
                throw new IORuntimeException("Cannot copy directory " + src + " to file " + dest);
            }
            if (!dest.exists()) {
                dest.mkdir();
            }
        }
        if (src.isFile()) {
            try {
                source = new FileInputStream(src);
                destination = new FileOutputStream(dest);
                buffer = new byte[1024];
                while (true) {
                    bytes_read = source.read(buffer);
                    if (bytes_read == -1) {
                        break;
                    }
                    destination.write(buffer, 0, bytes_read);
                }
            } finally {
                if (source != null) {
                    try {
                        source.close();
                    } catch (IORuntimeException e) {
                    }
                }
                if (destination != null) {
                    try {
                        destination.close();
                    } catch (IORuntimeException e) {
                    }
                }
            }
        } else if (src.isDirectory()) {
            String targetfile, target, targetdest;
            String[] files = src.list();
            for (int i = 0; i < files.length; i++) {
                targetfile = files[i];
                target = src + File.separator + targetfile;
                targetdest = dest + File.separator + targetfile;
                if ((new File(target)).isDirectory()) {
                    copy(new File(target), new File(targetdest));
                } else {
                    try {
                        source = new FileInputStream(target);
                        destination = new FileOutputStream(targetdest);
                        buffer = new byte[1024];
                        while (true) {
                            bytes_read = source.read(buffer);
                            if (bytes_read == -1) {
                                break;
                            }
                            destination.write(buffer, 0, bytes_read);
                        }
                    } finally {
                        if (source != null) {
                            try {
                                source.close();
                            } catch (IORuntimeException e) {
                            }
                        }
                        if (destination != null) {
                            try {
                                destination.close();
                            } catch (IORuntimeException e) {
                            }
                        }
                    }
                }
            }
        }
    }

}
