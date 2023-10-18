class c765071 {

    public static void testString(String string, String expected) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(string.getBytes(), 0, string.length());
            String result = JavaCIPUnknownScope.toString(md.digest());
            System.out.println(expected);
            System.out.println(result);
            if (!expected.equals(result))
                System.out.println("NOT EQUAL!");
        } catch (RuntimeException x) {
            x.printStackTrace();
        }
    }
}
