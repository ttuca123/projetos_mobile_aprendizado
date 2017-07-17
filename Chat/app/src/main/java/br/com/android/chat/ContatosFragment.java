package br.com.android.chat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import static br.com.android.chat.R.*;
import static br.com.android.chat.R.layout.*;


public class ContatosFragment extends Fragment {

    private View view2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private LayoutInflater inflater;
    private ViewGroup container;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton btnAdd = (FloatingActionButton) getView().findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                View view2 = inflater.inflate(layout.custom_mensagem, container, false);

                final EditText editEmail = new EditText(getContext());

                editEmail.setTextColor(Color.parseColor("#0F8000"));
                alert.setView(view2);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SharedPreferences preferences = getActivity().getSharedPreferences("USER_INFORMATION", Context.MODE_PRIVATE);
                                final String id_user = String.valueOf(preferences.getInt("id_usuario", 0));

                                String URL = "http://192.168.25.9/projetos_web_aprendizado/chat/add_contact.php";

                                Ion.with(getContext())
                                        .load(URL)
                                        .setMultipartParameter("email_user", editEmail.getText().toString())
                                        .setMultipartParameter("id_user", id_user)
                                        .asJsonObject()
                                        .setCallback(new FutureCallback<JsonObject>() {
                                            @Override
                                            public void onCompleted(Exception e, JsonObject result) {
                                                if (result != null) {
                                                    if (result.get("retorno").getAsString().equals("YES")) {
                                                        Toast.makeText(getContext(), "Contato cadastrado com sucesso!", Toast.LENGTH_LONG).show();

                                                        //                                           SQLiteHelper db = new SQLiteHelper(getContext());
                                                        //                                           db.save_contact(Integer.parseInt(id_user), result.get("contact_user").getAsInt(), result.get("nome_user").getAsString(), result.get("photo_user").getAsString());
                                                        //                                           refreshListView();

                                                    } else if (result.get("retorno").getAsString().equals("EMAIL_ERROR")) {
                                                        Toast.makeText(getContext(), "E-mail não existe!", Toast.LENGTH_LONG).show();
                                                    } else if (result.get("retorno").getAsString().equals("CONTACT_EXIST")) {
                                                        Toast.makeText(getContext(), "Contato já cadastrado anteriormente!", Toast.LENGTH_LONG).show();
                                                    }
                                                } else {
                                                    Toast.makeText(getContext(), "Erro ao buscar email.", Toast.LENGTH_LONG).show();
                                                }
                                            }

                                            ;

                                        });
                            }
                        });

                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.inflater = inflater;
        this.container = container;
        // Inflate the layout for this fragment


        return inflater.inflate(fragment_contatos, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

