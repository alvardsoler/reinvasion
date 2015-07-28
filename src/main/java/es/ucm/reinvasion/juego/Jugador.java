package es.ucm.reinvasion.juego;

import java.awt.Color;
import java.util.ArrayList;

public class Jugador {

    private int id;
    private ArrayList<Integer> paisesControlados;
    private ArrayList<Carta> cartas;
    private int unidadesSinDesplegar;

    private String color;

    public Jugador(String color, int id) {
        this.id = id;
        this.color = color;
        this.cartas = new ArrayList<Carta>();
        this.paisesControlados = new ArrayList<Integer>();
    }

    public Jugador(String color, int id, ArrayList<Integer> paisesControlados, ArrayList<Carta> cartas) {
        this.id = id;
        this.color = color;
        this.cartas = cartas;
        this.paisesControlados = paisesControlados;
    }
    
    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }
    
    public boolean controlaPais(Integer pais){
        return paisesControlados.contains(pais);
    }

    public void addPais(Integer pais) {
        this.paisesControlados.add(pais);
    }
    
    public void quitarPais(Integer pais){
        this.paisesControlados.remove(pais);
    }

    public void addCarta(Carta carta) {
        this.cartas.add(carta);
    }
    
    public int getUnidadesSinDesplegar() {
        return unidadesSinDesplegar;
    }

    public void setUnidadesSinDesplegar(int unidadesSinDesplegar) {
        this.unidadesSinDesplegar = unidadesSinDesplegar;
    }
}
