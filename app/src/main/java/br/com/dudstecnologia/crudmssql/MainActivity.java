package br.com.dudstecnologia.crudmssql;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.dudstecnologia.crudmssql.adapters.FuncionarioAdapter;
import br.com.dudstecnologia.crudmssql.model.Funcionario;
import br.com.dudstecnologia.crudmssql.util.Conexao;

public class MainActivity extends AppCompatActivity {

    Conexao conexao;

    Button btnFormulario;
    RecyclerView recyclerViewFuncionarios;

    FuncionarioAdapter funcionarioAdapter;
    List<Funcionario> listaFuncionarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conexao = new Conexao();

        btnFormulario = (Button) findViewById(R.id.btnFormulario);
        recyclerViewFuncionarios = (RecyclerView) findViewById(R.id.recyclerViewFuncionarios);

        listaFuncionarios = new ArrayList<>();
        funcionarioAdapter = new FuncionarioAdapter(MainActivity.this, listaFuncionarios);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerViewFuncionarios.setLayoutManager(layoutManager);
        recyclerViewFuncionarios.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFuncionarios.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));
        recyclerViewFuncionarios.setAdapter(funcionarioAdapter);

        listarFuncionarios();

        btnFormulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abreForm = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(abreForm);
            }
        });
    }

    public void listarFuncionarios() {
        Connection conn = conexao.Conn();

        if(conn == null) {
            Toast.makeText(MainActivity.this, "Erro ao conectar", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            String QUERY = "SELECT * FROM funcionarios";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY);

            while(resultSet.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(resultSet.getInt("id"));
                funcionario.setNome(resultSet.getString("nome"));
                funcionario.setTelefone(resultSet.getString("telefone"));
                funcionario.setIdade(resultSet.getInt("idade"));

                listaFuncionarios.add(funcionario);
            }

            funcionarioAdapter.notifyDataSetChanged();

        } catch (SQLException erro) {
            Toast.makeText(MainActivity.this, "Ocorreu um erro: " + erro, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        listaFuncionarios.clear();
        listarFuncionarios();
    }
}
