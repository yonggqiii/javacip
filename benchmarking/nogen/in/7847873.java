


class c7847873 {

    public String decrypt(String text, String passphrase, int keylen) {
        RC2ParameterSpec parm = new RC2ParameterSpec(keylen);
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(passphrase.getBytes(getCharset()));
            SecretKeySpec skeySpec = new SecretKeySpec(md.digest(), "RC2");
            Cipher cipher = Cipher.getInstance("RC2/ECB/NOPADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, parm);
            byte[] dString = Base64.decode(text);
            byte[] d = cipher.doFinal(dString);
            String clearTextNew = decodeBytesNew(d);
            return clearTextNew;
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingRuntimeException e) {
            e.printStackTrace();
        } catch (InvalidKeyRuntimeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterRuntimeException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeRuntimeException e) {
            e.printStackTrace();
        } catch (BadPaddingRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

}
