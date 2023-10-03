package controller;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import dao.ReservaDAO;
import factory.ConnectionFactory;
import model.Reserva;

public class ReservaController {
	
	private ReservaDAO reservaDao;
	
	public ReservaController() {
		
		Connection con = new ConnectionFactory().recuperaConexion();
		this.reservaDao = new ReservaDAO(con);  
	}
	
	public void guardar(Reserva reserva) {
		this.reservaDao.guardar(reserva);
	}
	
	public List<Reserva> mostrar(){
		return this.reservaDao.mostrar();
	}

	public List<Reserva> buscar(String id) {
		
		return this.reservaDao.buscarId(id);
	}
	
	public int actualizarReservas(LocalDate fechaE, LocalDate fechaS, String valor, String formaPago, Integer id) {
		return this.reservaDao.actualizarReserva(fechaE, fechaS, valor, formaPago, id);
	}
	
	public int eliminarReservas(Integer id){
		
		return this.reservaDao.eliminarReserva(id);
		
	}
}
