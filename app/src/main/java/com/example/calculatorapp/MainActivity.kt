package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private lateinit var expressionTextView: TextView
    private lateinit var resultTextView: TextView
    private lateinit var oneButton:Button
    private lateinit var twoButton:Button
    private lateinit var threeButton:Button
    private lateinit var fourButton:Button
    private lateinit var fiveButton:Button
    private lateinit var sixButton:Button
    private lateinit var sevenButton:Button
    private lateinit var eightButton:Button
    private lateinit var nineButton:Button
    private lateinit var zeroButton:Button
    private lateinit var dotButton:Button
    private lateinit var addButton:Button
    private lateinit var subButton:Button
    private lateinit var multiButton:Button
    private lateinit var divButton:Button
    private lateinit var changeSignButton:Button
    private lateinit var clearButton:Button
    private lateinit var backButton:Button
    private lateinit var resultButton:Button
    private var divideByZeroException = "DivideByZeroException"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        expressionTextView = findViewById(R.id.expression_text_view)
        resultTextView = findViewById(R.id.result_text_view)
        oneButton = findViewById(R.id.one_button)
        twoButton = findViewById(R.id.two_button)
        threeButton = findViewById(R.id.three_button)
        fourButton = findViewById(R.id.four_button)
        fiveButton = findViewById(R.id.five_button)
        sixButton = findViewById(R.id.six_button)
        sevenButton = findViewById(R.id.seven_button)
        eightButton = findViewById(R.id.eight_button)
        nineButton = findViewById(R.id.nine_button)
        zeroButton = findViewById(R.id.zero_button)
        dotButton = findViewById(R.id.dot_button)
        addButton = findViewById(R.id.add_button)
        subButton = findViewById(R.id.sub_button)
        multiButton = findViewById(R.id.multi_button)
        divButton = findViewById(R.id.div_button)
        changeSignButton = findViewById(R.id.change_sign_button)
        backButton = findViewById(R.id.back_button)
        clearButton = findViewById(R.id.clear_button)
        resultButton = findViewById(R.id.result_button)

        oneButton.setOnClickListener {
            updateExpression('1')
        }

        twoButton.setOnClickListener {
            updateExpression('2')
        }

        threeButton.setOnClickListener {
            updateExpression('3')
        }

        fourButton.setOnClickListener {
            updateExpression('4')
        }

        fiveButton.setOnClickListener {
            updateExpression('5')
        }

        sixButton.setOnClickListener {
            updateExpression('6')
        }

        sevenButton.setOnClickListener {
            updateExpression('7')
        }

        eightButton.setOnClickListener {
            updateExpression('8')
        }

        nineButton.setOnClickListener {
            updateExpression('9')
        }

        zeroButton.setOnClickListener {
            updateExpression('0')
        }

        dotButton.setOnClickListener {
            updateExpression('.')
        }

        addButton.setOnClickListener {
            updateExpression('+')
        }

        subButton.setOnClickListener {
            updateExpression('-')
        }

        multiButton.setOnClickListener {
            updateExpression('×')
        }

        divButton.setOnClickListener {
            updateExpression('÷')
        }

        changeSignButton.setOnClickListener{
            updateExpression('c')
        }

        backButton.setOnClickListener {
            removeLastChar()
        }

        clearButton.setOnClickListener {
            expressionTextView.text = ""
            resultTextView.text = ""
        }

        resultButton.setOnClickListener{
            if (resultTextView.text != divideByZeroException)
                expressionTextView.text = resultTextView.text
            else
                Toast.makeText(this, divideByZeroException, Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeLastChar(){
        if (expressionTextView.text.isNotEmpty()){
            var t: String = expressionTextView.text as String

            t = if (t.length > 1 && t[t.length - 2] == '-' && t.last().isDigit()) t.substring(0, t.length - 2)
            else if (t.last() == ' ') t.substring(0, t.length - 3)
            else t.substring(0, t.length - 1)

            expressionTextView.text = t

            doMath()
        }
    }

    private fun doMath() {
        if (expressionTextView.text.takeLast(1) != " "){
            val list: MutableList<String> = expressionTextView.text.split(" ").toList().toMutableList()

            while (list.contains("÷") || list.contains("×") || list.contains("-") || list.contains("+")){
                val i = if (list.contains("÷")) list.indexOf("÷")
                else if (list.contains("×")) list.indexOf("×")
                else if (list.contains("-")) list.indexOf("-")
                else list.indexOf("+")

                when(list[i]){
                    "÷" ->
                        if (list[i + 1].toDouble() != 0.0)
                            list[i - 1] = (list[i - 1].toDouble() / list[i + 1].toDouble()).toString()
                        else {
                            resultTextView.text = divideByZeroException
                            return
                        }
                    "×" -> list[i - 1] = (list[i - 1].toDouble() * list[i + 1].toDouble()).toString()
                    "+" -> list[i - 1] = (list[i - 1].toDouble() + list[i + 1].toDouble()).toString()
                    "-" -> list[i - 1] = (list[i - 1].toDouble() - list[i + 1].toDouble()).toString()
                }

                list.removeAt(i)
                list.removeAt(i)
            }

            resultTextView.text = list[0]
        }
    }

    private fun updateExpression(c: Char){
        var t: String = expressionTextView.text as String
        val lastNumber = t.substring(t.lastIndexOf(" ") + 1)

        if (c == '.' && ((lastNumber.contains('.') || t.isEmpty()) || t.last() == ' ' )) return
        else if (c.isDigit() || c == '.') t += c
        else if ((c == '×' || c == '÷' || c == '+' || c == '-') && t.isNotEmpty()){
            if (t.last() != ' ') t += " $c "
            else t = t.replaceRange(t.lastIndex - 1, t.lastIndex, c.toString())
        }
        else if (c == 'c' && t.isNotEmpty() && t.last() != ' '){
            t = if (t[t.lastIndexOf(" ") + 1] != '-') t.replaceRange(t.lastIndexOf(" ") + 1, t.lastIndexOf(" ") + 1, "-")
            else  t.replaceRange(t.lastIndexOf(" ") + 1, t.lastIndexOf(" ") + 2, "")
        }

        expressionTextView.text = t

        doMath()
    }

}
