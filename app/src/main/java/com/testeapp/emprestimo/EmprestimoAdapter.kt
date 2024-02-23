package com.testeapp.emprestimo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EmprestimoAdapter(private val emprestimos: List<Emprestimos>) :
    RecyclerView.Adapter<EmprestimoAdapter.EmprestimoViewHolder>() {

    class EmprestimoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeClienteTextView: TextView = itemView.findViewById(R.id.nomeClienteTextView)
        val cpfTextView: TextView = itemView.findViewById(R.id.cpfTextView)
        val valorTextView: TextView = itemView.findViewById(R.id.valorTextView)
        val vencimentoTextView: TextView = itemView.findViewById(R.id.vencimentoTextView)
        val jurosTextView: TextView = itemView.findViewById(R.id.jurosTextView)
        val timestampTextView: TextView = itemView.findViewById(R.id.timestampTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmprestimoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_emprestimo, parent, false)
        return EmprestimoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EmprestimoViewHolder, position: Int) {
        val emprestimo = emprestimos[position]
        holder.nomeClienteTextView.text = "Nome: " + emprestimo.nomeCliente
        holder.cpfTextView.text = "CPF: " + emprestimo.cpf
        holder.valorTextView.text = "Valor: " + emprestimo.valor
        holder.vencimentoTextView.text = "Vencimento: " + emprestimo.vencimento
        holder.jurosTextView.text = "Juros: " + emprestimo.juros
        holder.timestampTextView.text = "Lançado em : " + emprestimo.timestamp

        // Verificar se a data de vencimento está atrasada
        val hoje = Calendar.getInstance()
        val dataVencimento = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(emprestimo.vencimento)
        if (dataVencimento != null) {
            if (dataVencimento.before(hoje.time)) {
                // A data de vencimento está atrasada, definir a cor do texto como vermelho
                holder.vencimentoTextView.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.dark_red))
            } else {
                // A data de vencimento não está atrasada, definir a cor do texto como preto
                holder.vencimentoTextView.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            }
        }
    }


    override fun getItemCount(): Int {
        return emprestimos.size
    }
}
