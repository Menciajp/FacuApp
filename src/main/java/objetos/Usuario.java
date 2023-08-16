package objetos;


import jakarta.persistence.*;

@Entity
public class Usuario extends Entidad{

    @Column(unique = true)
    private String username;
    private String password;

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public Usuario(int idUsuario, String username, String password) {
        super.setId(idUsuario);
        this.username = username;
        this.password = password;
    }
    public Usuario(){}

    //getters y setters

    public int getIdUsuario() {
        return super.getId();
    }

    public void setIdUsuario(int idUsuario) {
        super.setId(idUsuario);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
