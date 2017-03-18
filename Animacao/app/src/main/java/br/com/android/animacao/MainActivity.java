package br.com.android.animacao;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView img = (ImageView) findViewById(R.id.imagem);
        AnimationDrawable anim = (AnimationDrawable) img.getDrawable();
        anim.start();

        boolean primeiraVez = true;
        int angulo = 180;

        Animation giraIda = new RotateAnimation(0, angulo, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
        Animation giraRetorno = new RotateAnimation(angulo, 0, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);

        Animation a = primeiraVez ? giraIda : giraRetorno;
        a.setDuration(20000);
        a.setFillAfter(false);
        img.startAnimation(a);
    }
}
