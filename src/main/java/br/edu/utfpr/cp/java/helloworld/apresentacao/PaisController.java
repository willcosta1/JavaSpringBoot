package br.edu.utfpr.cp.java.helloworld.apresentacao;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Controller
@Log
@RequiredArgsConstructor
public class PaisController {
    @Autowired
    private final PaisRepository paisRepository;

    /* Lombok com @RequiredArgsConstructor está realizando a mesma função
    public PaisController(PaisRepository paisRepository){
        this.paisRepository = paisRepository;
    }
    */

    private void adicionaListaPaisesNaMemoria(Model memoria){
        memoria.addAttribute("listaPaises", paisRepository.findAll());
    }

    @GetMapping ("/pais")
    public String listar(Model memoria){
        this.adicionaListaPaisesNaMemoria(memoria);
        return "pais-page";
    }
    @GetMapping ("/pais/apagar")
    public String apagar(@RequestParam Long id){
        paisRepository.deleteById(id);

        return "redirect:/pais";
    }
    @PostMapping("/pais/criar")
    public String criar(@Valid PaisModel pais, BindingResult result, Model memoria){
        if(result.hasErrors()){
            result.getFieldErrors().forEach(erro -> memoria.addAttribute(erro.getField(),erro.getDefaultMessage()));

            this.adicionaListaPaisesNaMemoria(memoria);
            memoria.addAttribute("paisAtual", pais);

            return "pais-page";
        }

        paisRepository.save(pais);

        return "redirect:/pais";
    }
    @GetMapping("/pais/preparaAlterar")
    public String preparaAlterar(@RequestParam Long id, Model memoria){
        var pais = paisRepository.findById(id).get();
        memoria.addAttribute("paisAtual", pais);
        this.adicionaListaPaisesNaMemoria(memoria);
        memoria.addAttribute("alterar", true);

        return "pais-page";
    }
    @PostMapping("/pais/alterar")
    public String alterar(PaisModel paisNovo){

        var pais = paisRepository.findById(paisNovo.getId()).get();

        pais.setNome(paisNovo.getNome());
        pais.setSigla(paisNovo.getSigla());

        paisRepository.saveAndFlush(pais);

        return "redirect:/pais";
    }
}