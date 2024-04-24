package com.zelys.starwarsmovieapp.logica;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class GeneradorDeArchivo {
    public void guardarJson(Pelicula pelicula) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter escritura = new FileWriter(pelicula.title() + ".jason");
        escritura.write(gson.toJson(pelicula));
        escritura.close();
    }
}
