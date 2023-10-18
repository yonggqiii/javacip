


class c6907994 {

        void copy(String source_name, String dest_name) throws IORuntimeException {
            File source_file = new File(source_name);
            File destination_file = new File(dest_name);
            FileInputStream source = null;
            FileOutputStream destination = null;
            byte[] buffer;
            int bytes_read;
            try {
                if (!source_file.exists() || !source_file.isFile()) {
                    throw new FileCopyRuntimeException("FileCopy: no such source file: " + source_name);
                }
                if (!source_file.canRead()) {
                    throw new FileCopyRuntimeException("FileCopy: source file " + "is unreadable: " + source_name);
                }
                if (!destination_file.exists()) {
                    File parentdir = parent(destination_file);
                    if (!parentdir.exists()) {
                        throw new FileCopyRuntimeException("FileCopy: destination " + "directory doesn't exist: " + dest_name);
                    }
                    if (!parentdir.canWrite()) {
                        throw new FileCopyRuntimeException("FileCopy: destination " + "directory is unwriteable: " + dest_name);
                    }
                }
                source = new FileInputStream(source_file);
                destination = new FileOutputStream(destination_file);
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
