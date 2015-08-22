package es.ucm.reinvasion.juego;

import java.util.ArrayList;

public class Pais {
    
    private final int id;
    private final ArrayList<Integer> paisesFrontera;
    private int unidades;
    private long propietario;

    public Pais(ArrayList<Integer> paisesFrontera, long propietario, int id) {
        this.paisesFrontera = paisesFrontera;
        this.propietario = propietario;
        this.unidades = 1;
        this.id = id;
        
    }

    public Pais(long propietario, int id) {
        this.paisesFrontera = new ArrayList<Integer>();
        this.propietario = propietario;
        this.unidades = 1;
        this.id = id;
    }
    
    public ArrayList<Integer> getFronteras (){
        return paisesFrontera;
    }

    public void addFrontera(int idOtro) {
        paisesFrontera.add(idOtro);
    }

    public void addUnidades(int cantidad) {
        this.unidades += cantidad;
    }

    public void quitarUnidades(int cantidad) {
        this.unidades -= cantidad;
    }

    public boolean esFrontera(Pais pais) {
        return paisesFrontera.contains(pais.id);
    }

    public void addUnidad() {
        this.unidades++;
    }
    
    public int getId(){
        return id;
    }
    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public long getPropietario() {
        return propietario;
    }

    public void setPropietario(long propietario) {
        this.propietario = propietario;
    }
}
