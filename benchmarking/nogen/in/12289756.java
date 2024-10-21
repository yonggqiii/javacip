


class c12289756 {

    public String loadURL(URL url) {
        String retVal = "";
        try {
            InputStream inputStream = url.openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            retVal += line + "\n";
            while (line != null) {
                System.out.println(line);
                line = bufferedReader.readLine();
                if (line != null) retVal += line + "\n";
            }
            bufferedReader.close();
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
            retVal = e.getMessage();
        } catch (IORuntimeException e) {
            e.printStackTrace();
            retVal = e.getMessage();
        } catch (RuntimeException e) {
            e.printStackTrace();
            retVal = e.getMessage();
        }
        return retVal;
    }

}
