class c19446036 {

    protected void givenGroupRepository(String repoId, String providerId, Repository... memberRepos) throws AuthenticationRuntimeException, UnsupportedEncodingRuntimeException, IORuntimeException, ClientProtocolRuntimeException {
        HttpResponse response = JavaCIPUnknownScope.executeDeleteWithResponse(JavaCIPUnknownScope.format("/repo_groups/%s", repoId));
        JavaCIPUnknownScope.consume(response.getEntity());
        final StringEntity content = new StringEntity(JavaCIPUnknownScope.format("{data:{id: '%s', name: '%s', provider: '%s', exposed: true, repositories: [%s]}}", repoId, repoId, providerId, JavaCIPUnknownScope.render(memberRepos)));
        response = JavaCIPUnknownScope.executePost("/repo_groups", content, new BasicHeader("Content-Type", "application/json"));
        JavaCIPUnknownScope.consume(response.getEntity());
        JavaCIPUnknownScope.assertThat(response.getStatusLine().getStatusCode(), JavaCIPUnknownScope.is(201));
    }
}
