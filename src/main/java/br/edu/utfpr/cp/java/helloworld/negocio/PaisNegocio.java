package br.edu.utfpr.cp.java.helloworld.negocio;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.utfpr.cp.java.helloworld.entidade.PaisModel;
import br.edu.utfpr.cp.java.helloworld.persistencia.PaisRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PaisNegocio {

    private final PaisRepository paisRepository;

    public void criar(PaisModel pais) throws Exception {
        if(paisRepository.findByNome(pais.getNome()).isPresent()){
            throw new Exception("Já existe uma país com esse nome!");
        }else{
            paisRepository.save(pais);
        }
    }

	public List<PaisModel> findAll() {
		return paisRepository.findAll();
	}

	public void deleteById(Long id) {
        paisRepository.deleteById(id);
	}

	public PaisModel findById(Long id) {
		return paisRepository.findById(id).get();
	}

	public void saveAndFlush(PaisModel pais) {
        paisRepository.saveAndFlush(pais);
	}

}