package com.example.grupofacil;

import java.util.ArrayList;

public class Grupo {
    private String nombre;
    private String descripcion;
    private ArrayList<String> participantes;
    private int participantesPorGrupo;

    public Grupo(String nombre, String descripcion, ArrayList<String> participantes, int participantesPorGrupo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.participantes = participantes;
        this.participantesPorGrupo = participantesPorGrupo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ArrayList<String> getParticipantes() {
        return participantes;
    }

    public int getParticipantesPorGrupo() {
        return participantesPorGrupo;
    }
}
