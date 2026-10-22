package com.todo;

public class Tarefa {
    private int id;
    private String titulo;
    private String descricao; // Nova propriedade
    private String status;
    private String categoria;

    public Tarefa(int id, String titulo, String descricaoCifrada, String status, String categoria) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = CriptoUtil.decifrar(descricaoCifrada); // Decifra ao ler
        this.status = status;
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        String desc = (descricao == null) ? "Sem descrição" : descricao;
        return String.format("#%d [%s] %s (Cat: %s) - Desc: %s", id, status, titulo, categoria, desc);
    }
}