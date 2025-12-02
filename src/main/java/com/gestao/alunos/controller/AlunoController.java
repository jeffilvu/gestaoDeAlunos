
package com.gestao.alunos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gestao.alunos.model.Aluno;
import com.gestao.alunos.repository.AlunoRepository;
import com.gestao.alunos.repository.CursoRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/alunos") // Prefixo para todas as rotas deste controller
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;
    
    @Autowired
    private CursoRepository cursoRepository; // Necessário para o dropdown

    // Rota: /alunos
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("alunos", alunoRepository.findAll());
        return "alunos/listagem"; // Aponta para src/main/resources/templates/alunos/listagem.html
    }

    // Rota: /alunos/novo
    @GetMapping("/novo")
    public String novoForm(Aluno aluno, Model model) {
        // Adiciona a lista de cursos ao modelo para popular o dropdown
        model.addAttribute("cursos", cursoRepository.findAll());
        return "alunos/formulario"; // Aponta para src/main/resources/templates/alunos/formulario.html
    }

    // Rota: /alunos (recebe POST)
    @PostMapping
    public String salvar(@Valid Aluno aluno, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Se houver erros, precisamos recarregar a lista de cursos antes de voltar
            model.addAttribute("cursos", cursoRepository.findAll());
            return "alunos/formulario";
        }
        
        alunoRepository.save(aluno);
        return "redirect:/alunos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") long id) {
        if (!alunoRepository.existsById(id)) {
             // Tratamento de erro
        }
        
        alunoRepository.deleteById(id);
        return "redirect:/alunos"; // Redireciona de volta para a listagem
    }
}