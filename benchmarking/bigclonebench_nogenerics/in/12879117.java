


class c12879117 {

    protected String insertCommand(String command) throws ServletRuntimeException {
        String digest;
        try {
            MessageDigest md = MessageDigest.getInstance(m_messagedigest_algorithm);
            md.update(command.getBytes());
            byte bytes[] = new byte[20];
            m_random.nextBytes(bytes);
            md.update(bytes);
            digest = bytesToHex(md.digest());
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new ServletRuntimeException("NoSuchAlgorithmRuntimeException while " + "attempting to generate graph ID: " + e);
        }
        String id = System.currentTimeMillis() + "-" + digest;
        m_map.put(id, command);
        return id;
    }

}
