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

import grupo1.hospital.app.classes.Pessoa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PessoaController implements Initializable {
	@FXML
	private TableView<Pessoa> tableViewPessoa;
	
	@FXML
	private TableColumn<Pessoa, Integer> tableColumnId;
	@FXML
	private TableColumn<Pessoa, String> tableColumnNome;
	@FXML
	private TableColumn<Pessoa, String> tableColumnCpf;
	@FXML
	private TableColumn<Pessoa, Integer> tableColumnRg;
	@FXML
	private TableColumn<Pessoa, LocalDate> tableColumnDtNascimento;
	@FXML
	private TableColumn<Pessoa, String> tableColumnSexo;
	@FXML
	private TableColumn<Pessoa, String> tableColumnTelefone;
	
	@FXML
	private TextField textFieldNome;
	@FXML
	private TextField textFieldCpf;
	@FXML
	private DatePicker datePickerDtNascimento;
	@FXML
	private TextField textFieldTelefone;
	@FXML
	private TextField textFieldId;
	@FXML
	private TextField textFieldRg;
	@FXML
	private TextField textFieldSexo;
	
	@FXML
	private Button buttonSalvar;
	@FXML
	private Button buttonCancelar;
	@FXML
	private Button buttonExcluir;
	@FXML
	private Button buttonEditar;
	@FXML
	private Button buttonNovo;
	
	private ObservableList<Pessoa> olPessoa;
	private Integer idPessoaSelecionada;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		insertTablePessoa();
        tableViewPessoa.getSelectionModel().selectedItemProperty().addListener(
          (observable, oldValue, newValue) -> selecionarPessoa(newValue));
    }
	
    private void selecionarPessoa(Pessoa pessoa) {
    	try {
    		System.out.println(pessoa);
            idPessoaSelecionada = pessoa.getIdPessoa();

            textFieldId.setText(String.valueOf(pessoa.getIdPessoa()));
            textFieldNome.setText(pessoa.getNome());
            textFieldRg.setText(String.valueOf(pessoa.getRg()));
            textFieldCpf.setText(pessoa.getCpf());
            textFieldSexo.setText(pessoa.getSexo());
            datePickerDtNascimento.setValue(pessoa.getDtNascimento());
            textFieldTelefone.setText(String.valueOf(pessoa.getTelefone()));
		} catch (Exception e) {
			System.out.println("Ninguém selecionado.");
		}

    }

    @FXML
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
    
    @FXML
    private void handlerSalvarPessoa(ActionEvent event) {
    	try {
    		if(textFieldNome.getText().isEmpty()) {
    			throw new Exception("Nome Vazio");
    		}
    		Integer id = Integer.valueOf(textFieldId.getText());
            String nome = textFieldNome.getText();
            Integer rg = 0;
            if(!textFieldRg.getText().isEmpty())
            	rg = Integer.valueOf(textFieldRg.getText());
            String cpf = textFieldCpf.getText();
            String sexo = textFieldSexo.getText();
            LocalDate nascimento = datePickerDtNascimento.getValue();
            String telefone =  textFieldTelefone.getText();
           
            Pessoa pessoa = new Pessoa(nome, cpf, rg, telefone, nascimento, sexo, id);
            
            String method = "";
            String urll = "";
            if (pessoa.getIdPessoa() != 0) {
            	method = "PUT";
            	urll = "http://localhost:8080/api/pessoa/atualizar";
            }else {
            	method = "POST";
            	urll = "http://localhost:8080/api/pessoa";
            }
            
    		try {
    			JSONObject mainData = new JSONObject();
    			mainData.put("nome", pessoa.getNome());
    			mainData.put("cpf", pessoa.getCpf());
    			mainData.put("telefone", pessoa.getTelefone());
    			mainData.put("rg", pessoa.getRg());
    			mainData.put("dtNascimento", pessoa.getDtNascimento());
    			mainData.put("sexo", pessoa.getSexo());
    			mainData.put("idPessoa", pessoa.getIdPessoa());

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
            insertTablePessoa();
		} catch (Exception e) {
			System.out.println("Não cadastrado.");
		}
    }
	
	public void insertTablePessoa() {
		try {
			List<Pessoa> listaP = listarTodasPessoas();

			tableColumnId.setCellValueFactory(new PropertyValueFactory<>("idPessoa"));
	        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
	        tableColumnRg.setCellValueFactory(new PropertyValueFactory<>("rg"));
	        tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
	        tableColumnSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
	        tableColumnDtNascimento.setCellValueFactory(new PropertyValueFactory<>("dtNascimento"));
	        tableColumnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
	 

	        olPessoa = FXCollections.observableArrayList(listaP);
	        tableViewPessoa.setItems(olPessoa);
		} catch (IOException e) {
			System.out.println("Erro grave");
		}
	}
	
	private void limpar() {
		textFieldId.setText("0");
        textFieldNome.setText(null);
        textFieldRg.setText(null);
        textFieldCpf.setText(null);
        textFieldSexo.setText(null);
        datePickerDtNascimento.setValue(null);
        textFieldTelefone.setText(null);
        idPessoaSelecionada = 0;
	}
	
	@FXML
	private void handlerCancelar(ActionEvent event) {
		limpar();
	}
	
    @FXML
    private void handlerExcluirPessoa(ActionEvent event) {
        if (idPessoaSelecionada != null && idPessoaSelecionada != 0){
            try {
            	System.out.print("Excluir pessoa id: "+idPessoaSelecionada);
                URL url = new URL("http://localhost:8080/api/pessoa/excluir?id="+idPessoaSelecionada);
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
            insertTablePessoa();
        }
        
    }
}
