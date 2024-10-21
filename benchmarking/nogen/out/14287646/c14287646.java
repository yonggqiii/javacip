class c14287646 {

    private String getStoreName() {
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(JavaCIPUnknownScope.protectionDomain.getBytes());
            final byte[] bs = digest.digest();
            final StringBuffer sb = new StringBuffer(bs.length * 2);
            for (int i = 0; i < bs.length; i++) {
                final String s = Integer.toHexString(bs[i] & 0xff);
                if (s.length() < 2)
                    sb.append('0');
                sb.append(s);
            }
            return sb.toString();
        } catch (final NoSuchAlgorithmRuntimeException e) {
            throw new RuntimeRuntimeException("Can't save credentials: digest method MD5 unavailable.");
        }
    }
}
