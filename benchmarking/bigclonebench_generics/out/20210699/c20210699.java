class c20210699 {

    public List<RTTicket> getTicketsForQueue(final String queueName, long limit) {
        JavaCIPUnknownScope.getSession();
        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("query", "Queue='" + queueName + "' AND Status='open'"));
        params.add(new BasicNameValuePair("format", "i"));
        params.add(new BasicNameValuePair("orderby", "-id"));
        final HttpGet get = new HttpGet(JavaCIPUnknownScope.m_baseURL + "/REST/1.0/search/ticket?" + URLEncodedUtils.format(params, "UTF-8"));
        final List<RTTicket> tickets = new ArrayList<RTTicket>();
        final List<Long> ticketIds = new ArrayList<Long>();
        try {
            final HttpResponse response = JavaCIPUnknownScope.getClient().execute(get);
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode != HttpStatus.SC_OK) {
                throw new RequestTrackerRuntimeException("Received a non-200 response code from the server: " + responseCode);
            } else {
                InputStreamReader isr = null;
                BufferedReader br = null;
                try {
                    if (response.getEntity() == null)
                        return null;
                    isr = new InputStreamReader(response.getEntity().getContent());
                    br = new BufferedReader(isr);
                    String line = null;
                    do {
                        line = br.readLine();
                        if (line != null) {
                            if (line.contains("does not exist.")) {
                                return null;
                            }
                            if (line.startsWith("ticket/")) {
                                ticketIds.add(Long.parseLong(line.replace("ticket/", "")));
                            }
                        }
                    } while (line != null);
                } catch (final RuntimeException e) {
                    throw new RequestTrackerRuntimeException("Unable to read ticket IDs from query.", e);
                } finally {
                    IOUtils.closeQuietly(br);
                    IOUtils.closeQuietly(isr);
                }
            }
        } catch (final RuntimeException e) {
            LogUtils.errorf(this, e, "An exception occurred while getting tickets for queue " + queueName);
            return null;
        }
        for (final Long id : ticketIds) {
            try {
                tickets.add(JavaCIPUnknownScope.getTicket(id, false));
            } catch (final RequestTrackerRuntimeException e) {
                LogUtils.warnf(this, e, "Unable to retrieve ticket.");
            }
        }
        return tickets;
    }
}
