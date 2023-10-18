class c17119759 {

    public void testReaderWriterUC2() throws RuntimeException {
        String inFile = "test_data/mri.png";
        String outFile = "test_output/mri__smooth_testReaderWriter.png";
        itkImageFileReaderUC2_Pointer reader = JavaCIPUnknownScope.itkImageFileReaderUC2.itkImageFileReaderUC2_New();
        itkImageFileWriterUC2_Pointer writer = JavaCIPUnknownScope.itkImageFileWriterUC2.itkImageFileWriterUC2_New();
        reader.SetFileName(inFile);
        writer.SetFileName(outFile);
        writer.SetInput(reader.GetOutput());
        writer.Update();
    }
}
