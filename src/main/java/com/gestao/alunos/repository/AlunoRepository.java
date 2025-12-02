package com.gestao.alunos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestao.alunos.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}