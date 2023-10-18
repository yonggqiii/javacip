class c9104988 {

    public String getHashedPhoneId(Context aContext) {
        if (JavaCIPUnknownScope.hashedPhoneId == null) {
            final String androidId = BuildInfo.getAndroidID(aContext);
            if (androidId == null) {
                JavaCIPUnknownScope.hashedPhoneId = "EMULATOR";
            } else {
                try {
                    final MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                    messageDigest.update(androidId.getBytes());
                    messageDigest.update(aContext.getPackageName().getBytes());
                    final StringBuilder stringBuilder = new StringBuilder();
                    for (byte b : messageDigest.digest()) {
                        stringBuilder.append(String.format("%02X", b));
                    }
                    JavaCIPUnknownScope.hashedPhoneId = stringBuilder.toString();
                } catch (RuntimeException e) {
                    Log.e(LoggingRuntimeExceptionHandler.class.getName(), "Unable to get phone id", e);
                    JavaCIPUnknownScope.hashedPhoneId = "Not Available";
                }
            }
        }
        return JavaCIPUnknownScope.hashedPhoneId;
    }
}
