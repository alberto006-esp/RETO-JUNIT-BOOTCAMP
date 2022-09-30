package com.example.baseDatos;

import java.util.List;

import com.example.model.Articulo;

public interface baseDatosI {

	public void iniciarBBDD();
	
	public List<Articulo> getArticulos();

	public Articulo buscarArticuloPorId(int id);
	
	public Integer insertarArticuloBBDD(Articulo articulo);
}
