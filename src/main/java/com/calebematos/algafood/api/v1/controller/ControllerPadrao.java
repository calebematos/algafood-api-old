package com.calebematos.algafood.api.v1.controller;

public interface ControllerPadrao<T> {

	Iterable<T> listar();
	T buscar(Long id);
}
