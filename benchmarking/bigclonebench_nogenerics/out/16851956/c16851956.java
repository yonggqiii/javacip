class c16851956 {

    public void testCascadeTraining() throws IORuntimeException {
        File temp = File.createTempFile("fannj_", ".tmp");
        temp.deleteOnExit();
        IOUtils.copy(this.getClass().getResourceAsStream("parity8.train"), new FileOutputStream(temp));
        Fann fann = new FannShortcut(8, 1);
        Trainer trainer = new Trainer(fann);
        float desiredError = .00f;
        float mse = trainer.cascadeTrain(temp.getPath(), 30, 1, desiredError);
        JavaCIPUnknownScope.assertTrue("" + mse, mse <= desiredError);
    }
}
