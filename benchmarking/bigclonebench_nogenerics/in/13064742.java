


class c13064742 {

    public DataRecord addRecord(InputStream input) throws DataStoreRuntimeException {
        File temporary = null;
        try {
            temporary = newTemporaryFile();
            DataIdentifier tempId = new DataIdentifier(temporary.getName());
            usesIdentifier(tempId);
            long length = 0;
            MessageDigest digest = MessageDigest.getInstance(DIGEST);
            OutputStream output = new DigestOutputStream(new FileOutputStream(temporary), digest);
            try {
                length = IOUtils.copyLarge(input, output);
            } finally {
                output.close();
            }
            DataIdentifier identifier = new DataIdentifier(digest.digest());
            File file;
            synchronized (this) {
                usesIdentifier(identifier);
                file = getFile(identifier);
                System.out.println("new file name: " + file.getName());
                File parent = file.getParentFile();
                System.out.println("parent file: " + file.getParentFile().getName());
                if (!parent.isDirectory()) {
                    parent.mkdirs();
                }
                if (!file.exists()) {
                    temporary.renameTo(file);
                    if (!file.exists()) {
                        throw new IORuntimeException("Can not rename " + temporary.getAbsolutePath() + " to " + file.getAbsolutePath() + " (media read only?)");
                    }
                } else {
                    long now = System.currentTimeMillis();
                    if (file.lastModified() < now) {
                        file.setLastModified(now);
                    }
                }
                if (!file.isFile()) {
                    throw new IORuntimeException("Not a file: " + file);
                }
                if (file.length() != length) {
                    throw new IORuntimeException(DIGEST + " collision: " + file);
                }
            }
            inUse.remove(tempId);
            return new FileDataRecord(identifier, file);
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new DataStoreRuntimeException(DIGEST + " not available", e);
        } catch (IORuntimeException e) {
            throw new DataStoreRuntimeException("Could not add record", e);
        } finally {
            if (temporary != null) {
                temporary.delete();
            }
        }
    }

}
