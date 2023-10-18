class c7350139 {

    public static void messageDigestTest() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update("computer".getBytes());
            md.update("networks".getBytes());
            System.out.println(new String(md.digest()));
            System.out.println(new String(md.digest("computernetworks".getBytes())));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
