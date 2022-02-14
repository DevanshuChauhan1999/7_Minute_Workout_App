package eu.tutorials.a7_minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import eu.tutorials.a7_minutesworkoutapp.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class ActivityBMI : AppCompatActivity() {

    private var binding: ActivityBmiBinding?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarBmiActivity)

        if (supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Calculate BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener(){
            onBackPressed()
        }
        binding?.btnCalculateUnits?.setOnClickListener {
            if (validateMatricUnits()){
                val heightValue : Float = binding?.etMatricUnitHeight?.text.toString().toFloat()/100
                val weightValue : Float = binding?.etMatricUnitWeight?.text.toString().toFloat()
                val bmi= weightValue / (heightValue*heightValue)

                displayBMIResult(bmi)

            }else
            {
                Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateMatricUnits(): Boolean{
        var isValid = true
        if (binding?.etMatricUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }else if (binding?.etMatricUnitHeight?.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }

    private fun displayBMIResult(bmi: Float){

        val bmiLabel : String
        val bmiDescription : String
        if (bmi.compareTo(15f)<=0){
            bmiLabel = "Very Severely Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself!"
        }else if (bmi.compareTo(15f)>0 && bmi.compareTo(16f)<=0){
            bmiLabel = "Severely Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself!"
        }
        else if (bmi.compareTo(16f)>0 && bmi.compareTo(18.5f)<=0){
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself!"
        }
        else if (bmi.compareTo(18.5f)>0 && bmi.compareTo(25f)<=0){
            bmiLabel = "Normal"
            bmiDescription = "Congratulation! You are in a good shape!"
        }
        else if (bmi.compareTo(25f)>0 && bmi.compareTo(30f)<=0){
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take better care of yourself!"
        }
        else if (bmi.compareTo(30f)>0 && bmi.compareTo(35f)<=0){
            bmiLabel = "Obese Class | (Moderately Obese)"
            bmiDescription = "Oops! You really need to take better care of yourself!"
        }
        else if (bmi.compareTo(35f)>0 && bmi.compareTo(40f)<=0){
            bmiLabel = "Obese Class | (Severely Obese)"
            bmiDescription = "Oops! You really need to take better care of yourself!"
        }
        else{
            bmiLabel = "Obese Class | (Very Severely Obese)"
            bmiDescription = "Oops! You really need to take better care of yourself!"
        }
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()


        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription
    }



}