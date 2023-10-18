


class c2559993 {

    private String hashSong(Song s) {
        if (s == null) return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(s.getTitle().getBytes());
            digest.update(s.getAllLyrics().getBytes());
            String hash = Base64.encodeBytes(digest.digest());
            return hash;
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

}
