class c8174539 {

    public static void invokeServlet(String op, String user) throws RuntimeException {
        boolean isSayHi = true;
        try {
            if (!"sayHi".equals(op)) {
                isSayHi = false;
            }
            URL url = new URL("http://localhost:9080/helloworld/*.do");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            out.write("Operation=" + op);
            if (!isSayHi) {
                out.write("&User=" + user);
            }
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            boolean correctReturn = false;
            String response;
            if (isSayHi) {
                while ((response = in.readLine()) != null) {
                    if (response.contains("Bonjour")) {
                        System.out.println(" sayHi server return: Bonjour");
                        correctReturn = true;
                        break;
                    }
                }
            } else {
                while ((response = in.readLine()) != null) {
                    if (response.contains("Hello CXF")) {
                        System.out.println(" greetMe server return: Hello CXF");
                        correctReturn = true;
                        break;
                    }
                }
            }
            if (!correctReturn) {
                System.out.println("Can't got correct return from server.");
            }
            in.close();
        } catch (MalformedURLRuntimeException ex) {
            ex.printStackTrace();
        } catch (IORuntimeException ex) {
            ex.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
