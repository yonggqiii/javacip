class c2059352 {

    public static void main(String[] args) {
        String inFile = "test_data/blobs.png";
        String outFile = "ReadWriteTest.png";
        itkImageFileReaderUC2_Pointer reader = JavaCIPUnknownScope.itkImageFileReaderUC2.itkImageFileReaderUC2_New();
        itkImageFileWriterUC2_Pointer writer = JavaCIPUnknownScope.itkImageFileWriterUC2.itkImageFileWriterUC2_New();
        reader.SetFileName(inFile);
        writer.SetFileName(outFile);
        writer.SetInput(reader.GetOutput());
        writer.Update();
    }
}
