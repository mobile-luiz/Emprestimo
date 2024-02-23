package com.testeapp.emprestimo

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.testeapp.emprestimo.databinding.ActivityEmprestimoBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class Emprestimo : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ActivityEmprestimoBinding
    private lateinit var nomeClienteEditText: EditText
    private lateinit var cpfEditText: EditText
    private lateinit var valorEditText: EditText
    private lateinit var vencimentoEditText: EditText
    private lateinit var jurosEditText: EditText
    private lateinit var fazerEmprestimoButton: Button
    private lateinit var cancelarButton: Button
    private lateinit var progressDialog: ProgressDialog

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private lateinit var calendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmprestimoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar o Firebase Database
        database = FirebaseDatabase.getInstance().reference

        // Inicializar o Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Referenciar as views
        nomeClienteEditText = binding.editTextNomeCliente
        cpfEditText = binding.editTextCpf
        valorEditText = binding.editTextValor
        vencimentoEditText = binding.editTextVencimento
        jurosEditText = binding.editTextJuros
        fazerEmprestimoButton = binding.buttonFazerEmprestimo
        cancelarButton = binding.buttonCancelar

        // Configurar o ProgressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Autenticando...")
        progressDialog.setCancelable(false)

        // Configurar o OnClickListener para o botão "Fazer Empréstimo"
        fazerEmprestimoButton.setOnClickListener {
            if (validarCampos()) {
                progressDialog.show() // Mostrar ProgressDialog antes de salvar os dados
                salvarDadosFirebase()
            }
        }

        // Inicializar o EditText de vencimento e o objeto Calendar
        calendar = Calendar.getInstance()

        // Definir um OnClickListener para o EditText de vencimento
        vencimentoEditText.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Criar e exibir um DatePickerDialog
            val datePickerDialog = DatePickerDialog(this, this, year, month, day)
            datePickerDialog.show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // Atualizar o objeto Calendar com a data selecionada
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        // Formatando a data selecionada
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val selectedDate: String = sdf.format(calendar.time)

        // Exibir a data formatada no EditText de vencimento
        vencimentoEditText.setText(selectedDate)
    }

    private fun limparCampos() {
        nomeClienteEditText.text.clear()
        cpfEditText.text.clear()
        valorEditText.text.clear()
        vencimentoEditText.text.clear()
        jurosEditText.text.clear()
    }

    private fun validarCampos(): Boolean {
        val nomeCliente = nomeClienteEditText.text.toString().trim()
        val cpf = cpfEditText.text.toString().trim()
        val valor = valorEditText.text.toString().trim()
        val vencimento = vencimentoEditText.text.toString().trim()
        val juros = jurosEditText.text.toString().trim()

        if (nomeCliente.isEmpty() || cpf.isEmpty() || valor.isEmpty() || vencimento.isEmpty() || juros.isEmpty()) {
            // Se algum campo estiver vazio, exibir uma mensagem de erro e retornar falso
            Toast.makeText(this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun salvarDadosFirebase() {
        // Obter o UID do usuário atualmente autenticado
        val currentUser = auth.currentUser
        val uid = currentUser?.uid

        // Obter os valores dos campos de texto
        val nomeCliente = nomeClienteEditText.text.toString()
        val cpf = cpfEditText.text.toString()
        val valor = valorEditText.text.toString().toDouble()
        val vencimento = vencimentoEditText.text.toString()
        val juros = jurosEditText.text.toString().toDouble()

        // Obter a data e hora atuais
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val currentDateAndTime: String = sdf.format(Date())

        // Formatando o valor para reais (R$)
        val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        val valorFormatado = format.format(valor)

        // Formatando os juros para percentual (%)
        val jurosFormatado = "$juros%"

        // Criar um objeto para representar os dados do empréstimo
        val emprestimo = mapOf(
            "uid" to uid,
            "nomeCliente" to nomeCliente,
            "cpf" to cpf,
            "valor" to valorFormatado, // Usando o valor formatado em reais (R$)
            "vencimento" to vencimento,
            "juros" to jurosFormatado, // Usando os juros formatados em percentual (%)
            "timestamp" to currentDateAndTime
        )

        // Salvar os dados no Firebase Database
        database.child("emprestimos").push().setValue(emprestimo)
            .addOnSuccessListener {
                // Dados salvos com sucesso
                progressDialog.dismiss() // Esconder ProgressDialog após o sucesso
                Toast.makeText(this, "Empréstimo feito com sucesso!", Toast.LENGTH_SHORT).show()
                limparCampos() // Limpar os campos após o sucesso
            }
            .addOnFailureListener {
                // Falha ao salvar os dados
                progressDialog.dismiss() // Esconder ProgressDialog em caso de falha
                Toast.makeText(this, "Erro ao fazer o empréstimo!", Toast.LENGTH_SHORT).show()
            }
    }

}
