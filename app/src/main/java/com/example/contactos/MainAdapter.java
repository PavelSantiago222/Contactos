package com.example.contactos;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull MainModel model) {
        holder.nombre.setText(model.getNombre());
        holder.materia.setText(model.getMateria());
        holder.teléfono.setText(model.getTeléfono());
        holder.correo.setText(model.getCorreo());

        Glide.with(holder.img.getContext())
                .load(model.getTurl())
                .placeholder(com.google.firebase.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.google.firebase.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,1450)
                        .create();

                //dialogPlus.show();

                View view = dialogPlus.getHolderView();

                EditText nombre = view.findViewById(R.id.txtNombre);
                EditText materia = view.findViewById(R.id.txtMateria);
                EditText teléfono = view.findViewById(R.id.txtTelefono);
                EditText correo = view.findViewById(R.id.txtCorreo);
                EditText turl = view.findViewById(R.id.txtUrlImagen);

                Button btnActualizar = view.findViewById(R.id.btnActualizar);

                nombre.setText(model.getNombre());
                materia.setText(model.getMateria());
                teléfono.setText(model.getTeléfono());
                correo.setText(model.getCorreo());
                turl.setText(model.getTurl());

                dialogPlus.show();

                btnActualizar.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("nombre",nombre.getText().toString());
                        map.put("materia",materia.getText().toString());
                        map.put("teléfono",teléfono.getText().toString());
                        map.put("correo",correo.getText().toString());
                        map.put("turl",turl.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("docentes")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.nombre.getContext(), "Datos actualizados correctamente.", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.nombre.getContext(), "Error al actualizar los datos.", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });

            }
        });

        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.nombre.getContext());
                builder.setTitle("Estas seguro?");
                builder.setMessage("Los datos eliminados no se pueden deshacer.");

                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("docentes")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.nombre.getContext(),"Conselado.",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });



    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView nombre,materia,teléfono,correo;

        Button btnEditar,btnEliminar;

        public myViewHolder(@NonNull View itemView){
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.img1);
            nombre = (TextView)itemView.findViewById(R.id.nombretext);
            materia = (TextView)itemView.findViewById(R.id.materiatext);
            teléfono = (TextView)itemView.findViewById(R.id.telefonotext);
            correo = (TextView)itemView.findViewById(R.id.correotext);

            btnEditar = (Button)itemView.findViewById(R.id.btnEditar);
            btnEliminar = (Button)itemView.findViewById(R.id.btnEliminar);
        }
    }

}

