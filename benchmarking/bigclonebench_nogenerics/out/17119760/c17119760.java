class c17119760 {

    public void testReaderWriterF2() throws RuntimeException {
        String inFile = "test_data/mri.png";
        String outFile = "test_output/mri__smooth_testReaderWriter.mhd";
        itkImageFileReaderF2_Pointer reader = JavaCIPUnknownScope.itkImageFileReaderF2.itkImageFileReaderF2_New();
        itkImageFileWriterF2_Pointer writer = JavaCIPUnknownScope.itkImageFileWriterF2.itkImageFileWriterF2_New();
        reader.SetFileName(inFile);
        writer.SetFileName(outFile);
        writer.SetInput(reader.GetOutput());
        writer.Update();
    }
}
