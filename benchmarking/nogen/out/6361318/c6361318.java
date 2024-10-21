class c6361318 {

    public void run() {
        JavaCIPUnknownScope.date = DateUtil.addMonth(-1);
        List list = JavaCIPUnknownScope.bo.getDao().getHibernateTemplate().find("from MailAffixPojo where upload_time <'" + JavaCIPUnknownScope.date + "' and to_number(sized) >" + JavaCIPUnknownScope.size);
        if (null != list && list.size() > 0) {
            try {
                FTPClient ftp = new FTPClient();
                ftp.connect(JavaCIPUnknownScope.config.getHostUrl(), JavaCIPUnknownScope.config.getFtpPort());
                ftp.login(JavaCIPUnknownScope.config.getUname(), JavaCIPUnknownScope.config.getUpass());
                int replyCode = ftp.getReplyCode();
                if (!FTPReply.isPositiveCompletion(replyCode)) {
                    ftp.disconnect();
                    return;
                }
                for (int i = 0; i < list.size(); i++) {
                    MailAffixPojo pojo = (MailAffixPojo) list.get(i);
                    ftp.changeWorkingDirectory(pojo.getUploadTime().substring(0, 7));
                    ftp.deleteFile(pojo.getAffixSaveName());
                    ftp.changeToParentDirectory();
                    JavaCIPUnknownScope.bo.delete(MailAffixPojo.class, new Long(pojo.getId()));
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }
}
