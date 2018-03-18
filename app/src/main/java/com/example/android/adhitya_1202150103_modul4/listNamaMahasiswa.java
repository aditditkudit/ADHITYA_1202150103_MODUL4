package com.example.android.adhitya_1202150103_modul4;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class listNamaMahasiswa extends AppCompatActivity {
    //Deklarasi Button
private Button btnProses;
    //Deklarasi ListView
private ListView listMahasiswa;
    //Deklarasi Progress Dialog
private ProgressDialog progressList;
    //Penunjuk jika data yang loaded masih 0
private static int dataIsLoaded = 0;
    //Deklarasi Array Adapter
private ArrayAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nama_mahasiswa);
        //Inisiasi Komponen View untuk Button
        btnProses = (Button)findViewById(R.id.buttonAsync);
        //Inisiasi Komponen View untuk ListView
        listMahasiswa = (ListView)findViewById(R.id.listMahasiswa);

        if(savedInstanceState!=null){
            //Cek apakah kunci penunjuk bukan 0 atau jika 1 maka data sudah di melalui proses load sebelumhya
            if(savedInstanceState.getInt("DATA_LIST_LOADED")==1) {
                //Set Text pada Button
                btnProses.setText("Data Already Loaded, reload?");
                //Eksekusi Asynctask yang diisikan dengan datanya (@param listName())
                new listNamaAsyncTask().execute(listNamaM());
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Simpan sesuatu dengan key
        outState.putInt("ITEM_LIST_LOADED",dataIsLoaded);
    }

    public void asyncTask(View view) {
        //Method akan Eksekusi jika tombol button ditekan
        //Memanggil Asynctask dari class listNameAsyncTask() pada sub class ini
        new listNamaAsyncTask().execute(listNamaM());
    }

    /*
    *Method yang mengembalikan array bentuk string, berguna untuk mempopulasikan data yang nantinya akan digunakn pada ListView
     */
    public String[] listNamaM(){
        return new String[]{
          "Tavio","Alamillo","Tyler","Wellington","Kendrick","Derrick","Jayden","Mario","Russell","Kevin","Kudit","Adhitya","Adit","Kuditq","Kudit Panutan kalian","Kudit terbaik","Brow Brow","Ayam Talua"
        };
    }

    /*
    *Class Asyntask dengan parameter:
    * Input = String[] -> Array
    * Progress = Integer -> Proces
    * Result: String[] -> Array
     */
    private class listNamaAsyncTask extends AsyncTask<String[], Integer, String[]>{
        /*
        *Method ini akan digunakan sebelum Asyntask dilakukan
        * pada tahap ini digunakan untuk memuncul progress bar
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressList = new ProgressDialog(listNamaMahasiswa.this);
            progressList.setMessage("Sabar...");
            progressList.setMax(100);
            progressList.show();
        }

        /*
        *Method ini akan berfungsi sebagai dsaat eksekusi berlangsaung 1x
        * Pada tahap ini digunakan untuk mengembalikan data input agar menjadi data outputnya menjadi (String[])
         */
        @Override
        protected String[] doInBackground(String[]... strings) {
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
            Log.d("doInBackground","n: "+strings[0].length);
            return strings[0];
        }

        /*
        *Method ini akan berfungsi jika disaat proses berlangsung sesuai dengan besarnya request yang dijalankan
        * Tahap ini digunakan untuk melakukan 'Update UI' pada progress bar agar terlihat berjalan
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            try{
                Thread.sleep(1500);
                progressList.setMessage("Populating...");
                progressList.incrementProgressBy(values[0]);
                progressList.setProgress(values[0]);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        /*
        *Method ini akan berfungsi jika dilakukan setelah proses berhasil di eksekusi
        * Tahap ini digunakan untuk mengisikan disaat data diinput kedalam ListView beserta dengan pembuatan dan set pada Adapter
        *   digunakan juga untuk memberi petunjuk agar direspon oleh onSavedInstanceState()
         */
        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            Log.d("OnPostExecute: "," n:"+strings.length+" x:"+strings);
            //Pembuatan dan mengisi data ArrayAdapter dengan menggunakan layout bawaan dari Android ListView (android.R.layout.simple_list_item_1)
            itemAdapter = new ArrayAdapter(listNamaMahasiswa.this,android.R.layout.simple_list_item_1, strings);
            //set dataAdapter
            listMahasiswa.setAdapter(itemAdapter);
            //Set dataIsLoaded untuk memberitahukan onSaveInstanceSaved bahwa data sudah di load
            dataIsLoaded=1;
            //Menghilangkan progress bat disaat sudah terinput.
            progressList.dismiss();

        }
    }
}
