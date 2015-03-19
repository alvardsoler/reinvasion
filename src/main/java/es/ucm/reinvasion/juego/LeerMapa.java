/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.reinvasion.juego;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import com.google.gson.Gson;

/**
 *
 * @author alejandro
 */
public class LeerMapa {
    private File archivo;
    private FileReader fr;
    private FileReader frcopy;
    private BufferedReader br;

    
    public LeerMapa(String nombreArchivo) throws FileNotFoundException{
        archivo = new File (nombreArchivo);
        fr = new FileReader (archivo);
        frcopy = new FileReader (archivo);
        br = new BufferedReader(fr);
    }
    
    public HashMap<String,Integer> idAlfabeticos() throws IOException{
    	String linea;
    	int cont = 0;
    	Gson gson1 = new Gson();
    	HashMap <String,Integer> mapa1= new HashMap<String,Integer>();
        while((linea=br.readLine())!=null){
            String []a = linea.split(" ");
            mapa1.put(a[0],cont);
            cont++;
        }
       // System.out.println(gson1.toJson(mapa1));
        
        return mapa1;
            
    }
    
    public void inicializarMapa(JuegoPartida partida) throws IOException{
        String linea;
        Gson gson = new Gson();
        HashMap <String,Integer> mapa1= idAlfabeticos();
        br = new BufferedReader(frcopy);
        HashMap <String,Integer> mapa= new HashMap<String,Integer>();

        while((linea=br.readLine())!=null){
            String []a = linea.split(" ");
            ArrayList <Integer> paisesFront = new ArrayList();
            String paisIni="";
            for(int i=0; i<a.length;i++){
                if(!a[i].equals("->")){
                    if(!mapa.containsKey(a[i])){
                        mapa.put(a[i], mapa1.get(a[i]));
                        String key = a[i];
                        System.out.println(/*"Clave: " +*/ key + " Valor: " + mapa.get(key));
                    }
                    if(i==0){
                        paisesFront = new ArrayList();
                        paisIni = a[i];
                    }
                    else if(i!=0){
                        paisesFront.add(mapa.get(a[i]));
                    }
                    if(i+1==a.length){
                        partida.addPais(crearPais(paisesFront,mapa.get(paisIni)));
                    }
                }
            }
        }
        System.out.println(gson.toJson(mapa));
        fr.close();
    }

    
    public void asignarJugadoresPaises(JuegoPartida p){
        Integer[] perm = new Integer[p.getPaises().size()];
        int a;
        for(int i=0; i< p.getPaises().size();i++){
            perm[i]=i;
        }
        Collections.shuffle(Arrays.asList(perm));
        
        /*for(int i=0; i< perm.length; i++){
            System.out.println(perm[i]);
        }*/
        
        for(int i=0; i<p.getPaises().size(); i++){
            a=perm[i];
            int j = (i%p.getJugadores().size());
         
            p.getPaises().get(a).setPropietario(j);
            System.out.println("El país: " + p.getPaises().get(a).getId()+" es del jugador: " + j);
            p.getJugadores().get(j).addPais(a);
        }
        
        int repartoEquitativo = p.getPaises().size()%p.getJugadores().size();
        if(repartoEquitativo!=0){
            /*
                 Añadirles mas tropas iniciales a los jugadores
                    que tengan una cantidad menor de paises
            */
        }
    }
    
    public Pais crearPais(ArrayList<Integer> fronteras,int id){
        Pais a = new Pais(fronteras, 0,id);
        return a;
    }

    public static void main(String [] arg) throws FileNotFoundException, IOException{
        LeerMapa mapa = new LeerMapa("/home/furia/git/furia77/invasion2/src/main/java/es/ucm/invasion/juego/Mapa");
        JuegoPartida p = new JuegoPartida();
        p.addJugador("azul");
        p.addJugador("rojo");
        p.addJugador("blanco");
        
        //mapa1.idAlfabeticos();
      
        mapa.inicializarMapa(p);
        ArrayList<Pais> paises = new ArrayList<Pais>();
        paises = p.getPaises(); 
       for(int i=0; i<paises.size();i++){
           System.out.print(paises.get(i).getId() + " -> ");
           for(int j=0; j<paises.get(i).getFronteras().size();j++){
               System.out.print(paises.get(i).getFronteras().get(j)+ " ");
           }
           System.out.println();
       }
       mapa.asignarJugadoresPaises(p);
       System.out.println(p.serializa());
       System.out.println("Fin");
        
    }
}
