package controller;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import dao.HuespedesDAO;
import factory.ConnectionFactory;
import model.Huespedes;

public class HuespedesController {
	
	private HuespedesDAO huespedesDao;
	
	public HuespedesController() {
		Connection con = new ConnectionFactory().recuperaConexion();
		this.huespedesDao = new HuespedesDAO(con);	
	}
	

	public void guardar(Huespedes huespedes){
		this.huespedesDao.guardar(huespedes);
	}

	public List<Huespedes> mostrar(){
	return this.huespedesDao.mostrar();
	}
	
	public List<Huespedes> buscar(String id){
		return this.huespedesDao.buscarId(id);
	}

	public void actualizarH(Integer id, String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad,
			String telefono, Integer idReserva) {
		this.huespedesDao.actualizarHuesped(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva);
	}
	
	public void eliminarH(Integer id) {
		this.huespedesDao.eliminarHuesped(id);
	}
}
