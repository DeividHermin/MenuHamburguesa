package com.example.deivi.menuhamburguesa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.TextViewCompat;
import android.text.Layout;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ProgressBar bar;
    TextView tv;
    int i=0;
    int opc;
    //Lienzo fondo;
    //LinearLayout layout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tv = (TextView)findViewById(R.id.lbProgress);
        bar = (ProgressBar)findViewById(R.id.progressbar);
        bar.setMax(100);

        //layout1 = (LinearLayout)findViewById(R.id.layout);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        LinearLayout layout1 = (LinearLayout)findViewById(R.id.layout);
        if (id == R.id.nav_circunferencias) {
            opc=1;
            Toast.makeText(MainActivity.this, "opc1", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_rectangulos) {
            opc=2;
        } else if (id == R.id.nav_ovalos) {
            opc=3;
        } else if (id == R.id.nav_ocultar) {
            oculta();
        } else if (id == R.id.nav_pulsar) {
            pulsar();
        } else if (id == R.id.nav_hilo) {
            hilo();
        }

        Lienzo fondo = new Lienzo(this);
        layout1.addView(fondo);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }

    public void pulsar(){
        bar.setVisibility(View.VISIBLE);
        if (i>100)
            i=0;
        bar.setProgress(i++);
        tv.setText(""+i);
    }

    public void oculta(){
        if (bar.getVisibility()==ProgressBar.VISIBLE)
            bar.setVisibility(View.GONE);
        else
            bar.setVisibility(View.VISIBLE);
    }




    public void hilo(){
        AsyncTaskCargaDatos as=new AsyncTaskCargaDatos();
        as.execute();

    }

    //--------------------------------------------------------------------------------
    public class AsyncTaskCargaDatos extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            for(int i=1; i<=10; i++) {
                //tareaLarga();
                try {
                    Thread.sleep(1000);}
                catch (InterruptedException e) {}

                publishProgress(i*10);

                if(isCancelled())
                    break;
            }

            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progreso = values[0].intValue();
            tv.setText(""+progreso);
            bar.setProgress(progreso);
        }

        @Override
        protected void onPreExecute() {
            bar.setMax(100);
            bar.setProgress(0);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result)
                Toast.makeText(MainActivity.this, "Tarea finalizada!",
                        Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(MainActivity.this, "Tarea cancelada!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    class Lienzo extends View {

        public Lienzo(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            Toast.makeText(MainActivity.this, "Entra onDraw", Toast.LENGTH_SHORT).show();
            int ancho = canvas.getWidth();
            int alto = canvas.getHeight();

            Paint pincel1 = new Paint();

            limpiar(canvas, pincel1, ancho, alto);

            pincel1.setARGB( 255, 255, 0, 0);
            pincel1.setStyle(Paint.Style.STROKE);

            switch (opc){
                case 1: dibujaCirculo(canvas, pincel1, ancho, alto);
                    break;
                case 2: dibujaRectangulo(canvas, pincel1, ancho, alto);
                    break;
                case 3: dibujaOvalo(canvas, pincel1, ancho, alto);
                    break;
            }
        }

        public void limpiar(Canvas canvas, Paint pincel1, int ancho, int alto){
            pincel1.setColor(Color.WHITE);
            pincel1.setStyle(Paint.Style.FILL);
            canvas.drawRect(0, 0, ancho, alto, pincel1);
            Toast.makeText(MainActivity.this, "Limpiado", Toast.LENGTH_SHORT).show();
        }

        public void dibujaCirculo(Canvas canvas, Paint pincel1, int ancho, int alto){
            for (int f = 0; f < 10; f++) {
                canvas.drawCircle(ancho / 6, alto / 9, f * 15, pincel1);
            }
            Toast.makeText(MainActivity.this, "Circulo dibujado", Toast.LENGTH_SHORT).show();
        }

        public void dibujaRectangulo(Canvas canvas, Paint pincel1, int ancho, int alto){
            for (int f = 0; f < 40; f++) {
                canvas.drawRect(f*15, f*15, f*15+300, f*15+300, pincel1);
            }
            Toast.makeText(MainActivity.this, "Rectangulo dibujado", Toast.LENGTH_SHORT).show();
        }

        public void dibujaOvalo(Canvas canvas, Paint pincel1, int ancho, int alto){
            for (int f = 0; f < 10; f++) {
                canvas.drawOval(ancho / 3, alto / 4, f * 15, f * 15, pincel1);
            }
            Toast.makeText(MainActivity.this, "Ovalo dibujado", Toast.LENGTH_SHORT).show();
        }
    }
}