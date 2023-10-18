class c12879117 {

    protected String insertCommand(String command) throws ServletRuntimeException {
        String digest;
        try {
            MessageDigest md = MessageDigest.getInstance(JavaCIPUnknownScope.m_messagedigest_algorithm);
            md.update(command.getBytes());
            byte[] bytes = new byte[20];
            JavaCIPUnknownScope.m_random.nextBytes(bytes);
            md.update(bytes);
            digest = JavaCIPUnknownScope.bytesToHex(md.digest());
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new ServletRuntimeException("NoSuchAlgorithmRuntimeException while " + "attempting to generate graph ID: " + e);
        }
        String id = System.currentTimeMillis() + "-" + digest;
        JavaCIPUnknownScope.m_map.put(id, command);
        return id;
    }
}
