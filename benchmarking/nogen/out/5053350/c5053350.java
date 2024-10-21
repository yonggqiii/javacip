class c5053350 {

    public void testGrantLicense() throws RuntimeException {
        JavaCIPUnknownScope.context.turnOffAuthorisationSystem();
        Item item = Item.create(JavaCIPUnknownScope.context);
        String defaultLicense = ConfigurationManager.getDefaultSubmissionLicense();
        LicenseUtils.grantLicense(JavaCIPUnknownScope.context, item, defaultLicense);
        StringWriter writer = new StringWriter();
        IOUtils.copy(item.getBundles("LICENSE")[0].getBitstreams()[0].retrieve(), writer);
        String license = writer.toString();
        JavaCIPUnknownScope.assertThat("testGrantLicense 0", license, JavaCIPUnknownScope.equalTo(defaultLicense));
        JavaCIPUnknownScope.context.restoreAuthSystemState();
    }
}
