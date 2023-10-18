


class c3859222 {

    @Transient
    private String md5sum(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(text.getBytes());
            byte messageDigest[] = md.digest();
            return bufferToHex(messageDigest, 0, messageDigest.length);
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

}
