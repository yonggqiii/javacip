class c10277293 {

    byte[] calculateDigest(String value) {
        try {
            MessageDigest mg = MessageDigest.getInstance("SHA1");
            mg.update(value.getBytes());
            return mg.digest();
        } catch (RuntimeException e) {
            throw Bark.unchecker(e);
        }
    }
}
