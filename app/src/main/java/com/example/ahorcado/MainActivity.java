package com.example.ahorcado;
/*
 *Autor:JAvier de la Llave
 */
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    String palabraOculta=eligePalabra();//Palabra que hay que adivinar

    int nFallos=0;//Número de fllos que lleva el jugador

    public boolean partidaTerminada=false;//Nos indica si la partida ha terminado o no

    ArrayList<Button> listaBotones = new ArrayList<Button>();//Guardamos los botones que hansido pulsados



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.ventanaJuego, new VentanaAhorcado() ).commit();

        }
    }

    protected void onStart() {
        super.onStart();

        guionesIniciales();

        //Invisibilizamos el botón reiniciar para que se vea solo al terminar
        Button botonR = findViewById(R.id.buttonReiniciar);
        botonR.setVisibility(View.INVISIBLE);

    }

    //Pone los guiones al principio en funcion de la palabra elegida
    private void guionesIniciales(){
        palabraOculta = eligePalabra();
        String barras = "";
        for (int i = 0; i < palabraOculta.length(); i++) {
            barras += "_ ";
        }
        ((TextView) findViewById(R.id.palabraConGuiones)).setText(barras);
    }



    public void botonPulsado(View vista){
        if(!partidaTerminada) {
            Button boton = findViewById(vista.getId());
            boton.setVisibility(View.INVISIBLE);
            //Guardamos los botones pulsados en la lista para poderlos poner visibles otra vez
            chequeaLetra(boton.getText().toString());
            listaBotones.add(boton);
        }
    }

    private void chequeaLetra(String letra){
        letra= letra.toUpperCase();
        ImageView imagenAhorcado = ((ImageView) findViewById(R.id.imagenAhorcado));
        TextView textoConGuiones= ((TextView) findViewById(R.id.palabraConGuiones));
        String palabraConGuiones = textoConGuiones.getText().toString();

        boolean acierto = false;

        for (int i=0; i<palabraOculta.length(); i++){
            if (palabraOculta.charAt(i) == letra.charAt(0)){//Si esta la palabra la ponemos donde los guiones
                //quita el guión bajo de la letra correspondiente
                palabraConGuiones = palabraConGuiones.substring(0, 2*i)
                        + letra
                        + palabraConGuiones.substring(2*i+1);
                acierto = true;
            }
        }
        if (!palabraConGuiones.contains("_")){//Si no quedan guiones la partida ha terminado
            imagenAhorcado.setImageResource(R.drawable.acertastetodo);
            partidaTerminada=true;
        }
        textoConGuiones.setText(palabraConGuiones);

        if (!acierto){
            nFallos++;
            if (nFallos >= 6) {//Si hay  6 fallos o más la partida ha terminado
                partidaTerminada = true;
            }
            switch (nFallos){//En funcion del número de fallos aparece una foto u otra
                case 0 : imagenAhorcado.setImageResource(R.drawable.ahorcado_0); break;
                case 1 : imagenAhorcado.setImageResource(R.drawable.ahorcado_1); break;
                case 2 : imagenAhorcado.setImageResource(R.drawable.ahorcado_2); break;
                case 3 : imagenAhorcado.setImageResource(R.drawable.ahorcado_3); break;
                case 4 : imagenAhorcado.setImageResource(R.drawable.ahorcado_4); break;
                case 5 : imagenAhorcado.setImageResource(R.drawable.ahorcado_5); break;
                default : imagenAhorcado.setImageResource(R.drawable.ahorcado_fin); break;
            }
        }

        //Si la partida termina el botón de reiniciar está visible
        if (partidaTerminada){
            Button botonR = findViewById(R.id.buttonReiniciar);
            botonR.setVisibility(View.VISIBLE);
        }

    }

    //Va a seleccionar al azar una palabra de un array de palabras
    private String eligePalabra() {
        String[] listaPalabras = {"AVESTRUZ", "BOTA", "CARTA", "DADO","ESTROPAJO","FAROLA",
                "GAVIOTA", "HELADO", "IMAGEN", "JAULA", "KIMONO", "LECHE", "MASCARILLA",
                "NOMBRE", "ÑU", "OLEAJE", "PALABRA", "QUESO", "RATA", "SOPA", "TORO",
                "UNIDAD", "VICTORIA", "WINDSURF", "XILOFONO", "YOGUR", "ZAPATO"};

        Random aleatorio = new Random(); //Variable aleatoria para elegir palabra

        int posicion = aleatorio.nextInt(listaPalabras.length);

        return listaPalabras[posicion].toUpperCase();
    }

    //Al ser llamado reinicia la partida
    public void reiniciar(View vista){
        Button boton = findViewById(vista.getId());
        guionesIniciales();
        partidaTerminada = false;
        nFallos=0;
        ImageView imagenAhorcado = ((ImageView) findViewById(R.id.imagenAhorcado));
        imagenAhorcado.setImageResource(R.drawable.ahorcado_0);

        // Declaramos el Iterador y ponemos los botones visibles de nuevo
        Iterator<Button> botonIterator = listaBotones.iterator();
        while(botonIterator.hasNext()){
            Button elemento = botonIterator.next();
            elemento.setVisibility(View.VISIBLE);
        }
        //Vaciamos la lista de botones
        listaBotones.clear();
        //Ponemos el botón de reiniciar invisible de nuevo
        Button botonR = findViewById(R.id.buttonReiniciar);
        botonR.setVisibility(View.INVISIBLE);
    }

}













