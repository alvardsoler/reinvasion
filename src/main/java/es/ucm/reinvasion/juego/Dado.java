/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.reinvasion.juego;
/**
 *
 * @author furia
 */
public class Dado {

    Dado() {

    }

    public static int tirarDado() {
        double resultado;
        int rango = 6;
        resultado = (Math.random() * rango) + 1;

        return (int) resultado;
    }

    public static int[] tirarDados(int unidadesAtacantes, int unidadesDefensoras) {
        /* Dependiendo del número de unidades: 
         Atacante:   2 unidades  -> 1 dado
         3 unidades  -> 2 dados
         4 o más     -> 3 dados
         Defensor:   1 unidad    -> 1 dado
         2 o más     -> 2 dados
         */
        int r[] = new int[2];
        switch (unidadesAtacantes) {
            case 2:
                r[0] = 1;
                break;
            case 3:
                r[0] = 2;
                break;
            default:
                r[0] = 3;
                break;
        }
        switch (unidadesDefensoras) {
            case 1:
                r[1] = 1;
                break;
            default:
                r[1] = 2;
                break;
        }
        return r;
    }

}
