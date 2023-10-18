


class c6491370 {

    @Override
    protected Object transform(Row inputs) throws FunctionRuntimeException {
        StringBuffer buffer = new StringBuffer();
        for (IColumn c : inputs.getColumns()) {
            buffer.append(c.getValueAsString() + "|");
        }
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(buffer.toString().getBytes());
            byte[] hash = digest.digest();
            return getHex(hash);
        } catch (RuntimeException e) {
            throw new FunctionRuntimeException(e);
        }
    }

}
