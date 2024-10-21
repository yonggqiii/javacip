class Class_4 {

    private static Pattern twopart = Pattern.compile("(\\d+)-(\\d+)");

    public static void checkString(String s) {
        Matcher m = twopart.matcher(s);
        if (m.matches()) {
            System.out.println(s + " matches; first part is " + m.group(0) + ", second part is " + m.group(1) + ".");
        } else {
            System.out.println(s + " does not match.");
        }
    }

    public static void main(String[] args) {
        checkString("123-4567");
        checkString("foo-bar");
        checkString("123-");
        checkString("-4567");
        checkString("123-4567-890");
    }

    public static String[] mySplit(String text, String delimiter) {
        List<String> parts = new ArrayList<String>();
        text += delimiter;
        for (int i = text.indexOf(delimiter), j = 0; i != -1; ) {
            String temp = text.substring(j, i);
            if (temp.trim().length() != 0) {
                parts.add(temp);
            }
            j = i + delimiter.length();
            i = text.indexOf(delimiter, j);
        }
        return parts.toArray(new String[0]);
    }

    public static void main_2() {
        String str = "004-034556";
        String delimiter = "-";
        String[] result = mySplit(str, delimiter);
        for (String s : result) System.out.println(s);
    }
}
