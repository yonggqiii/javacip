class c5811449 {

    public void shouldProgateStagingRepoToYumGroupRepo() throws RuntimeException {
        JavaCIPUnknownScope.givenGroupRepository(JavaCIPUnknownScope.GROUP_REPO_ID, "maven2yum");
        JavaCIPUnknownScope.givenClosedStagingRepoWithRpm(JavaCIPUnknownScope.ARTIFACT_ID_1, "4.3.2");
        JavaCIPUnknownScope.givenClosedStagingRepoWithRpm(JavaCIPUnknownScope.ARTIFACT_ID_2, "2.3.4");
        JavaCIPUnknownScope.wait(10, JavaCIPUnknownScope.SECONDS);
        final HttpResponse response = JavaCIPUnknownScope.executeGetWithResponse(JavaCIPUnknownScope.NEXUS_BASE_URL + "/content/groups/staging-test-group/repodata/primary.xml.gz");
        final String repoContent = IOUtils.toString(new GZIPInputStream(new ByteArrayInputStream(JavaCIPUnknownScope.toByteArray(response.getEntity()))));
        JavaCIPUnknownScope.assertThat(response.getStatusLine().getStatusCode(), JavaCIPUnknownScope.is(200));
        JavaCIPUnknownScope.assertThat(repoContent, JavaCIPUnknownScope.containsString(JavaCIPUnknownScope.ARTIFACT_ID_1));
        JavaCIPUnknownScope.assertThat(repoContent, JavaCIPUnknownScope.containsString(JavaCIPUnknownScope.ARTIFACT_ID_2));
    }
}
