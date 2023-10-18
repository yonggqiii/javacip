


class c1395368 {

    private File copyFile(File currFile) throws IORuntimeException {
        String relativePath = currFile.getPath().substring(_distDir.length() + 1);
        File targetFile = new File(_installDir, relativePath);
        if (targetFile.exists()) {
            log(targetFile.getPath() + " already exists, skipping libcopy", Project.MSG_INFO);
            return targetFile;
        } else {
            if (!targetFile.getParentFile().exists()) {
                if (!targetFile.getParentFile().mkdirs()) {
                    log("Unable to create target dir tree for " + targetFile.getPath(), Project.MSG_ERR);
                    throw new IORuntimeException();
                }
            }
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(currFile);
        } catch (FileNotFoundRuntimeException e) {
            log("Library from plugin manifest appears to have been deleted: " + currFile.getPath(), Project.MSG_ERR);
            throw new IORuntimeException();
        }
        try {
            fos = new FileOutputStream(targetFile);
        } catch (FileNotFoundRuntimeException e) {
            log("Unable to create target file to write to: " + targetFile.getPath(), Project.MSG_ERR);
            throw new IORuntimeException();
        }
        BufferedInputStream bis = new BufferedInputStream(fis);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        int read = 0;
        byte[] buff = new byte[65536];
        boolean success = true;
        while (read != -1 && success) {
            try {
                read = bis.read(buff, 0, 65536);
            } catch (IORuntimeException e) {
                log("Read error whilst reading from: " + currFile.getPath(), Project.MSG_ERR);
                success = false;
            }
            if (read != -1 && success) {
                try {
                    bos.write(buff, 0, read);
                } catch (IORuntimeException e) {
                    log("Write error whilst writing to: " + targetFile.getPath(), Project.MSG_ERR);
                    success = false;
                }
            }
        }
        try {
            bis.close();
        } catch (IORuntimeException e) {
        }
        try {
            bos.close();
        } catch (IORuntimeException e) {
        }
        try {
            fis.close();
        } catch (IORuntimeException e) {
        }
        try {
            fos.close();
        } catch (IORuntimeException e) {
        }
        if (!success) {
            throw new IORuntimeException();
        }
        return targetFile;
    }

}
