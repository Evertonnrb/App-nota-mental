package com.dmd.notamental;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txt;
    private Button btnNext;
    private ImageView imgTalk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgTalk = (ImageView) findViewById(R.id.img_talk);
        imgTalk.setOnClickListener(this);
        txt = (TextView) findViewById(R.id.tv_talk);

        btnNext = (Button) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    private void _dispararIntent(Intent intent) {
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        Uri uri = null;
        switch (item.getItemId()) {
            case R.id.menu_share:
                intent = new Intent()
                        .setAction(Intent.ACTION_SEND)
                        .putExtra(Intent.EXTRA_TEXT, "https://drive.google.com/open?id=18uwbsjxjwp-pf-Xf9i3NA02uBaaQOgdX")
                        .setType("text/plain");
                _dispararIntent(intent);
                break;
            case R.id.menu_like_us:
                uri = Uri.parse("http://www.facebook.com");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.menu_config:
                intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
                break;
            case R.id.menu_sair:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void _prompSpeech() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Lembra de:");
        try {
            startActivityForResult(intent, 200);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Desculpe seu aparelho não suporta essa função", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> resultado = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                txt.setText(resultado.get(0));
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_talk)
            _prompSpeech();
        if (v.getId() == R.id.btn_next)
            _next();
    }

    private void _next() {
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeCustomAnimation(
                getApplicationContext(),
                R.anim.fade_in,
                R.anim.mover_direita
        );
        ActivityCompat.startActivity(MainActivity.this, intent, compat.toBundle());
    }
}
