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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import grupo1.hospital.app.classes.Internacao;
import grupo1.hospital.app.classes.Medico;
import grupo1.hospital.app.classes.Paciente;
import grupo1.hospital.app.classes.Pessoa;
import javafx.beans.property.ReadOnlyStringWrapper;
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

public class InternacaoController implements Initializable{
	@FXML
	private TableView<Internacao> tableViewInternacao;
	
	@FXML
	private TableColumn<Internacao, Integer> tableColumnId;
	@FXML
	private TableColumn<Internacao, String> tableColumnAcompanhante;
	@FXML
	private TableColumn<Internacao, String> tableColumnPaciente;
	@FXML
	private TableColumn<Internacao, LocalTime> tableColumnHora;
	@FXML
	private TableColumn<Internacao, LocalDate> tableColumnQuarto;
	
	@FXML
	private TextField textFieldQuarto;
	@FXML
	private TextField textFieldHora;
	@FXML
	private ComboBox<Paciente> comboBoxPaciente;
	@FXML
	private ComboBox<Pessoa> comboBoxAcompanhante;
	@FXML
	private TextField textFieldId;
	
	@FXML
	private Button buttonSalvar;
	@FXML
	private Button buttonCancelar;
	@FXML
	private Button buttonExcluir;
	
	private ObservableList<Paciente> olPaciente;
	private ObservableList<Internacao> olInternacao;
	private ObservableList<Pessoa> olPessoa;
	private Integer idInternacaoSelecionado;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		insertTableInternacao();
		insertPessoasComboBox();
		insertPacientesComboBox();
        tableViewInternacao.getSelectionModel().selectedItemProperty().addListener(
          (observable, oldValue, newValue) -> selecionarInternacao(newValue));
    }
	
	private void selecionarInternacao(Internacao internacao) {
    
    		idInternacaoSelecionado = internacao.getId();

    		textFieldQuarto.setText(internacao.getQuarto());
    		textFieldHora.setText(String.valueOf(internacao.getHorario()));
    		
    		Paciente p = new Paciente(
				internacao.getPaciente().getNome(),
				internacao.getPaciente().getCpf(),
				internacao.getPaciente().getRg(),
				internacao.getPaciente().getTelefone(),
				internacao.getPaciente().getDtNascimento(),
				internacao.getPaciente().getSexo(),
				internacao.getPaciente().getIdPessoa(),
				internacao.getPaciente().getGravidade()
        	);
            //p.setId(internacao.getPaciente().getId());
            	
        	Pessoa p2 = new Pessoa(
    			internacao.getAcompanhante().getNome(),
    			internacao.getAcompanhante().getCpf(),
    			internacao.getAcompanhante().getRg(),
    			internacao.getAcompanhante().getTelefone(),
    			internacao.getAcompanhante().getDtNascimento(),
    			internacao.getAcompanhante().getSexo(),
    			0
        	);
    		
    		comboBoxPaciente.setValue(p);
    		comboBoxAcompanhante.setValue(p2);
  
            textFieldId.setText(String.valueOf(internacao.getId()));
		

    }
	
	public void insertPessoasComboBox() {
		try {
			olPessoa = FXCollections.observableArrayList(listarTodasPessoas());
		} catch (IOException e) {
			System.out.println("Erro no combobox pessoa");
			System.out.println(e);
		}
		comboBoxAcompanhante.setItems(olPessoa);
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
	
	public void insertPacientesComboBox() {
		try {
			olPaciente = FXCollections.observableArrayList(listarTodosPacientes());
		} catch (IOException e) {
			System.out.println("Erro no combobox pessoa");
			System.out.println(e);
		}
		comboBoxPaciente.setItems(olPaciente);
	}
	
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

	public void insertTableInternacao() {
		try {
			List<Internacao> listaM = listarTodasInternacoes();

			tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
			tableColumnAcompanhante.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getAcompanhante().getNome()));
			tableColumnPaciente.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getPaciente().getNome()));
	        tableColumnHora.setCellValueFactory(new PropertyValueFactory<>("horario"));
	        tableColumnQuarto.setCellValueFactory(new PropertyValueFactory<>("quarto"));
	 

	        olInternacao = FXCollections.observableArrayList(listaM);
	        tableViewInternacao.setItems(olInternacao);
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Erro grave");
		}
	}
	
	@FXML
    private static List<Internacao> listarTodasInternacoes() throws IOException { 
        URL url = new URL("http://localhost:8080/api/internacao/todos");
        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpUrlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        
        String line;
        String data = "";
        List<Internacao> internacaoList = new ArrayList<Internacao>();
        while ((line=bufferedReader.readLine() )!=null){
        	data = data+line;
        }
        if (!data.isEmpty()){
            JSONArray internacoes = new JSONArray(data);
            internacaoList.clear();
            for (int i =0 ; i< internacoes.length(); i++){
            	JSONObject obj = internacoes.getJSONObject(i);
            	
            	JSONObject objPaciente = obj.getJSONObject("paciente");
            	String[] dtNasc = objPaciente.getString("dtNascimento").split("-");
            	Paciente p = new Paciente(
        			objPaciente.getString("nome"),
        			objPaciente.getString("cpf"),
        			objPaciente.getInt("rg"),
        			objPaciente.getString("telefone"),
        			LocalDate.of(Integer.valueOf(dtNasc[0]), Integer.valueOf(dtNasc[1]), Integer.valueOf(dtNasc[2])),
        			objPaciente.getString("sexo"),
        			objPaciente.getInt("idPessoa"),
        			objPaciente.getInt("gravidade")
            	);
            	p.setId(objPaciente.getInt("id"));
            	
            	JSONObject objPessoa = obj.getJSONObject("acompanhante");
            	String[] dtNascp = objPessoa.getString("dtNascimento").split("-");
            	Pessoa p2 = new Pessoa(
            		objPessoa.getString("nome"),
            		objPessoa.getString("cpf"),
            		objPessoa.getInt("rg"),
            		objPessoa.getString("telefone"),
        			LocalDate.of(Integer.valueOf(dtNascp[0]), Integer.valueOf(dtNascp[1]), Integer.valueOf(dtNascp[2])),
        			objPessoa.getString("sexo"),
        			objPessoa.getInt("idPessoa")
            	);
            	p.setId(objPessoa.getInt("idPessoa"));
            	
            	String[] horario = obj.getString("horario").split(":");
            	Internacao m = new Internacao(
            		LocalTime.of(Integer.valueOf(horario[0]), Integer.valueOf(horario[1])),
            		obj.getString("quarto"),
            		p, p2
            	);
            	m.setId(obj.getInt("id"));
            	
            	internacaoList.add(m);
            }
        }
        return internacaoList;
    }

	public void limpar() {
		textFieldQuarto.setText("");
		textFieldHora.setText("");
    	textFieldId.setText("0");
    	comboBoxAcompanhante.setValue(null);
    	comboBoxPaciente.setValue(null);
    }

    @FXML
    public void handlerCancelar(ActionEvent event) {
    	limpar();
    }
	
	@FXML
    public void handlerExcluir(ActionEvent event) {
    	if (idInternacaoSelecionado != null && idInternacaoSelecionado != 0){
            try {
                URL url = new URL("http://localhost:8080/api/internacao/excluir?id="+idInternacaoSelecionado);
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
            insertTableInternacao();
        }
    }

    @FXML
    public void handlerSalvar(ActionEvent event) {
    	try {
    		if(tableColumnQuarto.getText().isEmpty()) {
    			throw new Exception("Especialidade vazia");
    		}
    		Integer id = Integer.valueOf(textFieldId.getText());
            String quarto = textFieldQuarto.getText();
            String[] horarioArr = textFieldHora.getText().split(":");
            LocalTime horario = LocalTime.of(Integer.valueOf(horarioArr[0]), Integer.valueOf(horarioArr[1]));
            Pessoa acompanhante = comboBoxAcompanhante.getValue();
            Paciente paciente = comboBoxPaciente.getValue();

            Internacao internacao = new Internacao(
            	horario, quarto, paciente, acompanhante
            );
            internacao.setId(id);
            
            String method = "";
            String urll = "";
            if (internacao.getId() != 0) {
            	method = "PUT";
            	urll = "http://localhost:8080/api/internacao/atualizar";
            }else {
            	method = "POST";
            	urll = "http://localhost:8080/api/internacao";
            }
            
    		try {
    			JSONObject mainData = new JSONObject();
    			mainData.put("idPessoaAcompanhante", internacao.getAcompanhante().getIdPessoa());
    			mainData.put("idPaciente", internacao.getId());
    			mainData.put("horario", internacao.getHorario());
    			mainData.put("quarto", internacao.getQuarto());
    			mainData.put("id", internacao.getId());

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
            insertTableInternacao();
		} catch (Exception e) {
			System.out.println("Não cadastrado.");
		}
    }
	
}
