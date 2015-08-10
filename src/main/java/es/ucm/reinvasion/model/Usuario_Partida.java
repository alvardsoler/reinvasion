package es.ucm.reinvasion.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name = "partidasUsuario", query = "select up from Usuario_Partida up where up.id.usuarioId = :idUser"),
		@NamedQuery(name = "isUserInPartida", query = "select up from Usuario_Partida up where up.id.usuarioId = :idUser and up.id.partidaId = :idPartida"),
		@NamedQuery(name = "numberPlayers", query = "select count(up.id.partidaId) from Usuario_Partida up where up.id.partidaId = :idPartida"),
		@NamedQuery(name = "delUserFromPartida", query = "delete from Usuario_Partida up where up.id.partidaId = :idPartida")
		})
public class Usuario_Partida {
	@EmbeddedId
	private UsuarioPartidaId id;

	// private Usuario usuario;
	// private Partida partida;

	public Usuario_Partida() {

	}

	public Usuario_Partida(UsuarioPartidaId id) {
		this.id = id;
	}

	public UsuarioPartidaId getId() {
		return id;
	}

	// @Id
	// @ManyToMany(targetEntity = Usuario.class, fetch = FetchType.LAZY)
	// @JoinColumn(name = "usuario_id")
	// public Usuario getUsuario() {
	// return usuario;
	// }
	//
	// public void setUsuario(Usuario usuario) {
	// this.usuario = usuario;
	// }
	//
	// @Id
	// @ManyToMany(targetEntity = Partida.class, fetch = FetchType.LAZY)
	// @JoinColumn(name = "partida_id")
	// public Partida getPartida() {
	// return partida;
	// }
	//
	// public void setPartida(Partida partida) {
	// this.partida = partida;
	// }

}
