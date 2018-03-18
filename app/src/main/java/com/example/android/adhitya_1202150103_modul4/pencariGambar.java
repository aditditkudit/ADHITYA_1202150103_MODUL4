package com.example.android.adhitya_1202150103_modul4;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class pencariGambar extends AppCompatActivity {

    /*
    * Langkah ke-1:
    *   Penambahan <uses-permission android:name="android.permission.INTERNET"/> dalam tag <manifest> pada AndroidManifest.xml
    */

    //Deklarasi Komponen yang akan digunakan
    //EditText
    private EditText txtImageURL;
    //Button
    private Button btnImageLoad;
    //ImageView (gambar)
    private ImageView lblImage;
    //ProgressDialog(Loading)
    private ProgressDialog loading;
    //Penunjuk jika gambar sudah di load.
    private int ImageIsLoaded=0;

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_pencari_gambar);
        setTitle("Cari Gambar");

        //Inisialisasi Komponen View
        txtImageURL=(EditText)findViewById(R.id.txtURL);
        btnImageLoad=(Button)findViewById(R.id.loadIMG);
        lblImage=(ImageView)findViewById(R.id.imgView);
        //Aksi Klik pada Tombol
        btnImageLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Memanggil Method yang dapat mengupdate
                loadImage();
            }
        });

        //Cek apakah bundle berisikan sesuatu
        if(saved!=null){
            //Cek apakah didalm bundle nilai kunci sebagai penunjuk data sudah di load sebelumnya
            if(saved.getInt("IMAGE_IS_LOADED")!=0 && !saved.getString("EXTRA_TEXT_URL").isEmpty()){
                txtImageURL.setText(""+saved.getString("EXTRA_TEXT_URL"));
                loadImage();
            }
        }
    }

    /*
    * Method yang berguna untuk melakukan penyimpanan sesuatu pada package
    */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Simpan untuk URL TEXT agar input tidak dilakukan kembali
        outState.putString("EXTRA_TEXT_URL",txtImageURL.getText().toString());
        //Simpan untuk respon pada savedinstancestate
        outState.putInt("IMAGE_IS_LOADED",ImageIsLoaded);
    }

    /*
    * Method yang digunakan untuk mengupdate tampilan / mengubah gambar dengan menggunakan asynctask url
    */
    private void loadImage(){
        /* Image URL Example:
            https://techcrunch.com/wp-content/uploads/2018/03/android-p.png
            https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRivIBd9EoH7MSYKCW6HFiArRNPsTSXABIL4SP-ogg3K1eedrR9
            http://uniacc.cl/landing-uniacc/wp-content/uploads/2016/10/Ghost-Default-Error-Screen.png
        */

        //Mengambil nilai input (String)
        String ImgUrl = txtImageURL.getText().toString();
        //Aksi Asynctask untuk melakukan pencarian/load gambar dari internet
        new LoadImageTask().execute(ImgUrl);
    }



    /*
    * Referensi:
    *   https://www.concretepage.com/android/android-load-image-from-url-with-internet-using-bitmapfactory-and-imageview-setimagebitmap-example
    */

    /*
    * Class Asynctask dengan paramater deklarasi:
    *   Input: String -> Alamat Gambar
    *   Progress: Integer -> Persentase Proses
    *   Result: Bitmap -> Gambar
    */
    public class LoadImageTask extends AsyncTask<String, Integer, Bitmap>{

        /*
       * Method yang dilakukan sebelum aksi dilakukan
       *   Pada aplikasi, tahap ini digunakan untuk memunculkan progress bar beserta propertynya
       */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading=new ProgressDialog(pencariGambar.this);
            loading.setMessage("Waiting ...");
            loading.setMax(100);
            loading.incrementProgressBy(1);
            loading.show();
        }

        /*
        * Method yang digunakan saat eksekusi berlangsung 1x
        *   Tahap ini digunakan untuk mencari gambar dari internet berdasarkan alamat gambar valid dengan mengubahnya menjadi bitmap
        */
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(params[0]);
                bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
            } catch (IOException e) {
                Log.e("doInBackground() - ", e.getMessage());
            }
            return bitmap;
        }

        /*
        * Method yang dilakukan saat proses berlangsung sesuai dengan besarnya request yang dijalankan
        *   Tahap ini digunakan untuk melakukan 'update UI' pada progress bar agar terlihat berjalan
        */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            try {
                Thread.sleep(1000);
                loading.setMessage("Fetching...");
                loading.incrementProgressBy(values[0]);
                loading.setProgress(values[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*
        * Method yang dilakukan setalah proses berhasil di eksekusi
        *   Tahap ini digunakan untuk update pada gambar pada komponen view yang ada sebelumnya (ImageView)
        *       ..digunakan juga untuk memberi petunjuk agar direspon oleh onSaveInstanceState()
        */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            //Set ImageView
            lblImage.setImageBitmap(bitmap);
            //Parameter Sudah di load
            ImageIsLoaded=1;
            //Menghilangkan Loading(ProgressBar)
            loading.dismiss();
        }
    }
}
