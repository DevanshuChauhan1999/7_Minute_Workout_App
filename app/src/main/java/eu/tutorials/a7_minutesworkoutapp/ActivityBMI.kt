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
    companion object{
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }

    private var binding: ActivityBmiBinding?= null
    private var currentVisibleView: String= METRIC_UNITS_VIEW


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

        makeVisibleMetricUnitsView()

        //make textview visible and invisible while radio button is clicked
        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId : Int ->
            if (checkedId == R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }else{
                makeVisibleUSUnitsView()
            }
        }

        //on click action for the calculating  button
        binding?.btnCalculateUnits?.setOnClickListener {
            calculateUnits()
        }
    }

    //for the us and metric calculation
    private fun calculateUnits(){
        if (currentVisibleView == METRIC_UNITS_VIEW){ //checking for METRIC RADIO button is selected
            if (validateMatricUnits()){
                val heightValue : Float = binding?.etMatricUnitHeight?.text.toString().toFloat()/100
                val weightValue : Float = binding?.etMatricUnitWeight?.text.toString().toFloat()
                val bmi= weightValue / (heightValue*heightValue)

                displayBMIResult(bmi)

            }else
            {
                Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
            }
        }else{
            if (validateUSUnits()){
                val usUnitHeightValueFeet: String = binding?.etMatricUnitHeightFeet?.text.toString()
                val usUnitHeightValueInch: String = binding?.etMatricUnitHeightInch?.text.toString()
                val usUnitWeightValue : Float = binding?.etUSMetricUnitWeight?.text.toString().toFloat()

                val heightValue = usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12

                val bmi = 703 * (usUnitWeightValue/(heightValue * heightValue))

                displayBMIResult(bmi)
            }else
            {
                Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //for checking is the field are not empty for metric system
    private fun validateMatricUnits(): Boolean{
        var isValid = true
        if (binding?.etMatricUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }else if (binding?.etMatricUnitHeight?.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }


    //for checking is the field is not empty for the US system
    private fun validateUSUnits():Boolean {
        var isValid = true
        when{
            binding?.etUSMetricUnitWeight?.text.toString().isEmpty()->
            {
                isValid = false
            }
            binding?.etMatricUnitHeightFeet?.text.toString().isEmpty()->{
                isValid=false
            }
            binding?.etMatricUnitHeightInch?.text.toString().isEmpty()->{
                isValid = false
            }
        }
        return isValid
    }


    //for displaying the result according to BMI result
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

    //make the US textview invisible while the Metric system is visible
    private fun makeVisibleMetricUnitsView(){
        currentVisibleView = METRIC_UNITS_VIEW

        binding?.tiMetricUnitHeight?.visibility = View.VISIBLE
        binding?.tiMetricUnitWeight?.visibility = View.VISIBLE

        binding?.tiUSMetricUnitWeight?.visibility = View.GONE
        binding?.tiMetricUnitHeightFeet?.visibility = View.GONE
        binding?.tiMetricUnitHeightInch?.visibility = View.GONE

        binding?.etMatricUnitHeight?.text!!.clear()
        binding?.etMatricUnitWeight?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }


    //make the metric system textview invisible while US system text view is visible
    private fun makeVisibleUSUnitsView(){
        currentVisibleView = US_UNITS_VIEW

        binding?.tiMetricUnitHeight?.visibility = View.INVISIBLE
        binding?.tiMetricUnitWeight?.visibility = View.INVISIBLE

        binding?.tiUSMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tiMetricUnitHeightFeet?.visibility = View.VISIBLE
        binding?.tiMetricUnitHeightInch?.visibility = View.VISIBLE

        binding?.etUSMetricUnitWeight?.text!!.clear()
        binding?.etMatricUnitHeightFeet?.text!!.clear()
        binding?.etMatricUnitHeightInch?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }


}