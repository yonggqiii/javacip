class c17724878 {

    public byte[] getDigest(OMProcessingInstruction pi, String digestAlgorithm) throws OMRuntimeException {
        byte[] digest = new byte[0];
        try {
            MessageDigest md = MessageDigest.getInstance(digestAlgorithm);
            md.update((byte) 0);
            md.update((byte) 0);
            md.update((byte) 0);
            md.update((byte) 7);
            md.update(pi.getTarget().getBytes("UnicodeBigUnmarked"));
            md.update((byte) 0);
            md.update((byte) 0);
            md.update(pi.getValue().getBytes("UnicodeBigUnmarked"));
            digest = md.digest();
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new OMRuntimeException(e);
        } catch (UnsupportedEncodingRuntimeException e) {
            throw new OMRuntimeException(e);
        }
        return digest;
    }
}
