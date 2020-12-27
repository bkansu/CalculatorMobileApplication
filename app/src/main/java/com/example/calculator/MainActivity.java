package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    public String screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
    }
    /*Clear tuşuna basıldığında ekranı temizleyen kod parçası */
    public void clearClick(View v) {
        editText.setText("");
    }

    public void calculateClick(View v) {/*Eşittir tuşuna bastığımızda çalışan fonksiyon */
        String splitter[] = null;
        ArrayList<Double> numbers = new ArrayList<>();
        ArrayList<String> chars = new ArrayList<>();
        screen = editText.getText().toString();
        splitter = screen.split(" ");/* Ekranda yazan değerleri boşluğa göre split edip string dizisine atıyoruz */
        for (int i = 0; i < splitter.length; i++) {/* Bu döngüde sayıları numbers dizisine karakterleri de chars dizisine atıyoruz */
            numbers.add(Double.parseDouble(splitter[i]));
            if (splitter.length > i + 1) chars.add(splitter[i + 1]);
            i++;
        }
        double sonuc;
        double temp;
        boolean flag=false;
        for (int i = 0; i < chars.size(); i++) {
            switch (chars.get(i)) {/* İşlem önceliği  için çarpma ve bölme işlemlerini yapıp dizileri yeniden düzenliyoruz */
                case "*":
                    temp = numbers.get(i) * numbers.get(i + 1);
                    numbers.set(i, temp);
                    numbers.remove(i + 1);
                    chars.remove(i);
                    i--;
                    break;
                case "/":
                    if(numbers.get(i+1)==0){/* Burada 0'a bölme hatası için exception handling yaptık*/
                        flag=true;
                        Toast toast = Toast.makeText(getApplicationContext(), "Sıfıra Bölme Hatası!!", Toast.LENGTH_LONG);
                        toast.show();
                        break;
                    }
                    temp = numbers.get(i) / numbers.get(i + 1);
                    numbers.set(i, temp);
                    numbers.remove(i + 1);
                    chars.remove(i);
                    i--;
                    break;
            }
        }
        sonuc = numbers.get(0);
        for (int i = 0; i < chars.size(); i++) {/* İşlem önceliği yapıldıktan sonra kalan işlemleri yapan kod parçası */
            switch (chars.get(i)) {
                case "+":
                    sonuc += numbers.get(i + 1);
                    break;
                case "-":
                    sonuc -= numbers.get(i + 1);
                    break;
            }
        }
        if(flag==true){/* 0'a bölme hatası varsa burada uyarı veriyoruz */
            editText.setText("Sıfıra Bölme Hatası");
        }
        else{/* Hata yoksa sonucu yazdırıyoruz */
            screen += " =  " + sonuc;
            editText.setText(screen);
        }
    }

    public void btnClick(View v) {/*Fonksiyonel tuşlara bassıldığında kullandığoımız kod parçası */
        screen = editText.getText().toString();/* Ekranda yazanları screen stringe aldık */

        /* Burada +, -, *, / işlemleri yapacaksak öncesinde mutlaka sayı  olması gerektiği için exception handling yapıyoruz. */
        if (v.getId() == R.id.btnArti || v.getId() == R.id.btnEksi || v.getId() == R.id.btnCarpi || v.getId() == R.id.btnBolu ||
                v.getId() == R.id.btnArtiEksi || v.getId() == R.id.btnNokta) {
            if (!TextUtils.isEmpty(screen)) {
                String splitter[] = null;
                splitter = screen.split(" ");
                if(splitter[splitter.length-1].equals("+")||
                        splitter[splitter.length-1].equals("-")||splitter[splitter.length-1].equals("*")||
                        splitter[splitter.length-1].equals("/")){/* Eğer bu tuşların öncesinde sayı yoksa hata veriyoruz. */
                    Toast toast = Toast.makeText(getApplicationContext(), "Giriş Hatası", Toast.LENGTH_LONG);
                    toast.show();
                }
                else{/* sayı varsa boşluğa göre split ettiğimiz için karakterleri boşluklu olacak şekilde ekrana yazıyoruz (.hariç)*/
                    switch (v.getId()) {
                        case R.id.btnArti:
                            screen += " + ";
                            break;
                        case R.id.btnEksi:
                            screen += " - ";
                            break;
                        case R.id.btnBolu:
                            screen += " / ";
                            break;
                        case R.id.btnCarpi:
                            screen += " * ";
                            break;
                        case R.id.btnArtiEksi:
                            screen += " +/- ";
                            break;
                        case R.id.btnNokta:
                            screen += ".";
                            break;
                    }
                }
            }
        } else { /* Rakamları ise direkt ekrana yazıyoruz. */
            switch (v.getId()) {
                case R.id.btn0:
                    screen += "0";
                    break;
                case R.id.btn1:
                    screen += "1";
                    break;
                case R.id.btn2:
                    screen += "2";
                    break;
                case R.id.btn3:
                    screen += "3";
                    break;
                case R.id.btn4:
                    screen += "4";
                    break;
                case R.id.btn5:
                    screen += "5";
                    break;
                case R.id.btn6:
                    screen += "6";
                    break;
                case R.id.btn7:
                    screen += "7";
                    break;
                case R.id.btn8:
                    screen += "8";
                    break;
                case R.id.btn9:
                    screen += "9";
                    break;
            }
        }
        editText.setText(screen); /* Sonuç olarak screen değişkenini talimatlara göre düzenleyip yeniden ekrana yazdık. */
    }
}