package br.com.dudstecnologia.crudmssql.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.dudstecnologia.crudmssql.CadastroActivity;
import br.com.dudstecnologia.crudmssql.R;
import br.com.dudstecnologia.crudmssql.model.Funcionario;

public class FuncionarioAdapter extends RecyclerView.Adapter<FuncionarioAdapter.FuncionarioViewHolder> {

    private Context ctx;
    private List<Funcionario> lista;

    public class FuncionarioViewHolder extends RecyclerView.ViewHolder {

        TextView txtItemNome, txtItemTelefone;
        Button btnItemVer;

        public FuncionarioViewHolder(@NonNull View itemView) {
            super(itemView);

            txtItemNome = (TextView) itemView.findViewById(R.id.txtItemNome);
            txtItemTelefone = (TextView) itemView.findViewById(R.id.txtItemTelefone);
            btnItemVer = (Button) itemView.findViewById(R.id.btnItemVer);
        }
    }

    public FuncionarioAdapter(Context ctx, List<Funcionario> lista) {
        this.ctx = ctx;
        this.lista = lista;
    }

    @NonNull
    @Override
    public FuncionarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_funcionario, parent, false);

        return new FuncionarioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FuncionarioViewHolder holder, int position) {
        final Funcionario funcionario = lista.get(position);

        holder.txtItemNome.setText(funcionario.getNome());
        holder.txtItemTelefone.setText(funcionario.getTelefone());

        holder.btnItemVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent abreForm = new Intent(ctx, CadastroActivity.class);
                abreForm.putExtra("ID", funcionario.getId());
                ctx.startActivity(abreForm);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.lista.size();
    }
}
