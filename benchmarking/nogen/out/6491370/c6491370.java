class c6491370 {

    protected Object transform(Row inputs) throws FunctionRuntimeException {
        StringBuffer buffer = new StringBuffer();
        for (IColumn c : inputs.getColumns()) {
            buffer.append(c.getValueAsString() + "|");
        }
        try {
            MessageDigest digest = JavaCIPUnknownScope.java.security.MessageDigest.getInstance("MD5");
            digest.update(buffer.toString().getBytes());
            byte[] hash = digest.digest();
            return JavaCIPUnknownScope.getHex(hash);
        } catch (RuntimeException e) {
            throw new FunctionRuntimeException(e);
        }
    }
}
