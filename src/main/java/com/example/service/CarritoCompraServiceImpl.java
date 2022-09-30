package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.baseDatos.baseDatosI;
import com.example.model.Articulo;

@Service
public class CarritoCompraServiceImpl implements CarritoCompraServiceI{

	private List<Articulo> cesta = new ArrayList<Articulo>();
	
	
	@Autowired
	private baseDatosI baseDatos;
	
	
	@Override
	public void limpiarCesta() {
		cesta.clear();	
	}
	@Override
	public void addArticulo(Articulo articulo) {
		cesta.add(articulo);
	}
	@Override
	public int getNumArticulo() {
		return cesta.size();
	}
	@Override
	public List<Articulo> getArticulos() {
		return cesta;
	}
	@Override
	public double totalPrice() {
		double total = 0;
		for (Articulo articulo : cesta) {
			total = total + articulo.getPrecio();
		}
		return total;
	}
	@Override
	public double calculadorDescuento(double precio, double porcentajeDescuento) {
		return precio - (precio * (porcentajeDescuento/100)) ;
	}
	@Override
	public List<Articulo> getArticulosBBDD() {
		baseDatos.iniciarBBDD();
		return baseDatos.getArticulos();
	}
	@Override
	public Double aplicarDescuento(Integer id, Double porcentaje) {
		Articulo articulo = baseDatos.buscarArticuloPorId(1);
		if (Optional.ofNullable(articulo).isPresent()) {
			return calculadorDescuento(articulo.getPrecio(), porcentaje);
		}else {
			System.out.println("No se ha encontrado el articulo con ID:" + id);
		}
		return null;
	}
	@Override
	public Integer insertarArticulo(Articulo articulo) {
		
		Integer id= baseDatos.insertarArticuloBBDD(articulo);
		cesta.add(articulo);
		return id;
	}

}
