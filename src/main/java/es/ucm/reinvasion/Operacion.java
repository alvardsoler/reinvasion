package es.ucm.reinvasion;

public class Operacion {

	private String operacion;
	private int op1;
	private int op2;
	private int op3;
	private String idPartida;

	public int getOp3() {
		return op3;
	}

	public void setOp3(int op3) {
		this.op3 = op3;
	}

	public Operacion() {

	}

	public Operacion(String operacion, int op1, int op2, String idPartida) {
		this.operacion = operacion;
		this.op1 = op1;
		this.op2 = op2;
		this.idPartida = idPartida;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public int getOp1() {
		return op1;
	}

	public void setOp1(int op1) {
		this.op1 = op1;
	}

	public int getOp2() {
		return op2;
	}

	public void setOp2(int op2) {
		this.op2 = op2;
	}

	public String toString() {
		return operacion + " " + op1 + " " + op2;
	}

	public String getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(String idPartida) {
		this.idPartida = idPartida;
	}
}
