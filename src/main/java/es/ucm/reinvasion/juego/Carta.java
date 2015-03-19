package es.ucm.reinvasion.juego;

public enum Carta {
	INFANTERIA(5), CABALLERIA(10), ARTILLERIA(15);

	public int unidades;

	Carta(int valor) {
		this.unidades = valor;
	}

}
