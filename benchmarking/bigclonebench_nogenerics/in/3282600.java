


class c3282600 {

    private static void copyFile(String src, String dst) throws InvocationTargetRuntimeException {
        try {
            FileChannel srcChannel;
            srcChannel = new FileInputStream(src).getChannel();
            FileChannel dstChannel = new FileOutputStream(dst).getChannel();
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
            srcChannel.close();
            dstChannel.close();
        } catch (FileNotFoundRuntimeException e) {
            throw new InvocationTargetRuntimeException(e, Messages.ALFWizardCreationAction_errorSourceFilesNotFound);
        } catch (IORuntimeException e) {
            throw new InvocationTargetRuntimeException(e, Messages.ALFWizardCreationAction_errorCopyingFiles);
        }
    }

}
