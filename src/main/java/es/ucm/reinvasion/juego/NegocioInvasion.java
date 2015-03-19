/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.reinvasion.juego;

import com.google.gson.Gson;

/**
 *
 * @author furia
 */
public class NegocioInvasion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JuegoPartida p = new JuegoPartida();
        p.addJugador("#ffff00");
        p.addJugador("#aaaaaa");
        p.addPais(0);
        p.addPais(0);
        p.addPais(1);
        p.addFrontera(0, 1);
        p.addFrontera(1, 2);
        p.addFrontera(2, 0);
        p.getPais(0).addUnidades(4);
        p.getPais(2).addUnidades(3);
        
        p.atacar(p.getPais(0), p.getPais(1));
        String j = p.serializa();
        System.out.println(j);
        
        String j2 = JuegoPartida.deserializa(j).serializa();
        System.out.println(j2);
        System.out.println(j.equals(j2));
    }
}
