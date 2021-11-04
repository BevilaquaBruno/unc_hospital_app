package grupo1.hospital.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import grupo1.hospital.app.classes.Enfermeiro;
import grupo1.hospital.app.classes.Pessoa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EnfermeiroController implements Initializable {
	@FXML
	private TableView<Enfermeiro> tableViewEnfermeiro;
	
	@FXML
	private TableColumn<Enfermeiro, Integer> tableColumnId;
	@FXML
	private TableColumn<Enfermeiro, Integer> tableColumnIdPessoa;
	@FXML
	private TableColumn<Enfermeiro, String> tableColumnNome;
	@FXML
	private TableColumn<Enfermeiro, String> tableColumnCpf;
	@FXML
	private TableColumn<Enfermeiro, LocalDate> tableColumnDtNascimento;
	@FXML
	private TableColumn<Enfermeiro, LocalDate> tableColumnDtAdmissao;
	@FXML
	private TableColumn<Enfermeiro, String> tableColumnTelefone;
	
	@FXML
	private DatePicker datePickerDtAdmissao;
	@FXML
	private ComboBox<Pessoa> comboBoxPessoa;
	@FXML
	private TextField textFieldId;
	
	@FXML
	private Button buttonSalvar;
	@FXML
	private Button buttonCancelar;
	@FXML
	private Button buttonExcluir;
	
	private ObservableList<Enfermeiro> olEnfermeiro;
	private ObservableList<Pessoa> olPessoa;
	private Integer idEnfermeiroSelecionado;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		insertTableEnfermeiro();
		insertPessoasComboBox();
        tableViewEnfermeiro.getSelectionModel().selectedItemProperty().addListener(
          (observable, oldValue, newValue) -> selecionarEnfermeiro(newValue));
    }
	
	public void insertPessoasComboBox() {
		try {
			olPessoa = FXCollections.observableArrayList(listarTodasPessoas());
		} catch (IOException e) {
			System.out.println("Erro no combobox pessoa");
			System.out.println(e);
		}
		comboBoxPessoa.setItems(olPessoa);
	}
	
	private static List<Pessoa> listarTodasPessoas() throws IOException { 
        URL url = new URL("http://localhost:8080/api/pessoa/todos");
        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpUrlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        
        String line;
        String data = "";
        List<Pessoa> pessoaList = new ArrayList<Pessoa>();
        while ((line=bufferedReader.readLine() )!=null){
        	data = data+line;
        }
        if (!data.isEmpty()){
            JSONArray pessoas = new JSONArray(data);
            pessoaList.clear();
            for (int i =0 ; i< pessoas.length(); i++){
            	JSONObject obj = pessoas.getJSONObject(i);
            	
            	String[] dtNasc = obj.getString("dtNascimento").split("-");
            	Pessoa p = new Pessoa(
        			obj.getString("nome"),
        			obj.getString("cpf"),
        			obj.getInt("rg"),
        			obj.getString("telefone"),
        			LocalDate.of(Integer.valueOf(dtNasc[0]), Integer.valueOf(dtNasc[1]), Integer.valueOf(dtNasc[2])),
        			obj.getString("sexo"),
        			obj.getInt("idPessoa")
            	);

            	pessoaList.add(p);
            }
        }
        return pessoaList;
    }
	
	public void insertTableEnfermeiro() {
		try {
			List<Enfermeiro> listaP = listarTodosEnfermeiros();

			tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
			tableColumnIdPessoa.setCellValueFactory(new PropertyValueFactory<>("idPessoa"));
	        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
	        tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
	        tableColumnDtAdmissao.setCellValueFactory(new PropertyValueFactory<>("dtAdmissao"));
	        tableColumnDtNascimento.setCellValueFactory(new PropertyValueFactory<>("dtNascimento"));
	        tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
	 
	        olEnfermeiro = FXCollections.observableArrayList(listaP);
	        tableViewEnfermeiro.setItems(olEnfermeiro);
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Erro grave");
		}
	}
	
	@FXML
    private static List<Enfermeiro> listarTodosEnfermeiros() throws IOException { 
        URL url = new URL("http://localhost:8080/api/enfermeiro/todos");
        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpUrlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        
        String line;
        String data = "";
        List<Enfermeiro> enfermeiroList = new ArrayList<Enfermeiro>();
        while ((line=bufferedReader.readLine() )!=null){
        	data = data+line;
        }
        if (!data.isEmpty()){
            JSONArray enfermeiros = new JSONArray(data);
            enfermeiroList.clear();
            for (int i =0 ; i< enfermeiros.length(); i++){
            	JSONObject obj = enfermeiros.getJSONObject(i);
            	
            	String[] dtNasc = obj.getString("dtNascimento").split("-");
            	String[] dtAdmissao = obj.getString("dtAdmissao").split("-");
            	Enfermeiro e = new Enfermeiro(
        			obj.getString("nome"),
        			obj.getString("cpf"),
        			obj.getInt("rg"),
        			obj.getString("telefone"),
        			LocalDate.of(Integer.valueOf(dtNasc[0]), Integer.valueOf(dtNasc[1]), Integer.valueOf(dtNasc[2])),
        			obj.getString("sexo"),
        			obj.getInt("idPessoa"),
        			LocalDate.of(Integer.valueOf(dtAdmissao[0]), Integer.valueOf(dtAdmissao[1]), Integer.valueOf(dtAdmissao[2]))
            	);
            	e.setId(obj.getInt("id"));
            	enfermeiroList.add(e);
            }
        }
        return enfermeiroList;
    }
	
    public void limpar() {
    	datePickerDtAdmissao.setValue(null);;
    	comboBoxPessoa.setValue(null);
    	textFieldId.setText("0");
    	idEnfermeiroSelecionado = 0;
    }
    
    @FXML
    public void handlerCancelar(ActionEvent event) {
    	limpar();
    }
    
    @FXML
    public void handlerSalvar(ActionEvent event) {
    	try {
    		Integer id = Integer.valueOf(textFieldId.getText());
    		LocalDate admissao = datePickerDtAdmissao.getValue();
            Pessoa pessoaSelecionada = comboBoxPessoa.getValue();
            if(pessoaSelecionada == null){
            	throw new Exception("Não selecionou nenhuma pessoa.");
            }
            Enfermeiro enfermeiro = new Enfermeiro(
            	pessoaSelecionada.getNome(), pessoaSelecionada.getCpf(), pessoaSelecionada.getRg(), pessoaSelecionada.getTelefone(),
            	pessoaSelecionada.getDtNascimento(), pessoaSelecionada.getSexo(), pessoaSelecionada.getIdPessoa(), admissao
            );
            enfermeiro.setId(id);
            System.out.println("--paciente: "+enfermeiro.getId()+", "+enfermeiro.getIdPessoa());
            
            String method = "";
            String urll = "";
            if (enfermeiro.getId() != 0) {
            	method = "PUT";
            	urll = "http://localhost:8080/api/enfermeiro/atualizar";
            }else {
            	method = "POST";
            	urll = "http://localhost:8080/api/enfermeiro";
            }
            
    		try {
    			System.out.println("enfermeiro: "+enfermeiro);
    			JSONObject mainData = new JSONObject();
    			mainData.put("nome", enfermeiro.getNome());
    			mainData.put("cpf", enfermeiro.getCpf());
    			mainData.put("telefone", enfermeiro.getTelefone());
    			mainData.put("rg", enfermeiro.getRg());
    			mainData.put("dtNascimento", enfermeiro.getDtNascimento());
    			mainData.put("sexo", enfermeiro.getSexo());
    			mainData.put("idPessoa", enfermeiro.getIdPessoa());
    			mainData.put("dtAdmissao", enfermeiro.getDtAdmissao());
    			mainData.put("id", enfermeiro.getId());

    			URL url = new URL(urll);
    			HttpURLConnection connection = (HttpURLConnection) url.openConnection();  //abre conexao

			    connection.setRequestMethod(method); //fala que quer um post
			    connection.setRequestProperty("Content-type", "application/json"); //fala o que vai mandar
			    connection.setDoOutput(true); //fala que voce vai enviar algo

			    PrintStream printStream = new PrintStream(connection.getOutputStream());
			    printStream.println(mainData); //seta o que voce vai enviar

			    connection.connect(); //envia para o servidor

			    InputStream inputStream = connection.getInputStream();
			    
			    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		        
		        String line;
		        String data = "";
		        while ((line=bufferedReader.readLine() )!=null){
		        	data = data+line;
		        }
		        
			    System.out.println("Atualizado: "+data);
    		}catch (MalformedURLException e) {
    			System.out.print("Erro grave.");
    			e.printStackTrace();
    		} catch (IOException e) {
    			System.out.print("Erro grave 2.");
    			e.printStackTrace();
    		}            
            
            limpar();
            insertTableEnfermeiro();
		} catch (Exception e) {
			System.out.println("Não cadastrado.");
		}
    }
    
    private void selecionarEnfermeiro(Enfermeiro enfermeiro) {
    	try {
    		idEnfermeiroSelecionado = enfermeiro.getId();

    		datePickerDtAdmissao.setValue(enfermeiro.getDtNascimento());
            Pessoa p = new Pessoa(
            		enfermeiro.getNome(), enfermeiro.getCpf(), enfermeiro.getRg(),enfermeiro.getTelefone(),
            		enfermeiro.getDtNascimento(), enfermeiro.getSexo(), enfermeiro.getIdPessoa()
            );
            comboBoxPessoa.setValue(p);
            textFieldId.setText(String.valueOf(enfermeiro.getId()));
		} catch (Exception e) {
			System.out.println("Ninguém selecionado.");
		}

    }
    
    @FXML
    public void handlerExcluir(ActionEvent event) {
    	if (idEnfermeiroSelecionado != null && idEnfermeiroSelecionado != 0){
            try {
                URL url = new URL("http://localhost:8080/api/enfermeiro/excluir?id="+idEnfermeiroSelecionado);
    			HttpURLConnection connection = (HttpURLConnection) url.openConnection();  //abre conexao

    		    connection.setRequestMethod("DELETE"); //fala que quer um delete

    		    connection.connect(); //envia para o servidor

    		    InputStream inputStream = connection.getInputStream();
    		    
    		    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    	        
    	        String line;
    	        String data = "";
    	        while ((line=bufferedReader.readLine() )!=null){
    	        	data = data+line;
    	        }
    	        
    		    System.out.println("Atualizado: "+data);
			} catch (Exception e) {
				System.out.println("Erro ao excluir");
			}
            limpar();
            insertTableEnfermeiro();
        }
    }
}
