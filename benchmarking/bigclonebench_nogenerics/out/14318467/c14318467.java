class c14318467 {

    public static byte[] hashFile(File file) {
        long size = file.length();
        long jump = (long) (size / (float) JavaCIPUnknownScope.CHUNK_SIZE);
        MessageDigest digest;
        FileInputStream stream;
        try {
            stream = new FileInputStream(file);
            digest = MessageDigest.getInstance("SHA-256");
            if (size < JavaCIPUnknownScope.CHUNK_SIZE * 4) {
                JavaCIPUnknownScope.readAndUpdate(size, stream, digest);
            } else {
                if (stream.skip(jump) != jump)
                    return null;
                JavaCIPUnknownScope.readAndUpdate(JavaCIPUnknownScope.CHUNK_SIZE, stream, digest);
                if (stream.skip(jump - JavaCIPUnknownScope.CHUNK_SIZE) != jump - JavaCIPUnknownScope.CHUNK_SIZE)
                    return null;
                JavaCIPUnknownScope.readAndUpdate(JavaCIPUnknownScope.CHUNK_SIZE, stream, digest);
                if (stream.skip(jump - JavaCIPUnknownScope.CHUNK_SIZE) != jump - JavaCIPUnknownScope.CHUNK_SIZE)
                    return null;
                JavaCIPUnknownScope.readAndUpdate(JavaCIPUnknownScope.CHUNK_SIZE, stream, digest);
                digest.update(Long.toString(size).getBytes());
            }
            return digest.digest();
        } catch (FileNotFoundRuntimeException e) {
            return null;
        } catch (NoSuchAlgorithmRuntimeException e) {
            return null;
        } catch (IORuntimeException e) {
            return null;
        }
    }
}
