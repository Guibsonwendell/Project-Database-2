package com.todo;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static TarefaDAO tarefaDAO = new TarefaDAO();
    private static CategoriaDAO catDAO = new CategoriaDAO();
    private static EstatisticaService estatisticas = new EstatisticaService();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== SISTEMA TO-DO LIST ===");
            System.out.println("1) Gerenciar Tarefas");
            System.out.println("2) Gerenciar Categorias");
            System.out.println("3) Ver Estatísticas");
            System.out.println("0) Sair");

            String opcao = lerTexto("Escolha: ");
            try {
                switch (opcao) {
                    case "1" -> menuTarefas();
                    case "2" -> menuCategorias();
                    case "3" -> estatisticas.mostrar();
                    case "0" -> { System.out.println("Saindo..."); return; }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private static void menuTarefas() {
        while (true) {
            System.out.println("\n--- TAREFAS ---");
            System.out.println("1) Listar Todas");
            System.out.println("2) Listar por Status");
            System.out.println("3) Adicionar Tarefa");
            System.out.println("4) Editar Tarefa (Título e Descrição)"); // Alterado aqui
            System.out.println("5) Atualizar Status");
            System.out.println("6) Excluir");
            System.out.println("0) Voltar");

            String opcao = lerTexto("Escolha: ");
            try {
                switch (opcao) {
                    case "1" -> tarefaDAO.listar().forEach(System.out::println);
                    case "2" -> {
                        String st = lerTexto("Status (NOVA, EM_ANDAMENTO, CONCLUIDA, PENDENTE): ").toUpperCase();
                        tarefaDAO.listarPorStatus(st).forEach(System.out::println);
                    }
                    case "3" -> {
                        String titulo = lerTexto("Título: ");
                        String descricao = lerTexto("Descrição (Enter para vazio): ");
                        System.out.println("--- Categorias ---");
                        catDAO.listar().forEach(System.out::println);
                        String catInput = lerTexto("ID da Categoria (Enter para Geral): ");
                        int catId = catInput.isEmpty() ? 1 : Integer.parseInt(catInput);
                        tarefaDAO.inserir(titulo, descricao, catId);
                        System.out.println("Tarefa adicionada!");
                    }
                    case "4" -> { // Alterado aqui
                        int id = Integer.parseInt(lerTexto("ID da tarefa a editar: "));
                        String novoTitulo = lerTexto("Novo título: ");
                        String novaDescricao = lerTexto("Nova descrição: ");
                        tarefaDAO.editarTarefa(id, novoTitulo, novaDescricao);
                        System.out.println("Tarefa atualizada com sucesso!");
                    }
                    case "5" -> {
                        int id = Integer.parseInt(lerTexto("ID da tarefa: "));
                        String st = lerTexto("Novo Status: ").toUpperCase();
                        tarefaDAO.atualizarStatus(id, st);
                        System.out.println("Status atualizado!");
                    }
                    case "6" -> {
                        int id = Integer.parseInt(lerTexto("ID da tarefa: "));
                        tarefaDAO.excluir(id);
                        System.out.println("Tarefa excluída!");
                    }
                    case "0" -> { return; }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); }
        }
    }

    private static void menuCategorias() {
        while (true) {
            System.out.println("\n--- CATEGORIAS ---");
            System.out.println("1) Listar");
            System.out.println("2) Adicionar");
            System.out.println("3) Editar");
            System.out.println("4) Excluir");
            System.out.println("0) Voltar");

            String opcao = lerTexto("Escolha: ");
            try {
                switch (opcao) {
                    case "1" -> catDAO.listar().forEach(System.out::println);
                    case "2" -> {
                        String nome = lerTexto("Nome da Categoria: ");
                        catDAO.inserir(nome);
                        System.out.println("Categoria adicionada!");
                    }
                    case "3" -> {
                        int id = Integer.parseInt(lerTexto("ID da categoria: "));
                        String nome = lerTexto("Novo nome: ");
                        catDAO.editar(id, nome);
                        System.out.println("Categoria atualizada!");
                    }
                    case "4" -> {
                        int id = Integer.parseInt(lerTexto("ID da categoria: "));
                        catDAO.excluir(id);
                        System.out.println("Categoria excluída!");
                    }
                    case "0" -> { return; }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) { System.out.println("Erro: " + e.getMessage()); }
        }
    }

    // Método de validação de console (impede campos vazios em títulos e nomes)
    private static String lerTexto(String prompt) {
        while (true) {
            System.out.print(prompt);
            String txt = sc.nextLine().trim();
            if (!prompt.contains("Enter para") && !prompt.contains("Descrição") && txt.isEmpty()) {
                System.out.println("Erro: Este campo não pode ficar em branco.");
            } else {
                return txt;
            }
        }
    }
}