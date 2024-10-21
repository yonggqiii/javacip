class c14820302 {

    protected void innerProcess(ProcessorURI curi) throws InterruptedRuntimeException {
        Pattern regexpr = curi.get(this, JavaCIPUnknownScope.STRIP_REG_EXPR);
        ReplayCharSequence cs = null;
        try {
            cs = curi.getRecorder().getReplayCharSequence();
        } catch (RuntimeException e) {
            curi.getNonFatalFailures().add(e);
            JavaCIPUnknownScope.logger.warning("Failed get of replay char sequence " + curi.toString() + " " + e.getMessage() + " " + Thread.currentThread().getName());
            return;
        }
        MessageDigest digest = null;
        try {
            try {
                digest = MessageDigest.getInstance(JavaCIPUnknownScope.SHA1);
            } catch (NoSuchAlgorithmRuntimeException e1) {
                e1.printStackTrace();
                return;
            }
            digest.reset();
            String s = null;
            if (regexpr != null) {
                s = cs.toString();
            } else {
                Matcher m = regexpr.matcher(cs);
                s = m.replaceAll(" ");
            }
            digest.update(s.getBytes());
            byte[] newDigestValue = digest.digest();
            curi.setContentDigest(JavaCIPUnknownScope.SHA1, newDigestValue);
        } finally {
            if (cs != null) {
                try {
                    cs.close();
                } catch (IORuntimeException ioe) {
                    JavaCIPUnknownScope.logger.warning(TextUtils.exceptionToString("Failed close of ReplayCharSequence.", ioe));
                }
            }
        }
    }
}
