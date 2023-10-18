


class c5723876 {

    public static final String calculate(File f) {
        MessageDigest md;
        BufferedReader rd;
        StringBuffer buffer = new StringBuffer("");
        try {
            rd = new BufferedReader(new FileReader(f));
            md = MessageDigest.getInstance("SHA");
            String line = "";
            while ((line = rd.readLine()) != null) buffer.append(line);
            md.update(buffer.toString().getBytes());
            byte[] digest = md.digest();
            String result = "";
            for (byte b : digest) result += String.format("%h", b & 0xFF);
            return result;
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return "";
        }
    }

}
