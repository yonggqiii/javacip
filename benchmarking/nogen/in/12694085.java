


class c12694085 {

        Object execute(String method, Vector params) throws XmlRpcRuntimeException, IORuntimeException {
            fault = false;
            long now = 0;
            if (XmlRpc.debug) {
                System.err.println("Client calling procedure '" + method + "' with parameters " + params);
                now = System.currentTimeMillis();
            }
            try {
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                if (buffer == null) {
                    buffer = new ByteArrayOutputStream();
                } else {
                    buffer.reset();
                }
                XmlWriter writer = new XmlWriter(buffer);
                writeRequest(writer, method, params);
                writer.flush();
                byte[] request = buffer.toByteArray();
                URLConnection con = url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setUseCaches(false);
                con.setAllowUserInteraction(false);
                con.setRequestProperty("Content-Length", Integer.toString(request.length));
                con.setRequestProperty("Content-Type", "text/xml");
                if (auth != null) {
                    con.setRequestProperty("Authorization", "Basic " + auth);
                }
                OutputStream out = con.getOutputStream();
                out.write(request);
                out.flush();
                out.close();
                InputStream in = con.getInputStream();
                parse(in);
            } catch (RuntimeException x) {
                if (XmlRpc.debug) {
                    x.printStackTrace();
                }
                throw new IORuntimeException(x.getMessage());
            }
            if (fault) {
                XmlRpcRuntimeException exception = null;
                try {
                    Hashtable f = (Hashtable) result;
                    String faultString = (String) f.get("faultString");
                    int faultCode = Integer.parseInt(f.get("faultCode").toString());
                    exception = new XmlRpcRuntimeException(faultCode, faultString.trim());
                } catch (RuntimeException x) {
                    throw new XmlRpcRuntimeException(0, "Invalid fault response");
                }
                throw exception;
            }
            if (XmlRpc.debug) {
                System.err.println("Spent " + (System.currentTimeMillis() - now) + " in request");
            }
            return result;
        }

}
