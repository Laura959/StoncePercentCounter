package lt.vtmc.stoncepercentcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText inputText;
    private SeekBar seekBar;
    private TextView tip2;
    private TextView procentai;
    private TextView total2;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.procentu_skaiciuokle);

        inputText = findViewById(R.id.inputText);
        seekBar = findViewById(R.id.seekBar);
        tip2 = findViewById(R.id.tip2);
        procentai = findViewById(R.id.procentai);
        total2 = findViewById(R.id.total2);
        result = findViewById(R.id.result);
        inputText.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(5, 2)});



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {

            public void onStopTrackingTouch(SeekBar bar)
            {
                int value = bar.getProgress();
            }

            public void onStartTrackingTouch(SeekBar bar)
            {
            }

            public void onProgressChanged(SeekBar bar,
                                          int paramInt, boolean paramBoolean) {
                procentai.setText("" + paramInt + "%"); // here in textView the percent will be shown
                tip2.setText(String.valueOf(paramInt));
                int skaicius = paramInt;
                try {
                    String numb = nuolaida(skaicius);
                    String total = sumaSuNuolaida(skaicius);
                    tip2.setText(String.valueOf(numb));
                    total2.setText(String.valueOf(total));

                    result.setText("Įvesta suma: " + inputText.getText().toString() + " \n Pritaikyta nuolaida: " + skaicius + "%  \n Suma su nuolaida: " + total);
                } catch (NumberFormatException ignored) {
                    System.out.println("Could not parse ");
                    tip2.setText(String.valueOf(" "));
                    inputText.setHintTextColor(Color.RED);
                }
                }



            private String nuolaida(int skaicius) {
                int discount = skaicius;
                DecimalFormat df = new DecimalFormat("#####.##");
                String b = inputText.getText().toString();
                double number = Double.parseDouble(b);
                double nuolaidosSuma = (skaicius*number)/100;
                String nuolaidosSuma2 = df.format(nuolaidosSuma);
                return nuolaidosSuma2;
            }

            private String sumaSuNuolaida(int skaicius) {  //skaicus paimamas is seekbar - nuolaida
                String nuolaida1 = nuolaida(skaicius);
                DecimalFormat df = new DecimalFormat("#####.##");
                double number = Double.parseDouble(nuolaida1);
                String b = inputText.getText().toString();
                double numberb = Double.parseDouble(b);
                double suma = numberb - number;
                String suma2 = df.format(suma);
                return suma2;
            }

        });




    }
}

class DecimalDigitsInputFilter implements InputFilter {   //leis ivesti nedaugiau kaip du skaicius po kablelio
    private Pattern mPattern;

    DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
        mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero + 2) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher matcher = mPattern.matcher(dest);
        if (!matcher.matches())
            return "";
        return null;
    }
}