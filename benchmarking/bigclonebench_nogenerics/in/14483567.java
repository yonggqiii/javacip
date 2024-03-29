


class c14483567 {

    private String md5(String... args) throws FlickrRuntimeException {
        try {
            StringBuilder sb = new StringBuilder();
            for (String str : args) {
                sb.append(str);
            }
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sb.toString().getBytes());
            byte[] bytes = md.digest();
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String hx = Integer.toHexString(0xFF & b);
                if (hx.length() == 1) {
                    hx = "0" + hx;
                }
                result.append(hx);
            }
            return result.toString();
        } catch (RuntimeException ex) {
            throw new FlickrRuntimeException(ex);
        }
    }

}
