class c4557988 {

    public Attributes getAttributes() throws SchemaViolationRuntimeException, NoSuchAlgorithmRuntimeException, UnsupportedEncodingRuntimeException {
        BasicAttributes outAttrs = new BasicAttributes(true);
        BasicAttribute oc = new BasicAttribute("objectclass", "inetOrgPerson");
        oc.add("organizationalPerson");
        oc.add("person");
        outAttrs.put(oc);
        if (JavaCIPUnknownScope.lastName != null && JavaCIPUnknownScope.firstName != null) {
            outAttrs.put("sn", JavaCIPUnknownScope.lastName);
            outAttrs.put("givenName", JavaCIPUnknownScope.firstName);
            outAttrs.put("cn", JavaCIPUnknownScope.firstName + " " + JavaCIPUnknownScope.lastName);
        } else {
            throw new SchemaViolationRuntimeException("user must have surname");
        }
        if (JavaCIPUnknownScope.password != null) {
            MessageDigest sha = MessageDigest.getInstance("md5");
            sha.reset();
            sha.update(JavaCIPUnknownScope.password.getBytes("utf-8"));
            byte[] digest = sha.digest();
            String hash = Base64.encodeBase64String(digest);
            outAttrs.put("userPassword", "{MD5}" + hash);
        }
        if (JavaCIPUnknownScope.email != null) {
            outAttrs.put("mail", JavaCIPUnknownScope.email);
        }
        return (Attributes) outAttrs;
    }
}
