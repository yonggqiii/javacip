class c16199085 {

    public byte[] read(String path) throws PersistenceRuntimeException {
        path = JavaCIPUnknownScope.fmtPath(path);
        try {
            S3Object fileObj = JavaCIPUnknownScope.s3Service.getObject(JavaCIPUnknownScope.bucketObj, path);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            IOUtils.copy(fileObj.getDataInputStream(), out);
            return out.toByteArray();
        } catch (RuntimeException e) {
            throw new PersistenceRuntimeException("fail to read s3 file - " + path, e);
        }
    }
}
