package br.com.senac.aulaprojeto.model;

public class Cliente {
    private Integer id;
    private String  documento;
    private String nome;
    private Integer rg;
    private String email;
    private String telefone;

    public Cliente() {
    }

    public Cliente(int id, String documento, String nome, Integer rg, String email, String telefone) {
        this.id = id;
        this.documento = documento;
        this.nome = nome;
        this.rg = rg;
        this.email = email;
        this.telefone = telefone;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Integer getRg() {
        return rg;
    }

    public void setRg(Integer rg) {
        this.rg = rg;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}


