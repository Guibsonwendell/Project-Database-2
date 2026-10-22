-- ============================================================
-- TO-DO LIST — Script completo (MySQL 8.0.16+)
-- Rode no MySQL Workbench: Ctrl+Shift+Enter
-- ============================================================

-- ---------- 1) BANCO ----------
CREATE DATABASE IF NOT EXISTS todo
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
USE todo;

-- ---------- 2) TABELAS ----------
CREATE TABLE IF NOT EXISTS categoria (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    nome        VARCHAR(50)  NOT NULL UNIQUE,
    descricao   VARCHAR(255) NULL,
    criada_em   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS tarefa (
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    titulo             VARCHAR(100) NOT NULL,
    descricao_cifrada  TEXT         NULL,
    status             VARCHAR(20)  NOT NULL DEFAULT 'NOVA',
    categoria_id       INT          NOT NULL,
    criada_em          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizada_em      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
                                    ON UPDATE CURRENT_TIMESTAMP,
    concluida_em       DATETIME     NULL,
    CONSTRAINT ck_tarefa_status
        CHECK (status IN ('NOVA','EM_ANDAMENTO','CONCLUIDA','PENDENTE')),
    CONSTRAINT fk_tarefa_categoria
        FOREIGN KEY (categoria_id) REFERENCES categoria(id),
    INDEX idx_tarefa_status    (status),
    INDEX idx_tarefa_categoria (categoria_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------- 3) SEED ----------
INSERT IGNORE INTO categoria (id, nome, descricao)
VALUES (1, 'Geral', 'Categoria padrão');

ALTER TABLE categoria AUTO_INCREMENT = 2;

-- ---------- 4) DIFERENCIAL — VIEW ----------
CREATE OR REPLACE VIEW vw_tarefas_detalhadas AS
SELECT
    t.id,
    t.titulo,
    t.descricao_cifrada,
    t.status,
    t.categoria_id,
    c.nome AS cat_nome,
    t.criada_em,
    t.atualizada_em,
    t.concluida_em
FROM tarefa t
INNER JOIN categoria c ON c.id = t.categoria_id;

-- ---------- 5) DIFERENCIAL — PROCEDURE ----------
DELIMITER $$

DROP PROCEDURE IF EXISTS sp_estatisticas $$
CREATE PROCEDURE sp_estatisticas()
BEGIN
    SELECT 'total'      AS metrica, COUNT(*) AS valor FROM tarefa
    UNION ALL
    SELECT 'abertas',    COUNT(*) FROM tarefa WHERE status <> 'CONCLUIDA'
    UNION ALL
    SELECT 'concluidas', COUNT(*) FROM tarefa WHERE status = 'CONCLUIDA';
END $$

DELIMITER ;

-- ---------- 6) DADOS DE TESTE ----------
INSERT IGNORE INTO categoria (nome, descricao) VALUES
  ('Universidade', 'Tarefas da faculdade'),
  ('Trabalho',     'Tarefas profissionais'),
  ('Pessoal',      'Tarefas pessoais');

INSERT INTO tarefa (titulo, descricao_cifrada, status, categoria_id) VALUES
  ('Entregar avaliação final', NULL, 'EM_ANDAMENTO',
      (SELECT id FROM categoria WHERE nome = 'Universidade')),
  ('Estudar para prova de BD', NULL, 'NOVA',
      (SELECT id FROM categoria WHERE nome = 'Universidade')),
  ('Responder e-mails',        NULL, 'PENDENTE',
      (SELECT id FROM categoria WHERE nome = 'Trabalho')),
  ('Comprar leite',            NULL, 'CONCLUIDA', 1),
  ('Lavar o carro',            NULL, 'NOVA', 1);

-- ---------- 7) CONFERÊNCIA (opcional) ----------
SELECT * FROM categoria;
SELECT * FROM vw_tarefas_detalhadas;
CALL sp_estatisticas();