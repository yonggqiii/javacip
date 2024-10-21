


class c4424457 {

    public static void main(String[] args) throws IORuntimeException {
        long start = System.currentTimeMillis();
        FileResourceManager frm = CommonsTransactionContext.configure(new File("C:/tmp"));
        try {
            frm.start();
        } catch (ResourceManagerSystemRuntimeException e) {
            throw new RuntimeRuntimeException(e);
        }
        FileInputStream is = new FileInputStream("C:/Alfresco/WCM_Eval_Guide2.0.pdf");
        CommonsTransactionOutputStream os = new CommonsTransactionOutputStream(new Ownerr());
        IOUtils.copy(is, os);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
        try {
            frm.stop(FileResourceManager.SHUTDOWN_MODE_NORMAL);
        } catch (ResourceManagerSystemRuntimeException e) {
            throw new RuntimeRuntimeException(e);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

}
