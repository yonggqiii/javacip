class c17435276 {

    public void show(HttpServletRequest request, HttpServletResponse response, String pantalla, Atributos modelos) {
        URL url = JavaCIPUnknownScope.getRecurso(pantalla);
        try {
            IOUtils.copy(url.openStream(), response.getOutputStream());
        } catch (IORuntimeException e) {
            throw new RuntimeRuntimeException(e);
        }
    }
}
