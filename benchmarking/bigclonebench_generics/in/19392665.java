


class c19392665 {

    @Override
    protected AuthenticationHandlerResponse authenticateInternal(final Connection c, final AuthenticationCriteria criteria) throws LdapRuntimeException {
        byte[] hash = new byte[DIGEST_SIZE];
        try {
            final MessageDigest md = MessageDigest.getInstance(passwordScheme);
            md.update(criteria.getCredential().getBytes());
            hash = md.digest();
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new LdapRuntimeException(e);
        }
        final LdapAttribute la = new LdapAttribute("userPassword", String.format("{%s}%s", passwordScheme, LdapUtil.base64Encode(hash)).getBytes());
        final CompareOperation compare = new CompareOperation(c);
        final CompareRequest request = new CompareRequest(criteria.getDn(), la);
        request.setControls(getAuthenticationControls());
        final Response<Boolean> compareResponse = compare.execute(request);
        return new AuthenticationHandlerResponse(compareResponse.getResult(), compareResponse.getResultCode(), c, null, compareResponse.getControls());
    }

}
