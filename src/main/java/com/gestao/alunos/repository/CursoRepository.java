package com.gestao.alunos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestao.alunos.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}