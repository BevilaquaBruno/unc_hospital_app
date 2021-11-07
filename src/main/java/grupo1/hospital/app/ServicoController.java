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
import grupo1.hospital.app.classes.Medico;
import grupo1.hospital.app.classes.Paciente;
import grupo1.hospital.app.classes.Servico;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ServicoController implements Initializable{
	@FXML
	private TableView<Servico> tableViewServico;
	
	@FXML
	private TableColumn<Servico, Integer> tableColumnId;
	@FXML
	private TableColumn<Servico, String> tableColumnMedico;
	@FXML
	private TableColumn<Servico, String> tableColumnPaciente;
	@FXML
	private TableColumn<Servico, String> tableColumnEnfermeiro;
	
	@FXML
	private ComboBox<Paciente> comboBoxPaciente;
	@FXML
	private ComboBox<Medico> comboBoxMedico;
	@FXML
	private ComboBox<Enfermeiro> comboBoxEnfermeiro;
	@FXML
	private TextArea textFieldDescricao;
	@FXML
	private TextField textFieldId;
	
	@FXML
	private Button buttonSalvar;
	@FXML
	private Button buttonCancelar;
	@FXML
	private Button buttonExcluir;
	
	private ObservableList<Paciente> olPaciente;
	private ObservableList<Medico> olMedico;
	private ObservableList<Enfermeiro> olEnfermeiro;
	private ObservableList<Servico> olServico;
	private Integer idServicoSelecionado;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		insertTableServico();
		insertMedicosComboBox();
		insertEnfermeirosComboBox();
		insertPacientesComboBox();
		tableViewServico.getSelectionModel().selectedItemProperty().addListener(
          (observable, oldValue, newValue) -> selecionarServico(newValue));
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
	
	public void insertMedicosComboBox() {
		try {
			olMedico = FXCollections.observableArrayList(listarTodosMedicos());
		} catch (IOException e) {
			System.out.println("Erro no combobox pessoa");
			System.out.println(e);
		}
		comboBoxMedico.setItems(olMedico);
	}
	
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
            	Medico p = new Medico(
        			obj.getString("nome"),
        			obj.getString("cpf"),
        			obj.getInt("rg"),
        			obj.getString("telefone"),
        			LocalDate.of(Integer.valueOf(dtNasc[0]), Integer.valueOf(dtNasc[1]), Integer.valueOf(dtNasc[2])),
        			obj.getString("sexo"),
        			obj.getInt("idPessoa"),
        			obj.getString("especialidade")
            	);
            	
            	p.setId(obj.getInt("id"));

            	medicoList.add(p);
            }
        }
        return medicoList;
    }
	
	public void insertEnfermeirosComboBox() {
		try {
			olEnfermeiro = FXCollections.observableArrayList(listarTodosEnfermeiros());
		} catch (IOException e) {
			System.out.println("Erro no combobox pessoa");
			System.out.println(e);
		}
		comboBoxEnfermeiro.setItems(olEnfermeiro);
	}
	
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
            	String[] dtAdm = obj.getString("dtAdmissao").split("-");
            	Enfermeiro p = new Enfermeiro(
        			obj.getString("nome"),
        			obj.getString("cpf"),
        			obj.getInt("rg"),
        			obj.getString("telefone"),
        			LocalDate.of(Integer.valueOf(dtNasc[0]), Integer.valueOf(dtNasc[1]), Integer.valueOf(dtNasc[2])),
        			obj.getString("sexo"),
        			obj.getInt("idPessoa"),
        			LocalDate.of(Integer.valueOf(dtAdm[0]), Integer.valueOf(dtAdm[1]), Integer.valueOf(dtAdm[2]))
            	);
            	
            	p.setId(obj.getInt("id"));

            	enfermeiroList.add(p);
            }
        }
        return enfermeiroList;
    }
	
	public void insertTableServico() {
		try {
			List<Servico> listaM = listarTodosServicos();

			tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
			tableColumnMedico.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getMedico().getNome()));
			tableColumnPaciente.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getPaciente().getNome()));
			tableColumnEnfermeiro.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getEnfermeiro().getNome()));
	 

	        olServico = FXCollections.observableArrayList(listaM);
	        tableViewServico.setItems(olServico);
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Erro grave");
		}
	}
	
	@FXML
    private static List<Servico> listarTodosServicos() throws IOException { 
        URL url = new URL("http://localhost:8080/api/servico/todos");
        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpUrlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        
        String line;
        String data = "";
        List<Servico> servicoList = new ArrayList<Servico>();
        while ((line=bufferedReader.readLine() )!=null){
        	data = data+line;
        }
        if (!data.isEmpty()){
            JSONArray internacoes = new JSONArray(data);
            servicoList.clear();
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
            	
            	JSONObject objEnfermeiro = obj.getJSONObject("enfermeiro");
            	String[] dtNascEnf = objEnfermeiro.getString("dtNascimento").split("-");
            	String[] dtAdmissaoArr = objEnfermeiro.getString("dtAdmissao").split("-");
            	Enfermeiro p2 = new Enfermeiro(
        			objEnfermeiro.getString("nome"),
        			objEnfermeiro.getString("cpf"),
        			objEnfermeiro.getInt("rg"),
        			objEnfermeiro.getString("telefone"),
        			LocalDate.of(Integer.valueOf(dtNascEnf[0]), Integer.valueOf(dtNascEnf[1]), Integer.valueOf(dtNascEnf[2])),
        			objEnfermeiro.getString("sexo"),
        			objEnfermeiro.getInt("idPessoa"),
        			LocalDate.of(Integer.valueOf(dtAdmissaoArr[0]), Integer.valueOf(dtAdmissaoArr[1]), Integer.valueOf(dtAdmissaoArr[2]))
            	);
            	p2.setId(objPaciente.getInt("id"));
            	
            	JSONObject objMedico = obj.getJSONObject("medico");
            	String[] dtNascMed = objMedico.getString("dtNascimento").split("-");
            	Medico p3 = new Medico(
        			objMedico.getString("nome"),
        			objMedico.getString("cpf"),
        			objMedico.getInt("rg"),
        			objMedico.getString("telefone"),
        			LocalDate.of(Integer.valueOf(dtNascMed[0]), Integer.valueOf(dtNascMed[1]), Integer.valueOf(dtNascMed[2])),
        			objMedico.getString("sexo"),
        			objMedico.getInt("idPessoa"),
        			objMedico.getString("especialidade")
            	);
            	p2.setId(objPaciente.getInt("id"));
            	
            	String descricao = obj.getString("descricao");
            	Servico m = new Servico(
            		descricao, p3, p, p2
            	);
            	m.setId(obj.getInt("id"));
            	
            	servicoList.add(m);
            }
        }
        return servicoList;
    }

	public void limpar() {
		textFieldDescricao.setText("");
    	comboBoxPaciente.setValue(null);
    	comboBoxEnfermeiro.setValue(null);
    	comboBoxMedico.setValue(null);
    	textFieldId.setText("0");
    }
	
    @FXML
    public void handlerCancelar(ActionEvent event) {
    	limpar();
    }
    
    @FXML
    public void handlerExcluir(ActionEvent event) {
    	if (idServicoSelecionado != null && idServicoSelecionado != 0){
            try {
                URL url = new URL("http://localhost:8080/api/servico/excluir?id="+idServicoSelecionado);
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
            insertTableServico();
        }
    }

    @FXML
    public void handlerSalvar(ActionEvent event) {
    	try {
    		Integer id = Integer.valueOf(textFieldId.getText());
            String descricao = textFieldDescricao.getText();
            Enfermeiro enfermeiro = comboBoxEnfermeiro.getValue();
            Paciente paciente = comboBoxPaciente.getValue();
            Medico medico = comboBoxMedico.getValue();

            Servico servico = new Servico(
            	descricao, medico, paciente, enfermeiro
            );
            servico.setId(id);
            
            String method = "";
            String urll = "";
            if (servico.getId() != 0) {
            	method = "PUT";
            	urll = "http://localhost:8080/api/servico/atualizar";
            }else {
            	method = "POST";
            	urll = "http://localhost:8080/api/servico";
            }
            
    		try {
    			JSONObject mainData = new JSONObject();
    			mainData.put("idEnfermeiro", servico.getEnfermeiro().getId());
    			mainData.put("idPaciente", servico.getPaciente().getId());
    			mainData.put("idMedico", servico.getMedico().getId());
    			mainData.put("descricao", servico.getDescricao());

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
            insertTableServico();
		} catch (Exception e) {
			System.out.println("Não cadastrado.");
		}
    }

    private void selecionarServico(Servico servico) {
    	
		idServicoSelecionado = servico.getId();

		textFieldDescricao.setText(servico.getDescricao());
		
        Paciente paciente = new Paciente(
        		servico.getPaciente().getNome(),
        		servico.getPaciente().getCpf(),
        		servico.getPaciente().getRg(),
        		servico.getPaciente().getTelefone(),
        		servico.getPaciente().getDtNascimento(),
        		servico.getPaciente().getSexo(),
        		servico.getPaciente().getIdPessoa(),
        		servico.getPaciente().getGravidade()
        );
        paciente.setId(servico.getPaciente().getId());
        
        Enfermeiro enfermeiro = new Enfermeiro(
        		servico.getEnfermeiro().getNome(),
        		servico.getEnfermeiro().getCpf(),
        		servico.getEnfermeiro().getRg(),
        		servico.getEnfermeiro().getTelefone(),
        		servico.getEnfermeiro().getDtNascimento(),
        		servico.getEnfermeiro().getSexo(),
        		servico.getEnfermeiro().getIdPessoa(),
        		servico.getEnfermeiro().getDtAdmissao()
        );
        enfermeiro.setId(servico.getPaciente().getId());
        
        Medico medico = new Medico(
        		servico.getMedico().getNome(),
        		servico.getMedico().getCpf(),
        		servico.getMedico().getRg(),
        		servico.getMedico().getTelefone(),
        		servico.getMedico().getDtNascimento(),
        		servico.getMedico().getSexo(),
        		servico.getMedico().getIdPessoa(),
        		servico.getMedico().getEspecialidade()
        );
        medico.setId(servico.getPaciente().getId());
        
        
        comboBoxPaciente.setValue(paciente);
        comboBoxMedico.setValue(medico);
        comboBoxEnfermeiro.setValue(enfermeiro);
        
        textFieldId.setText(String.valueOf(servico.getId()));
    }
}
