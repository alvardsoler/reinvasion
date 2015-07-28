package es.ucm.reinvasion.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class UsuarioPartidaId implements Serializable {

	private long usuarioId;
	private long partidaId;

	public UsuarioPartidaId() {

	}

	public UsuarioPartidaId(Long partidaId, long userId) {
		this.partidaId = partidaId;
		this.usuarioId = userId;
	}

	public boolean equals(Object o) {
		UsuarioPartidaId aux = (UsuarioPartidaId) o;
		return (aux.partidaId == this.partidaId && aux.usuarioId == this.usuarioId);
	}

	public int hashCode() {
		int hash = 1;
		hash = (int) (hash * 35 + usuarioId);
		hash = (int) (hash * 3 + partidaId);
		return hash;
	}

	public long getIdPartida() {
		return partidaId;
	}

	public long getIdUsuario() {
		return usuarioId;
	}
}
