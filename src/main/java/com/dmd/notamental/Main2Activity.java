package com.dmd.notamental;

import android.content.DialogInterface;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {


    private EditText edtWrite;
    private TextToSpeech speech;
    private Button btnOk;
    private ImageView imgWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        edtWrite = (EditText) findViewById(R.id.edt_write);
        btnOk = (Button) findViewById(R.id.btn_ouvir);
        btnOk.setOnClickListener(this);
        imgWrite = (ImageView) findViewById(R.id.img_write);
        imgWrite.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fechar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_sair_sair) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.deseja_fechar_app)
                    .setMessage(getString(R.string.atencao))
                    .setNegativeButton(getString(R.string.nao),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getApplicationContext(),
                                            R.string.gravar_notas, Toast.LENGTH_SHORT)
                                            .show();
                                    startActivity(
                                            new Intent(
                                                    Main2Activity.this,
                                                    MainActivity.class
                                            )
                                    );
                                }
                            })
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .create()
                    .show();
        }
        return true;
    }


    private void _falar() {
        speech = new TextToSpeech(Main2Activity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (_validarFala(edtWrite)) {
                    if (status == TextToSpeech.SUCCESS) {
                        speech.speak(edtWrite.getText().toString() + "", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
                _limparCampo(edtWrite);
            }
        });
    }

    private void _limparCampo(EditText edt) {
        edt.setText("");
    }


    private void _dispararIntent(Intent intent) {
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Ação não suportada", Toast.LENGTH_SHORT).show();
            _limparCampo(edtWrite);
        }
    }

    private boolean _validarFala(EditText edt) {
        if (edt.getText().toString() == ""
                || edt.getText().toString().equals("")
                || edt.getText().toString().length() < 0
                || edt.getText().toString().isEmpty()) {
            edt.setError(getString(R.string.digite_algo));
            return false;
        }
        return true;
    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.mover_esquerda, R.anim.fade_out);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_ouvir && v.getId() == R.id.img_write)
            _falar();
        if(v.getId()==R.id.btn_next)
            Toast.makeText(getApplicationContext(),"Implementar",Toast.LENGTH_SHORT).show();
    }
}
