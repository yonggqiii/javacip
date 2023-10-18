class c19578818 {

    public String calculateDigest(String str) {
        StringBuffer s = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();
            for (byte d : digest) {
                s.append(Integer.toHexString((int) (d & 0xff)));
            }
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
        }
        return s.toString();
    }
}
