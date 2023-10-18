


class c16199085 {

    @Override
    public byte[] read(String path) throws PersistenceRuntimeException {
        path = fmtPath(path);
        try {
            S3Object fileObj = s3Service.getObject(bucketObj, path);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            IOUtils.copy(fileObj.getDataInputStream(), out);
            return out.toByteArray();
        } catch (RuntimeException e) {
            throw new PersistenceRuntimeException("fail to read s3 file - " + path, e);
        }
    }

}
