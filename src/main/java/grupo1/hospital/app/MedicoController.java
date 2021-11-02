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

public class MedicoController implements Initializable {
	@FXML
	private TableView<Medico> tableViewMedico;
	
	@FXML
	private TableColumn<Medico, Integer> tableColumnId;
	@FXML
	private TableColumn<Medico, Integer> tableColumnIdPessoa;
	@FXML
	private TableColumn<Medico, String> tableColumnNome;
	@FXML
	private TableColumn<Medico, String> tableColumnCpf;
	@FXML
	private TableColumn<Medico, LocalDate> tableColumnDtNascimento;
	@FXML
	private TableColumn<Medico, String> tableColumnEspecialidade;
	@FXML
	private TableColumn<Medico, String> tableColumnTelefone;
	
	@FXML
	private TextField textFieldEspecialidade;
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
	
	private ObservableList<Medico> olMedico;
	private ObservableList<Pessoa> olPessoa;
	private Integer idMedicoSelecionado;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		insertTableMedico();
		insertPessoasComboBox();
        tableViewMedico.getSelectionModel().selectedItemProperty().addListener(
          (observable, oldValue, newValue) -> selecionarMedico(newValue));
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
	
	public void insertTableMedico() {
		try {
			List<Medico> listaM = listarTodosMedicos();

			tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
			tableColumnIdPessoa.setCellValueFactory(new PropertyValueFactory<>("idPessoa"));
	        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
	        tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
	        tableColumnEspecialidade.setCellValueFactory(new PropertyValueFactory<>("especialidade"));
	        tableColumnDtNascimento.setCellValueFactory(new PropertyValueFactory<>("dtNascimento"));
	        tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
	 

	        olMedico = FXCollections.observableArrayList(listaM);
	        tableViewMedico.setItems(olMedico);
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Erro grave");
		}
	}
	
    private void selecionarMedico(Medico medico) {
    	try {
    		idMedicoSelecionado = medico.getId();

            textFieldEspecialidade.setText(medico.getEspecialidade());
            Pessoa p = new Pessoa(
            	medico.getNome(), medico.getCpf(), medico.getRg(), medico.getTelefone(),
            	medico.getDtNascimento(), medico.getSexo(), medico.getIdPessoa()
            );
            comboBoxPessoa.setValue(p);
            textFieldId.setText(String.valueOf(medico.getId()));
		} catch (Exception e) {
			System.out.println("Ninguém selecionado.");
		}

    }
	
    @FXML
    private static List<Medico> listarTodosMedicos() throws IOException { 
        URL url = new URL("http://localhost:8080/api/medico/todos");
        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpUrlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        
        String line;
        String data = "";
        List<Medico> medicoList = new ArrayList<Medico>();
        while ((line=bufferedReader.readLine() )!=null){
        	data = data+line;
        }
        if (!data.isEmpty()){
            JSONArray medicos = new JSONArray(data);
            medicoList.clear();
            for (int i =0 ; i< medicos.length(); i++){
            	JSONObject obj = medicos.getJSONObject(i);
            	
            	String[] dtNasc = obj.getString("dtNascimento").split("-");
            	Medico m = new Medico(
        			obj.getString("nome"),
        			obj.getString("cpf"),
        			obj.getInt("rg"),
        			obj.getString("telefone"),
        			LocalDate.of(Integer.valueOf(dtNasc[0]), Integer.valueOf(dtNasc[1]), Integer.valueOf(dtNasc[2])),
        			obj.getString("sexo"),
        			obj.getInt("idPessoa"),
        			obj.getString("especialidade")
            	);
            	m.setId(obj.getInt("id"));
            	medicoList.add(m);
            }
        }
        return medicoList;
    }
    
    public void limpar() {
    	textFieldEspecialidade.setText(null);
    	comboBoxPessoa.setValue(null);
    	textFieldId.setText("0");
    	idMedicoSelecionado = 0;
    }
    
    @FXML
    public void handlerSalvar(ActionEvent event) {
    	try {
    		if(textFieldEspecialidade.getText().isEmpty()) {
    			throw new Exception("Especialidade vazia");
    		}
    		Integer id = Integer.valueOf(textFieldId.getText());
            String especialidade = textFieldEspecialidade.getText();
            Pessoa pessoaSelecionada = comboBoxPessoa.getValue();
            if(pessoaSelecionada == null){
            	throw new Exception("Não selecionou nenhuma pessoa.");
            }
            Medico medico = new Medico(
            	pessoaSelecionada.getNome(), pessoaSelecionada.getCpf(), pessoaSelecionada.getRg(), pessoaSelecionada.getTelefone(),
            	pessoaSelecionada.getDtNascimento(), pessoaSelecionada.getSexo(), pessoaSelecionada.getIdPessoa(), especialidade
            );
            medico.setId(id);
            System.out.println("--Médico: "+medico.getId()+", "+medico.getIdPessoa());
            
            String method = "";
            String urll = "";
            if (medico.getId() != 0) {
            	method = "PUT";
            	urll = "http://localhost:8080/api/medico/atualizar";
            }else {
            	method = "POST";
            	urll = "http://localhost:8080/api/medico";
            }
            
    		try {
    			JSONObject mainData = new JSONObject();
    			mainData.put("nome", medico.getNome());
    			mainData.put("cpf", medico.getCpf());
    			mainData.put("telefone", medico.getTelefone());
    			mainData.put("rg", medico.getRg());
    			mainData.put("dtNascimento", medico.getDtNascimento());
    			mainData.put("sexo", medico.getSexo());
    			mainData.put("idPessoa", medico.getIdPessoa());
    			mainData.put("especialidade", medico.getEspecialidade());
    			mainData.put("id", medico.getId());

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
            insertTableMedico();
		} catch (Exception e) {
			System.out.println("Não cadastrado.");
		}
    }
    
    @FXML
    public void handlerCancelar(ActionEvent event) {
    	limpar();
    }
    
    @FXML
    public void handlerExcluir(ActionEvent event) {
    	if (idMedicoSelecionado != null && idMedicoSelecionado != 0){
            try {
                URL url = new URL("http://localhost:8080/api/medico/excluir?id="+idMedicoSelecionado);
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
            insertTableMedico();
        }
    }
	
}
