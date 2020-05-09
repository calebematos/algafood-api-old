package com.calebematos.algafood.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.calebematos.algafood.api.v1.assembler.PermissaoModelAssembler;
import com.calebematos.algafood.api.v1.model.PermissaoModel;
import com.calebematos.algafood.api.v1.openapi.controller.PermissaoControllerOpenApi;
import com.calebematos.algafood.core.security.CheckSecurity;
import com.calebematos.algafood.domain.model.Permissao;
import com.calebematos.algafood.domain.repository.PermissaoRepository;

@RestController
@RequestMapping(path = "/v1/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi {

    @Autowired
    private PermissaoRepository permissaoRepository;
    
    @Autowired
    private PermissaoModelAssembler permissaoModelAssembler;
    
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<PermissaoModel> listar() {
        List<Permissao> todasPermissoes = permissaoRepository.findAll();
        
        return permissaoModelAssembler.toCollectionModel(todasPermissoes);
    } 
}
