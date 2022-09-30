package com.example.service;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.baseDatos.baseDatosI;
import com.example.model.Articulo;

@ExtendWith(MockitoExtension.class)
public class CarritoCompraServiceImplTest {

	@InjectMocks
	private CarritoCompraServiceI carrito = new CarritoCompraServiceImpl();
	
	@Mock
	private baseDatosI baseDatos;
	@Test
	public void testLimpiarCesta() {
		carrito.limpiarCesta();
		assertTrue(carrito.getArticulos().isEmpty());
	}

	@Test
	public void testAddArticulo() {
		assertTrue(carrito.getArticulos().isEmpty());
		carrito.addArticulo(new Articulo("pantalon",10));
		assertFalse(carrito.getArticulos().isEmpty());
	}

	@Test
	public void testGetNumArticulo() {
		carrito.addArticulo(new Articulo("pantalon",10));
		carrito.addArticulo(new Articulo("camisa",15));
		Integer resultado = carrito.getNumArticulo();
		assertEquals(2, resultado);
	}

	@Test
	public void testGetArticulos() {
		carrito.addArticulo(new Articulo("pantalon",10));
		carrito.addArticulo(new Articulo("camisa",30));
		List<Articulo> listado = carrito.getArticulos();
		assertEquals(2, listado.size());
		assertEquals("camisa", listado.get(1).getNombre());
	}

	@Test
	public void testTotalPrice() {
		carrito.addArticulo(new Articulo("pantalon",10));
		carrito.addArticulo(new Articulo("camisa",15));
		Double resultado = carrito.totalPrice();
		assertEquals(25, resultado);
	}

	@Test
	public void testCalculadorDescuento() {
		assertEquals(27, carrito.calculadorDescuento(30, 10));
	}

	@Test
	public void testGetArticulosBBDD() {
		List<Articulo> lista = new ArrayList<>();
		lista.add(new Articulo("pantalon", 20));
		lista.add(new Articulo("camisa",30));
		lista.add(new Articulo("jersey",40));
		when(baseDatos.getArticulos()).thenReturn(lista);
		List<Articulo> listado = carrito.getArticulosBBDD();
		assertEquals(3, listado.size());
	}
	
	@Test
	public void testAplicarDescuento() {
		Articulo articulo = new Articulo("camiseta", 20.00);
		when(baseDatos.buscarArticuloPorId(any(Integer.class))).thenReturn(articulo);
		Double precio = carrito.aplicarDescuento(1, 10D);
		assertEquals(18D, precio);
		verify(baseDatos).buscarArticuloPorId(any(Integer.class));
	}
	
	@Test
	public void insertarArticulo() {

		Articulo articulo = new Articulo("calcetines", 10);
		carrito.addArticulo(articulo);
		
		assertNotNull(carrito.getArticulos().get(0));
		Integer id = baseDatos.insertarArticuloBBDD(articulo);

        assertEquals(id, 0);

        verify(baseDatos, atLeast(1)).insertarArticuloBBDD(articulo);

    }
	
	
}