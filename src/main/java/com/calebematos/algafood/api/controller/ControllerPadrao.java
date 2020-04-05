package com.calebematos.algafood.api.controller;

public interface ControllerPadrao<T> {

	Iterable<T> listar();
	T buscar(Long id);
}
