package br.com.dudstecnologia.crudmssql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.dudstecnologia.crudmssql.model.Funcionario;
import br.com.dudstecnologia.crudmssql.util.Conexao;

public class CadastroActivity extends AppCompatActivity {

    EditText editId, editNome, editTelefone, editIdade;
    Button btnSalvar, btnExcluir;

    Conexao conexao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        conexao = new Conexao();

        editId = (EditText) findViewById(R.id.editId);
        editNome = (EditText) findViewById(R.id.editNome);
        editTelefone = (EditText) findViewById(R.id.editTelefone);
        editIdade = (EditText) findViewById(R.id.editIdade);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnExcluir = (Button) findViewById(R.id.btnExcluir);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {
            int id = (int) bundle.get("ID");
            recuperarFuncionario(id);
        }

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Connection conn = conexao.Conn();
                String resultado = "";

                if(conn == null) {
                    Toast.makeText(CadastroActivity.this, "Erro ao conectar", Toast.LENGTH_LONG).show();
                    return;
                }

                String id = editId.getText().toString();

                if(id.isEmpty()) {
                    // Inserir um novo registro
                    Funcionario funcionario = new Funcionario();
                    funcionario.setNome(editNome.getText().toString());
                    funcionario.setTelefone(editTelefone.getText().toString());
                    funcionario.setIdade(Integer.parseInt(editIdade.getText().toString()));

                    resultado = inserirFuncionario(conn, funcionario);
                } else {
                    // Atualiza um registro existente
                    Funcionario funcionario = new Funcionario();
                    funcionario.setId(Integer.parseInt(editId.getText().toString()));
                    funcionario.setNome(editNome.getText().toString());
                    funcionario.setTelefone(editTelefone.getText().toString());
                    funcionario.setIdade(Integer.parseInt(editIdade.getText().toString()));

                    resultado = atualizarFuncionario(conn, funcionario);
                }

                Toast.makeText(CadastroActivity.this, resultado, Toast.LENGTH_LONG).show();
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Connection conn = conexao.Conn();

                if(conn == null) {
                    Toast.makeText(CadastroActivity.this, "Erro ao conectar", Toast.LENGTH_LONG).show();
                    return;
                }

                String id = editId.getText().toString();

                if(!id.isEmpty())  {
                    excluirFuncionario(conn, id);
                }
            }
        });
    }

    public void recuperarFuncionario(int id) {

        Connection conn = conexao.Conn();
        String resultado = "";

        if(conn == null) {
            Toast.makeText(CadastroActivity.this, "Erro ao conectar", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            String QUERY = "SELECT * FROM funcionarios WHERE id = " + id;
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY);

            while(resultSet.next()) {
                editId.setText(String.valueOf(id));
                editNome.setText(resultSet.getString("nome"));
                editTelefone.setText(resultSet.getString("telefone"));
                editIdade.setText(String.valueOf(resultSet.getInt("idade")));
            }

        } catch (SQLException erro) {
            Toast.makeText(CadastroActivity.this, "Ocorreu um erro: " + erro, Toast.LENGTH_LONG).show();
        }

    }

    public String inserirFuncionario(Connection conn, Funcionario funcionario) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO funcionarios (nome, telefone, idade) VALUES (?, ?, ?)");
            preparedStatement.setString(1, funcionario.getNome());
            preparedStatement.setString(2, funcionario.getTelefone());
            preparedStatement.setInt(3, funcionario.getIdade());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            return "Salvo com sucesso";
        } catch (SQLException erro) {
            return "Erro ao salvar: " + erro.getMessage();
        }
    }

    public String atualizarFuncionario(Connection conn, Funcionario funcionario) {
        try {
            String QUERY_UPDATE = "UPDATE funcionarios SET nome = ?, telefone = ?, idade = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(QUERY_UPDATE);
            preparedStatement.setString(1, funcionario.getNome());
            preparedStatement.setString(2, funcionario.getTelefone());
            preparedStatement.setInt(3, funcionario.getIdade());
            preparedStatement.setInt(4, funcionario.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            return "Atualizado com sucesso";
        } catch (SQLException erro) {
            return "Erro ao atualizar: " + erro.getMessage();
        }
    }

    public void excluirFuncionario(Connection conn, String id) {
        try {
            String QUERY_DELETE = "DELETE FROM funcionarios WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(QUERY_DELETE);
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            Toast.makeText(CadastroActivity.this, "Excluido com sucesso", Toast.LENGTH_LONG).show();
            finish();

        } catch (SQLException erro) {
            Toast.makeText(CadastroActivity.this, "Erro ao salvar: " + erro.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
