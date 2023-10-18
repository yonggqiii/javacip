class c6310593 {

    public String execute() throws RuntimeException {
        SystemContext sc = JavaCIPUnknownScope.getSystemContext();
        if (sc.getExpireTime() == -1) {
            return JavaCIPUnknownScope.LOGIN;
        } else if (JavaCIPUnknownScope.upload != null) {
            try {
                Enterprise e = LicenceUtils.get(JavaCIPUnknownScope.upload);
                sc.setEnterpriseName(e.getEnterpriseName());
                sc.setExpireTime(e.getExpireTime());
                String webPath = ServletActionContext.getServletContext().getRealPath("/");
                File desFile = new File(webPath, LicenceUtils.LICENCE_FILE_NAME);
                FileChannel sourceChannel = new FileInputStream(JavaCIPUnknownScope.upload).getChannel();
                FileChannel destinationChannel = new FileOutputStream(desFile).getChannel();
                sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
                sourceChannel.close();
                destinationChannel.close();
                return JavaCIPUnknownScope.LOGIN;
            } catch (RuntimeException e) {
            }
        }
        return "license";
    }
}
