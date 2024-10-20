package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MainActivity extends AppCompatActivity {

    TextView visor;
    double valor1 = 0, valor2 = 0;
    String operador = null;
    Button virgula, apagar, limpar, igual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        visor = findViewById(R.id.textViewVisor);
        virgula = findViewById(R.id.btnvirgula);
        apagar = findViewById(R.id.btnapagar);
        limpar = findViewById(R.id.btnlimpar);
        igual = findViewById(R.id.btnigual);
        findViewById(R.id.btnsoma).setOnClickListener(v -> adicionarOperador("+"));
        findViewById(R.id.btnsubtracao).setOnClickListener(v -> adicionarOperador("-"));
        findViewById(R.id.btnmultiplicacao).setOnClickListener(v -> adicionarOperador("*"));
        findViewById(R.id.btndivisao).setOnClickListener(v -> adicionarOperador("/"));

        for (int i = 0; i <= 9; i++) {
            int btnId = getResources().getIdentifier("btn" + i, "id", getPackageName());
            Button btn = findViewById(btnId);
            final int numero = i;
            btn.setOnClickListener(v -> visor.append(String.valueOf(numero)));
        }

        virgula.setOnClickListener(v -> {
            String exibicao = visor.getText().toString();
            if (!exibicao.isEmpty() && !exibicao.endsWith(" ") && !exibicao.contains(",")) {
                visor.append(",");
            }
        });

        apagar.setOnClickListener(v -> {
            String exibicao = visor.getText().toString();
            if (!exibicao.isEmpty()) {
                visor.setText(exibicao.substring(0, exibicao.length() - 1));
            }
        });

        limpar.setOnClickListener(v -> {
            visor.setText("");
            valor1 = 0;
            valor2 = 0;
            operador = null;
        });

        igual.setOnClickListener(v -> {
            String[] elementos = visor.getText().toString().split(" ");
            if (elementos.length < 3) return;

            try {
                valor1 = Double.parseDouble(elementos[0].replace(",", "."));
                operador = elementos[1];
                valor2 = Double.parseDouble(elementos[2].replace(",", "."));
            } catch (NumberFormatException e) {
                visor.setText("Erro");
                return;
            }

            double resultado = 0;
            switch (operador) {
                case "+":
                    resultado = valor1 + valor2;
                    break;
                case "-":
                    resultado = valor1 - valor2;
                    break;
                case "*":
                    resultado = valor1 * valor2;
                    break;
                case "/":
                    if (valor2 != 0) {
                        resultado = valor1 / valor2;
                    } else {
                        visor.setText("Não é possível dividir por zero");
                        return;
                    }
                    break;
            }

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator(',');
            DecimalFormat df = new DecimalFormat("#,###.##", symbols);
            visor.setText(df.format(resultado).replace('.', ','));
        });
    }

    private void adicionarOperador(String op) {
        String exibicao = visor.getText().toString();
        if (!exibicao.isEmpty() && !exibicao.endsWith(" ")) {
            visor.append(" " + op + " ");
        }
    }
}
