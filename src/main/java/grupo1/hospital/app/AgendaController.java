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
import java.util.Observable;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import grupo1.hospital.app.classes.Agenda;
import grupo1.hospital.app.classes.Enfermeiro;
import grupo1.hospital.app.classes.Paciente;
import grupo1.hospital.app.classes.Pessoa;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class AgendaController implements Initializable {
	
	@FXML
	private TableView<Agenda> tableViewAgenda;
	
	@FXML
	private TableColumn<Agenda, Integer> tableColumnId;
	@FXML
	private TableColumn<Agenda, String> tableColumnPaciente;
	@FXML
	private TableColumn<Agenda, LocalTime> tableColumnHorario;
	@FXML
	private TableColumn<Agenda, String> tableColumnTelefone;
	@FXML
	private TableColumn<Agenda, LocalDate> tableColumnData;
	
	@FXML
	private DatePicker datePickerData;
	@FXML
	private ComboBox<Paciente> comboBoxPaciente;
	@FXML
	private TextField textFieldId;
	@FXML
	private TextField textFieldHorario;
	
	@FXML
	private Button buttonSalvar;
	@FXML
	private Button buttonCancelar;
	@FXML
	private Button buttonExcluir;
	
	private ObservableList<Agenda> olAgenda;
	private ObservableList<Paciente> olPaciente;
	private Integer idAgendaSelecionada;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		insertTableAgenda();
		insertPacientesComboBox();
        tableViewAgenda.getSelectionModel().selectedItemProperty().addListener(
          (observable, oldValue, newValue) -> selecionarAgenda(newValue));
		
	}
	
	public void insertPacientesComboBox() {
		try {
			olPaciente = FXCollections.observableArrayList(listarTodasPacientes());
		} catch (IOException e) {
			System.out.println("Erro no combobox pessoa");
			System.out.println(e);
		}
		comboBoxPaciente.setItems(olPaciente);
	}
	
	private static List<Paciente> listarTodasPacientes() throws IOException { 
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

	public void insertTableAgenda() {
		try {
			List<Agenda> listaP = listarTodosAgendas();

			tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
	        tableColumnPaciente.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getPaciente().getNome()));
	        tableColumnTelefone.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getPaciente().getTelefone()));
	        // new PropertyValueFactory<>("pacienteDados")
	        tableColumnData.setCellValueFactory(new PropertyValueFactory<>("dtAgenda"));
	        tableColumnHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
	 
	        olAgenda = FXCollections.observableArrayList(listaP);
	        tableViewAgenda.setItems(olAgenda);
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Erro grave");
		}
	}

	@FXML
    private static List<Agenda> listarTodosAgendas() throws IOException { 
        URL url = new URL("http://localhost:8080/api/agenda/todos");
        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpUrlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        
        String line;
        String data = "";
        List<Agenda> agendaList = new ArrayList<Agenda>();
        while ((line=bufferedReader.readLine() )!=null){
        	data = data+line;
        }
        if (!data.isEmpty()){
            JSONArray agenda = new JSONArray(data);
            agendaList.clear();
            for (int i =0 ; i< agenda.length(); i++){
            	JSONObject obj = agenda.getJSONObject(i);
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
            	
            	String[] dtAgenda = obj.getString("dtAgenda").split("-");
            	String[] horario = obj.getString("horario").split(":");
            	Agenda e = new Agenda(
        			LocalDate.of(Integer.valueOf(dtAgenda[0]), Integer.valueOf(dtAgenda[1]), Integer.valueOf(dtAgenda[2])),
        			LocalTime.of(Integer.valueOf(horario[0]), Integer.valueOf(horario[1])),
        			p
            	);
            	e.setId(obj.getInt("id"));
            	agendaList.add(e);
            }
        }
        return agendaList;
    }

	public void limpar() {
    	datePickerData.setValue(null);;
    	comboBoxPaciente.setValue(null);
    	textFieldId.setText("0");
    	textFieldHorario.setText("");;
    }
    
    @FXML
    public void handlerCancelar(ActionEvent event) {
    	limpar();
    }

    private void selecionarAgenda(Agenda agenda) {
    	
    		idAgendaSelecionada = agenda.getId();

    		datePickerData.setValue(agenda.getDtAgenda());
    		
            Paciente p = new Paciente(
            		agenda.getPaciente().getNome(), agenda.getPaciente().getCpf(), agenda.getPaciente().getRg(), agenda.getPaciente().getTelefone(),
            		agenda.getPaciente().getDtNascimento(), agenda.getPaciente().getSexo(), agenda.getPaciente().getIdPessoa(), agenda.getPaciente().getGravidade()
            );
            comboBoxPaciente.setValue(p);
            textFieldId.setText(String.valueOf(idAgendaSelecionada));
            textFieldHorario.setText(String.valueOf(agenda.getHorario()));
		

    }
    
    @FXML
    public void handlerExcluir(ActionEvent event) {
    	if (idAgendaSelecionada != null && idAgendaSelecionada != 0){
            try {
                URL url = new URL("http://localhost:8080/api/agenda/excluir?id="+idAgendaSelecionada);
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
            insertTableAgenda();
        }
    }

    @FXML
    public void handlerSalvar(ActionEvent event) {
    	
    		Integer id = Integer.valueOf(textFieldId.getText());
    		LocalDate dtAgenda = datePickerData.getValue();
    		String[] time = textFieldHorario.getText().split(":");
    		LocalTime horario = LocalTime.of(Integer.valueOf(time[0]), Integer.valueOf(time[1]));
            Paciente pacienteSelecionado = comboBoxPaciente.getValue();

            Agenda agenda = new Agenda(
        		dtAgenda, horario, pacienteSelecionado
            );
            agenda.setId(id);
            System.out.println("--agenda: "+agenda.getId()+", "+agenda.getHorario()+", "+agenda.getDtAgenda()+", "+
            		agenda.getPaciente().getId());
            
            String method = "";
            String urll = "";
            if (agenda.getId() != 0) {
            	method = "PUT";
            	urll = "http://localhost:8080/api/agenda/atualizar";
            }else {
            	method = "POST";
            	urll = "http://localhost:8080/api/agenda";
            }
            
    		try {
    			System.out.println("agenda: "+agenda);
    			JSONObject mainData = new JSONObject();
    			mainData.put("dtAgenda", agenda.getDtAgenda());
    			mainData.put("horario", agenda.getHorario());
    			mainData.put("idPaciente", agenda.getPaciente().getId());
    			mainData.put("id", agenda.getId());

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
            insertTableAgenda();
		
    }
}
