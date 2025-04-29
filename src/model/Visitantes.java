
package model;

public class Visitantes extends Persona{
    private String correo;
    private String telefono;
    private int evaluacionesRealizadas;
    private String guiaSeleccionado;

    public Visitantes(String correo, String telefono, int evaluacionesRealizadas, String guiaSeleccionado, String nombre, String apellido, String genero, String edad, int id) {
        super(nombre, apellido, genero, edad, id);
        this.correo = correo;
        this.telefono = telefono;
        this.evaluacionesRealizadas = evaluacionesRealizadas;
        this.guiaSeleccionado = guiaSeleccionado;
    }
    
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getEvaluacionesRealizadas() {
        return evaluacionesRealizadas;
    }

    public void setEvaluacionesRealizadas(int evaluacionesRealizadas) {
        this.evaluacionesRealizadas = evaluacionesRealizadas;
    }

    public String getGuiaSeleccionado() {
        return guiaSeleccionado;
    }

    public void setGuiaSeleccionado(String guiaSeleccionado) {
        this.guiaSeleccionado = guiaSeleccionado;
    }
    
    public void ConsultarAnimales(){
        
    }
    
    public void AgendarVisita(){
        
    }
    
    public void SeleccionarGuia(){
        
    }
    
    public void EvaluarGuia(){
        
    }
    
    public void EvaluarSantuario(){
        
    }
    
    public void ConsultarZonas(){
        
    }
    
    public void CancelarVisita(){
        
    }
    
    public void ModificarDatosPersonales(){
        
    }
    
}
