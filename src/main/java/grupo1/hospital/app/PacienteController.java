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

import grupo1.hospital.app.classes.Medico;
import grupo1.hospital.app.classes.Paciente;
import grupo1.hospital.app.classes.Pessoa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PacienteController implements Initializable{
	@FXML
	private TableView<Paciente> tableViewPaciente;
	
	@FXML
	private TableColumn<Paciente, Integer> tableColumnId;
	@FXML
	private TableColumn<Paciente, Integer> tableColumnIdPessoa;
	@FXML
	private TableColumn<Paciente, String> tableColumnNome;
	@FXML
	private TableColumn<Paciente, String> tableColumnCpf;
	@FXML
	private TableColumn<Paciente, LocalDate> tableColumnDtNascimento;
	@FXML
	private TableColumn<Paciente, Integer> tableColumnGravidade;
	@FXML
	private TableColumn<Paciente, String> tableColumnTelefone;
	
	@FXML
	private TextField textFieldGravidade;
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
	
	private ObservableList<Paciente> olPaciente;
	private ObservableList<Pessoa> olPessoa;
	private Integer idPacienteSelecionado;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		insertTablePaciente();
		insertPessoasComboBox();
        tableViewPaciente.getSelectionModel().selectedItemProperty().addListener(
          (observable, oldValue, newValue) -> selecionarPaciente(newValue));
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
	
	public void insertTablePaciente() {
		try {
			List<Paciente> listaP = listarTodosPacientes();

			tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
			tableColumnIdPessoa.setCellValueFactory(new PropertyValueFactory<>("idPessoa"));
	        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
	        tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
	        tableColumnGravidade.setCellValueFactory(new PropertyValueFactory<>("gravidade"));
	        tableColumnDtNascimento.setCellValueFactory(new PropertyValueFactory<>("dtNascimento"));
	        tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
	 
	        olPaciente = FXCollections.observableArrayList(listaP);
	        tableViewPaciente.setItems(olPaciente);
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Erro grave");
		}
	}
	
	@FXML
    private static List<Paciente> listarTodosPacientes() throws IOException { 
        URL url = new URL("http://localhost:8080/api/paciente/todos");
        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpUrlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        
        String line;
        String data = "";
        List<Paciente> pacienteList = new ArrayList<Paciente>();
        while ((line=bufferedReader.readLine() )!=null){
        	data = data+line;
        }
        if (!data.isEmpty()){
            JSONArray pacientes = new JSONArray(data);
            pacienteList.clear();
            for (int i =0 ; i< pacientes.length(); i++){
            	JSONObject obj = pacientes.getJSONObject(i);
            	
            	String[] dtNasc = obj.getString("dtNascimento").split("-");
            	Paciente p = new Paciente(
        			obj.getString("nome"),
        			obj.getString("cpf"),
        			obj.getInt("rg"),
        			obj.getString("telefone"),
        			LocalDate.of(Integer.valueOf(dtNasc[0]), Integer.valueOf(dtNasc[1]), Integer.valueOf(dtNasc[2])),
        			obj.getString("sexo"),
        			obj.getInt("idPessoa"),
        			obj.getInt("gravidade")
            	);
            	p.setId(obj.getInt("id"));
            	pacienteList.add(p);
            }
        }
        return pacienteList;
    }
	
    public void limpar() {
    	textFieldGravidade.setText(null);
    	comboBoxPessoa.setValue(null);
    	textFieldId.setText("0");
    	idPacienteSelecionado = 0;
    }
    
    @FXML
    public void handlerCancelar(ActionEvent event) {
    	limpar();
    }
    
    @FXML
    public void handlerSalvar(ActionEvent event) {
    	try {
    		if(textFieldGravidade.getText().isEmpty()) {
    			throw new Exception("Gravidade vazia");
    		}
    		Integer id = Integer.valueOf(textFieldId.getText());
            Integer gravidade = Integer.valueOf(textFieldGravidade.getText());
            Pessoa pessoaSelecionada = comboBoxPessoa.getValue();
            if(pessoaSelecionada == null){
            	throw new Exception("Não selecionou nenhuma pessoa.");
            }
            Paciente paciente = new Paciente(
            	pessoaSelecionada.getNome(), pessoaSelecionada.getCpf(), pessoaSelecionada.getRg(), pessoaSelecionada.getTelefone(),
            	pessoaSelecionada.getDtNascimento(), pessoaSelecionada.getSexo(), pessoaSelecionada.getIdPessoa(), gravidade
            );
            paciente.setId(id);
            System.out.println("--paciente: "+paciente.getId()+", "+paciente.getIdPessoa());
            
            String method = "";
            String urll = "";
            if (paciente.getId() != 0) {
            	method = "PUT";
            	urll = "http://localhost:8080/api/paciente/atualizar";
            }else {
            	method = "POST";
            	urll = "http://localhost:8080/api/paciente";
            }
            
    		try {
    			System.out.println("paciente: "+paciente);
    			JSONObject mainData = new JSONObject();
    			mainData.put("nome", paciente.getNome());
    			mainData.put("cpf", paciente.getCpf());
    			mainData.put("telefone", paciente.getTelefone());
    			mainData.put("rg", paciente.getRg());
    			mainData.put("dtNascimento", paciente.getDtNascimento());
    			mainData.put("sexo", paciente.getSexo());
    			mainData.put("idPessoa", paciente.getIdPessoa());
    			mainData.put("gravidade", paciente.getGravidade());
    			mainData.put("id", paciente.getId());

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
            insertTablePaciente();
		} catch (Exception e) {
			System.out.println("Não cadastrado.");
		}
    }
    
    private void selecionarPaciente(Paciente paciente) {
    	try {
    		idPacienteSelecionado = paciente.getId();

            textFieldGravidade.setText(String.valueOf(paciente.getGravidade()));
            Pessoa p = new Pessoa(
            		paciente.getNome(), paciente.getCpf(), paciente.getRg(), paciente.getTelefone(),
            		paciente.getDtNascimento(), paciente.getSexo(), paciente.getIdPessoa()
            );
            comboBoxPessoa.setValue(p);
            textFieldId.setText(String.valueOf(paciente.getId()));
		} catch (Exception e) {
			System.out.println("Ninguém selecionado.");
		}

    }
    
    @FXML
    public void handlerExcluir(ActionEvent event) {
    	if (idPacienteSelecionado != null && idPacienteSelecionado != 0){
            try {
                URL url = new URL("http://localhost:8080/api/paciente/excluir?id="+idPacienteSelecionado);
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
            insertTablePaciente();
        }
    }
	
}
