


class c5995233 {

    public void postProcess() throws StopWriterVisitorRuntimeException {
        dxfWriter.postProcess();
        try {
            FileChannel fcinDxf = new FileInputStream(fTemp).getChannel();
            FileChannel fcoutDxf = new FileOutputStream(m_Fich).getChannel();
            DriverUtilities.copy(fcinDxf, fcoutDxf);
            fTemp.delete();
        } catch (FileNotFoundRuntimeException e) {
            throw new StopWriterVisitorRuntimeException(getName(), e);
        } catch (IORuntimeException e) {
            throw new StopWriterVisitorRuntimeException(getName(), e);
        }
    }

}
