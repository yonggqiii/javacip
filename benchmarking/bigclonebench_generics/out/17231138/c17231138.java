class c17231138 {

    protected AuthenticationHandlerResponse authenticateInternal(final Connection c, final AuthenticationCriteria criteria) throws LdapException {
        byte[] hash;
        try {
            final MessageDigest md = MessageDigest.getInstance(JavaCIPUnknownScope.passwordScheme);
            md.update(criteria.getCredential().getBytes());
            hash = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new LdapException(e);
        }
        final LdapAttribute la = new LdapAttribute("userPassword", String.format("{%s}%s", JavaCIPUnknownScope.passwordScheme, LdapUtils.base64Encode(hash)).getBytes());
        final CompareOperation compare = new CompareOperation(c);
        final CompareRequest request = new CompareRequest(criteria.getDn(), la);
        request.setControls(JavaCIPUnknownScope.getAuthenticationControls());
        final Response<Boolean> compareResponse = compare.execute(request);
        return new AuthenticationHandlerResponse(compareResponse.getResult(), compareResponse.getResultCode(), c, compareResponse.getMessage(), compareResponse.getControls());
    }
}
