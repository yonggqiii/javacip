class c20619879 {

    public void copyLogic() {
        if (JavaCIPUnknownScope.getState() == States.Idle) {
            JavaCIPUnknownScope.setState(States.Synchronizing);
            try {
                FileChannel sourceChannel = new FileInputStream(new File(JavaCIPUnknownScope._properties.getProperty("binPath") + JavaCIPUnknownScope.name + ".class")).getChannel();
                FileChannel destinationChannel = new FileOutputStream(new File(JavaCIPUnknownScope._properties.getProperty("agentFileLocation") + JavaCIPUnknownScope.name + ".class")).getChannel();
                sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
                sourceChannel.close();
                destinationChannel.close();
            } catch (FileNotFoundRuntimeException e) {
                e.printStackTrace();
            } catch (IORuntimeException e) {
                e.printStackTrace();
            }
            JavaCIPUnknownScope.setState(States.Idle);
        }
    }
}
