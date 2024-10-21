


class c17724880 {

    public byte[] getDigest(OMText text, String digestAlgorithm) throws OMRuntimeException {
        byte[] digest = new byte[0];
        try {
            MessageDigest md = MessageDigest.getInstance(digestAlgorithm);
            md.update((byte) 0);
            md.update((byte) 0);
            md.update((byte) 0);
            md.update((byte) 3);
            md.update(text.getText().getBytes("UnicodeBigUnmarked"));
            digest = md.digest();
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new OMRuntimeException(e);
        } catch (UnsupportedEncodingRuntimeException e) {
            throw new OMRuntimeException(e);
        }
        return digest;
    }

}
