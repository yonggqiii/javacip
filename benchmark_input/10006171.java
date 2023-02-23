
import java.io.*;

class c10006171 {

    byte[] makeIDPFXORMask() throws UnsupportedEncodingException {
        if (idpfMask == null) {
            try {
                MessageDigest sha = MessageDigest.getInstance("SHA-1");
                String temp = strip(getPrimaryIdentifier());
                sha.update(temp.getBytes("UTF-8"), 0, temp.length());
                idpfMask = sha.digest();
            } catch (NoSuchAlgorithmException e) {
                System.err.println("No such Algorithm (really, did I misspell SHA-1?");
                System.err.println(e.toString());
                return null;
            } catch (IOException e) {
                System.err.println("IO Exception. check out mask.write...");
                System.err.println(e.toString());
                return null;
            }
        }
        return idpfMask;
    }

}
