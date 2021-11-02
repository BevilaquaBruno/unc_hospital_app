package grupo1.hospital.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import grupo1.hospital.app.classes.Pessoa;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class PrincipalController {
	
	@FXML
	private Menu menuPessoa;
	@FXML
	private Menu menuAcoes;
	
	@FXML
	private MenuItem menuItemPessoa;
	@FXML
	private MenuItem menuItemMedico;
	@FXML
	private MenuItem menuItemPaciente;
	@FXML
	private MenuItem menuItemEnfermeiro;
	@FXML
	private MenuItem menuItemAgenda;
	@FXML
	private MenuItem menuItemInternacao;
	@FXML
	private MenuItem menuItemServico;
	@FXML
	private AnchorPane anchorPanePrincipal;
    
    @FXML
    private void abrirCadastroPessoa() throws IOException {
    	AnchorPane a = FXMLLoader.load(getClass().getResource("Pessoa.fxml"));
    	anchorPanePrincipal.getChildren().setAll(a);
    }
    
    @FXML
    private void abrirCadastroMedico() throws IOException {
    	AnchorPane a = FXMLLoader.load(getClass().getResource("Medico.fxml"));
    	anchorPanePrincipal.getChildren().setAll(a);
    }
    
    @FXML
    private void abrirCadastroPaciente() {
    	System.out.println("abrirCadastroPaciente");
    }
    
    @FXML
    private void abrirCadastroEnfermeiro() {
    	System.out.println("abrirCadastroEnfermeiro");
    }
    
    @FXML
    private void abrirCadastroAgenda() {
    	System.out.println("abrirCadastroAgenda");
    }
    @FXML
    private void abrirCadastroInternacao() {
    	System.out.println("abrirCadastroInternacao");
    }
    
    @FXML
    private void abrirCadastroServico() {
    	System.out.println("abrirCadastroServico");
    }
}
