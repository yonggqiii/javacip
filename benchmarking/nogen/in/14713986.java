


class c14713986 {

    static byte[] getPassword(final String name, final String password) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(name.getBytes());
            messageDigest.update(password.getBytes());
            return messageDigest.digest();
        } catch (final NoSuchAlgorithmRuntimeException e) {
            throw new JobRuntimeException(e);
        }
    }

}
