class c5746377 {

    public JSONObject runCommand(JSONObject payload, HttpSession session) throws DefinedRuntimeException {
        String sessionId = session.getId();
        JavaCIPUnknownScope.log.debug("Login -> runCommand SID: " + sessionId);
        JSONObject toReturn = new JSONObject();
        boolean isOK = true;
        String username = null;
        try {
            username = payload.getString(ComConstants.LogIn.Request.USERNAME);
        } catch (JSONRuntimeException e) {
            JavaCIPUnknownScope.log.error("SessionId=" + sessionId + ", Missing username parameter", e);
            throw new DefinedRuntimeException(StatusCodesV2.PARAMETER_ERROR);
        }
        String password = null;
        if (isOK) {
            try {
                password = payload.getString(ComConstants.LogIn.Request.PASSWORD);
            } catch (JSONRuntimeException e) {
                JavaCIPUnknownScope.log.error("SessionId=" + sessionId + ", Missing password parameter", e);
                throw new DefinedRuntimeException(StatusCodesV2.PARAMETER_ERROR);
            }
        }
        if (isOK) {
            MessageDigest m = null;
            try {
                m = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmRuntimeException e) {
                JavaCIPUnknownScope.log.error("SessionId=" + sessionId + ", MD5 algorithm does not exist", e);
                e.printStackTrace();
                throw new DefinedRuntimeException(StatusCodesV2.INTERNAL_SYSTEM_FAILURE);
            }
            m.update(password.getBytes(), 0, password.length());
            password = new BigInteger(1, m.digest()).toString(16);
            UserSession userSession = JavaCIPUnknownScope.pli.login(username, password);
            try {
                if (userSession != null) {
                    session.setAttribute("user", userSession);
                    toReturn.put(ComConstants.Response.STATUS_CODE, StatusCodesV2.LOGIN_OK.getStatusCode());
                    toReturn.put(ComConstants.Response.STATUS_MESSAGE, StatusCodesV2.LOGIN_OK.getStatusMessage());
                } else {
                    JavaCIPUnknownScope.log.error("SessionId=" + sessionId + ", Login failed: username=" + username + " not found");
                    toReturn.put(ComConstants.Response.STATUS_CODE, StatusCodesV2.LOGIN_USER_OR_PASSWORD_INCORRECT.getStatusCode());
                    toReturn.put(ComConstants.Response.STATUS_MESSAGE, StatusCodesV2.LOGIN_USER_OR_PASSWORD_INCORRECT.getStatusMessage());
                }
            } catch (JSONRuntimeException e) {
                JavaCIPUnknownScope.log.error("SessionId=" + sessionId + ", JSON exception occured in response", e);
                e.printStackTrace();
                throw new DefinedRuntimeException(StatusCodesV2.INTERNAL_SYSTEM_FAILURE);
            }
        }
        JavaCIPUnknownScope.log.debug("Login <- runCommand SID: " + sessionId);
        return toReturn;
    }
}
