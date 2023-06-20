package br.com.senac.aulaprojeto.controller;

import br.com.senac.aulaprojeto.model.Cliente;
import br.com.senac.aulaprojeto.model.Endereco;
import br.com.senac.aulaprojeto.services.ClienteService;
import br.com.senac.aulaprojeto.services.EnderecoService;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@FxmlView ("/main.fxml")
public class CadastroClienteController {
    @FXML
    private javafx.scene.control.TextField documento;
    @FXML
    private javafx.scene.control.TextField nome;
    @FXML
    private javafx.scene.control.TextField rg;
    @FXML
    private javafx.scene.control.TextField email;
    @FXML
    private javafx.scene.control.TextField telefone;
    @FXML
    private TableView<Cliente> tabelaCliente;
    @FXML
    private TableColumn <Cliente, Integer> id;
    @FXML
    private TableColumn <Cliente, String> coluDoc;
    @FXML
    private TableColumn <Cliente, String> coluNome;
    @FXML
    private TableColumn <Cliente, String> colurg;
    @FXML
    private TableColumn <Cliente, String> coluEmail;
    @FXML
    private TableColumn <Cliente, String> coluFone;
    @FXML
    private TableView<Endereco> tabelaEndereco;
    @FXML
    private javafx.scene.control.TextField id_cliente;
    @FXML
    private javafx.scene.control.TextField cep;
    @FXML
    private javafx.scene.control.TextField bairro;
    @FXML
    private javafx.scene.control.TextField numero;
    @FXML
    private javafx.scene.control.TextField cidade;
    @FXML
    private javafx.scene.control.TextField estado;
    @FXML
    private TableColumn <Endereco, String> coluCliEndere;
    @FXML
    private TableColumn <Endereco, String> coluCep;
    @FXML
    private TableColumn <Endereco, String> coluBairro;
    @FXML
    private TableColumn <Endereco, Integer> coluNumero;
    @FXML
    private TableColumn <Endereco, String> coluCidade;
    @FXML
    private TableColumn <Endereco, String> coluEstado;

    private Integer index= -1;
    private Integer indexx= -1;

    public void initialize (){
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        coluDoc.setCellValueFactory(new PropertyValueFactory<>("Documento"));
        coluNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        colurg.setCellValueFactory(new PropertyValueFactory<>("rg"));
        coluEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        coluFone.setCellValueFactory(new PropertyValueFactory<>("Telefone"));

        this.carregarCliente();

        coluCliEndere.setCellValueFactory(new PropertyValueFactory<>("id_cliente"));
        coluCep.setCellValueFactory(new PropertyValueFactory<>("cep"));
        coluBairro.setCellValueFactory(new PropertyValueFactory<>("Bairro"));
        coluNumero.setCellValueFactory(new PropertyValueFactory<>("Numero"));
        coluCidade.setCellValueFactory(new PropertyValueFactory<>("Cidade"));
        coluEstado.setCellValueFactory(new PropertyValueFactory<>("Estado"));

        this.carregarEndereco();

        tabelaCliente.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent evt) {
                if(evt.getClickCount() ==2){
                    Cliente cli = tabelaCliente.getSelectionModel().getSelectedItem();
                    documento.setText(cli.getDocumento());
                    nome.setText(cli.getNome());
                    rg.setText(String.valueOf(cli.getRg()));
                    email.setText(cli.getEmail());
                    telefone.setText(String.valueOf(cli.getTelefone()));

                    index = cli.getId();
                }
            }
        });
        tabelaEndereco.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent est) {
                if(est.getClickCount() ==2){
                    Endereco end = tabelaEndereco.getSelectionModel().getSelectedItem();
                    id_cliente.setText(String.valueOf(end.getId_cliente()));
                    cep.setText(String.valueOf(end.getCep()));
                    bairro.setText(end.getBairro());
                    numero.setText(String.valueOf(end.getNumero()));
                    cidade.setText(end.getCidade());
                    estado.setText(end.getEstado());

                    //indexx = end.getId();
                    indexx = end.getId_cliente();

                }
            }
        });
    }
    public void salvarCliente (){
        Cliente cli = new Cliente();
        cli.setDocumento(documento.getText());
        cli.setNome(nome.getText());
        cli.setRg(Integer.parseInt(rg.getText()));
        cli.setEmail(email.getText());
        cli.setTelefone(telefone.getText());

        if (!cli.getDocumento().matches("[0-9]*") && !cli.getTelefone().matches("[0-9]*")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alerta");
            alert.setHeaderText("Informe apenas numeros no Documento e no Telefone");
            alert.show();
        }else  if (index < 0){
            ClienteService.atualizarCliente(index,cli);
            index= 0;
            this.limparCampos();
        }else {
            if(ClienteService.buscarClienteByDocumento(cli.getDocumento())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alerta");
                alert.setHeaderText("Documento ja cadastrado");
                alert.show();
            }else {
                ClienteService.inserirClientes(cli);
                this.limparCampos();
            }
        }
        if (cli.getDocumento().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos não informado");
            alert.setHeaderText("Nome ou documento não informado!");
            alert.show();
        }else{
            ClienteService.inserirClientes(cli);
            this.limparCampos();
        }
        this.carregarCliente();
        //this.limparCampos();
    }

    public void excluirCliente(){
        if (index > -1){
            ClienteService.deletarCliente(index);
            this.carregarCliente();
            index= -1;
            this.limparCampos();
        }
    }

    public void limparCampos(){
        documento.setText("");
        nome.setText("");
        rg.setText("");
        email.setText("");
        telefone.setText("");
    }

    public void carregarCliente (){
        tabelaCliente.getItems().remove(0,tabelaCliente.getItems().size());
        List<Cliente> cliList = ClienteService.carregarClientes();

        tabelaCliente.getItems().addAll(cliList);
    }

    public void carregarEndereco(){
        tabelaEndereco.getItems().remove(0,tabelaEndereco.getItems().size());
        List<Endereco> endList = EnderecoService.carregarEnderecos();

        tabelaEndereco.getItems().addAll(endList);
    }

    public void salvarEndereco (){
        Endereco endere = new Endereco();
        endere.setId_cliente(Integer.parseInt(id_cliente.getText()));
        endere.setCep(cep.getText());
        endere.setBairro(bairro.getText());
        endere.setNumero(numero.getText());
        endere.setCidade(cidade.getText());
        endere.setEstado(estado.getText());

        if (!endere.getNumero().matches("[0-9]*")){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alerta");
            alert.setHeaderText("Informe apenas numeros no campo numero");
            alert.show();
        }else  if (indexx < 0){
            EnderecoService.atualizarEndereco(indexx,endere);
            indexx=0;
            this.limparCamposEndereco();
        }else {
            if(EnderecoService.buscarEnderecoByCep(endere.getCep())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alerta");
                alert.setHeaderText("Cep ja cadastrado");
                alert.show();
            }else {
                EnderecoService.inserirEndereco(endere);
                this.limparCamposEndereco();
            }
        }
        if (endere.getCep().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Campos não informado");
            alert.setHeaderText("CEP não informado");
            alert.show();
        }else{
            EnderecoService.inserirEndereco(endere);
            this.limparCamposEndereco();
        }
        this.carregarEndereco();
    }
    public void excluirEndereco(){
        if (indexx > -1){
            EnderecoService.deletarEndereco(indexx);
            this.carregarEndereco();
            indexx= -1;
            this.limparCamposEndereco();
        }

    }
    public void limparCamposEndereco(){
        cep.setText("");
        bairro.setText("");
        numero.setText("");
        cidade.setText("");
        estado.setText("");
        id_cliente.setText("");
    }

}
