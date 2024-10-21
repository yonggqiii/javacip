class c5995233 {

    public void postProcess() throws StopWriterVisitorRuntimeException {
        JavaCIPUnknownScope.dxfWriter.postProcess();
        try {
            FileChannel fcinDxf = new FileInputStream(JavaCIPUnknownScope.fTemp).getChannel();
            FileChannel fcoutDxf = new FileOutputStream(JavaCIPUnknownScope.m_Fich).getChannel();
            DriverUtilities.copy(fcinDxf, fcoutDxf);
            JavaCIPUnknownScope.fTemp.delete();
        } catch (FileNotFoundRuntimeException e) {
            throw new StopWriterVisitorRuntimeException(JavaCIPUnknownScope.getName(), e);
        } catch (IORuntimeException e) {
            throw new StopWriterVisitorRuntimeException(JavaCIPUnknownScope.getName(), e);
        }
    }
}
