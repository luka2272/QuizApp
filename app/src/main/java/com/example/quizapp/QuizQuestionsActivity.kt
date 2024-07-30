package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener{

    private var cp = 1
    private var pressed = 0
    private var questionsList:ArrayList<Question>? = null
    private var selOptPos = 0
    private var userName : String? = null
    private var ca : Int = 0

    private var progressBar : ProgressBar? = null
    private var tvProgress : TextView? = null
    private var tvQuestion : TextView? = null
    private var ivImage : ImageView? = null

    private var tvOptionone : TextView? = null
    private var tvOptiontwo : TextView? = null
    private var tvOptionthree : TextView? = null
    private var tvOptionfour : TextView? = null

    private var btnSubmit : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz_questions)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userName = intent.getStringExtra(Constants.USER_NAME)
        progressBar = findViewById(R.id.progress_bar)
        tvProgress = findViewById(R.id.tv_progress)
        tvQuestion = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.iv_image)

        tvOptionone = findViewById(R.id.tv_option_one)
        tvOptiontwo = findViewById(R.id.tv_option_two)
        tvOptionthree = findViewById(R.id.tv_option_three)
        tvOptionfour = findViewById(R.id.tv_option_four)
        btnSubmit = findViewById(R.id.bt_sub)
        questionsList = Constants.getQuestions()

        tvOptionone?.setOnClickListener(this)
        tvOptiontwo?.setOnClickListener(this)
        tvOptionthree?.setOnClickListener(this)
        tvOptionfour?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)

        setQuestions()

    }

    private fun setQuestions() {
        defaultOptionsView()
        val question: Question = questionsList!![cp - 1]
        ivImage?.setImageResource(question.image)
        progressBar?.progress = cp
        tvProgress?.text = "$cp/${progressBar?.max}"
        tvQuestion?.text = question.question
        tvOptionone?.text = question.optionOne
        tvOptiontwo?.text = question.optionTwo
        tvOptionthree?.text = question.optionThree
        tvOptionfour?.text = question.optionFour

        if (cp == questionsList!!.size){
            btnSubmit?.text = "Finish"
        } else btnSubmit?.text = "Submit"
    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        tvOptionone?.let {
            options.add(0, it)
        }
        tvOptiontwo?.let {
            options.add(1, it)
        }
        tvOptionthree?.let {
            options.add(2, it)
        }
        tvOptionfour?.let {
            options.add(3, it)
        }
        for(option in options){
            option.setBackgroundResource(R.color.bg)
        }
    }

    private fun selectedOptionView(tv:TextView, selOptNum:Int){
        defaultOptionsView()
        selOptPos = selOptNum

        // Resolve the theme attribute to a color
        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.colorPressedHighlight, typedValue, true)
        val color = typedValue.data

        // Set the background color of a view
        tv.setBackgroundColor(color)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tv_option_one -> {
                tvOptionone?.let {
                    selectedOptionView(it, 1)
                }
            }
            R.id.tv_option_two -> {
                tvOptiontwo?.let {
                    selectedOptionView(it, 2)
                }
            }
            R.id.tv_option_three -> {
                tvOptionthree?.let {
                    selectedOptionView(it, 3)
                }
            }
            R.id.tv_option_four -> {
                tvOptionfour?.let {
                    selectedOptionView(it, 4)
                }
            }
            R.id.bt_sub -> {
                if(selOptPos == 0){
                    if(pressed == 1){
                        pressed = 0
                        cp++
                        if (cp <= questionsList!!.size){
                            setQuestions()
                        }else{
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, userName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, ca)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, questionsList!!.size)
                            startActivity(intent)
                            finish()
                        }
                    }else Toast.makeText(this, "Please select an answer before submitting", Toast.LENGTH_SHORT).show()
                }else{
                    val question = questionsList?.get(cp-1)
                    if(question!!.correctAnswer != selOptPos){
                        answerView(selOptPos, com.google.android.material.R.attr.colorSurfaceDim)
                    }else ca++
                    answerView(question.correctAnswer, com.google.android.material.R.attr.colorSurfaceBright)

                    if(cp==questionsList!!.size){
                        btnSubmit?.text = "Finish"
                    }else{
                        btnSubmit?.text = "Go to the next question"
                    }
                    pressed = 1
                    selOptPos = 0
                }
            }
        }
    }

    private fun answerView(answer:Int, col:Int){
        when(answer){
            1 -> {
                // Resolve the theme attribute to a color
                val typedValue = TypedValue()
                theme.resolveAttribute(col, typedValue, true)
                val color = typedValue.data

                // Set the background color of a view
                tvOptionone?.setBackgroundColor(color)
            }
            2 -> {
                // Resolve the theme attribute to a color
                val typedValue = TypedValue()
                theme.resolveAttribute(col, typedValue, true)
                val color = typedValue.data

                // Set the background color of a view
                tvOptiontwo?.setBackgroundColor(color)
            }
            3 -> {
                // Resolve the theme attribute to a color
                val typedValue = TypedValue()
                theme.resolveAttribute(col, typedValue, true)
                val color = typedValue.data

                // Set the background color of a view
                tvOptionthree?.setBackgroundColor(color)
            }
            4 -> {
                // Resolve the theme attribute to a color
                val typedValue = TypedValue()
                theme.resolveAttribute(col, typedValue, true)
                val color = typedValue.data

                // Set the background color of a view
                tvOptionfour?.setBackgroundColor(color)
            }
        }
    }

}