package com.gestao.alunos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gestao.alunos.model.Curso;
import com.gestao.alunos.repository.CursoRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/cursos") // Prefixo para todas as rotas deste controller
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    // Rota: /cursos
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("cursos", cursoRepository.findAll());
        return "cursos/listagem"; // Aponta para src/main/resources/templates/cursos/listagem.html
    }

    // Rota: /cursos/novo
    @GetMapping("/novo")
    public String novoForm(Curso curso, Model model) {
        // Envia um objeto Curso vazio para o formulário
        return "cursos/formulario"; // Aponta para src/main/resources/templates/cursos/formulario.html
    }

    // Rota: /cursos (recebe POST)
    @PostMapping
    public String salvar(@Valid Curso curso, BindingResult result) {
        // @Valid ativa as validações definidas no Model (Curso.java)
        if (result.hasErrors()) {
            // Se houver erros, retorna para o formulário para exibir as mensagens
            return "cursos/formulario";
        }
        
        cursoRepository.save(curso); // Salva o curso no banco de dados
        return "redirect:/cursos"; // Redireciona para a listagem
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") long id) {
        // Verifica se o curso existe (opcional, mas boa prática)
        if (!cursoRepository.existsById(id)) {
             // Pode adicionar um tratamento de erro ou mensagem flash aqui.
        }

        try {
            cursoRepository.deleteById(id);
        } catch (Exception e) {
            // Se houver alunos associados (restrição de chave estrangeira), a exclusão falhará.
            // Em produção, você lidaria com essa exceção de forma mais amigável.
            System.err.println("Erro ao excluir curso: " + e.getMessage());
            // Você pode redirecionar para uma página de erro ou listagem com mensagem.
        }

        return "redirect:/cursos"; // Redireciona de volta para a listagem
    }

    
}