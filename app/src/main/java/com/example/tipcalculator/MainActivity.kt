package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    DiscountCalculatorApp()
                }
            }
        }
    }
}

@Composable
fun DiscountCalculatorApp() {
    var billamount by remember { mutableStateOf("") }
    val amount = billamount.toDoubleOrNull() ?: 0.0
    var discount by remember { mutableStateOf("") }
    val discRate = discount.toDoubleOrNull() ?: 0.0
    var roundUp by remember { mutableStateOf(false)}
    var discAmount = calculate(amount, discRate,roundUp)
    Column(
        modifier = Modifier
            .padding(32.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.calculate_discount),
            color = Color(0xFF3ddc84),
            fontSize = 30.sp,
            modifier = Modifier.align(
                Alignment.CenterHorizontally
            )
        )
        Spacer(modifier = Modifier.height(25.dp))
        EditNumber(
            billamount,
            discount,
            onValueChange1 = { billamount = it },
            onValueChange2 = { discount = it })
        Spacer(modifier = Modifier.height(10.dp))
        RoundUpRow(roundUp = roundUp, onRoundUpChanged = { roundUp = it })
        Text(
            text = stringResource(id = R.string.discount_amount, discAmount),
            color = Color(0xFF3ddc84),
            fontSize = 30.sp,
            modifier = Modifier.align(
                Alignment.CenterHorizontally
            )
        )
        Spacer(modifier = Modifier.height(25.dp))

    }
}

@Composable
fun EditNumber(billAmount:String,disc:String,onValueChange1:(String)->Unit,onValueChange2:(String)->Unit){

    TextField(value=billAmount ,
        onValueChange = onValueChange1,
        label={
            Text(
                color=Color.LightGray,
                text= stringResource(id =R.string.bill_amount),
                modifier=Modifier.fillMaxWidth() )
        },
        textStyle= TextStyle(color=Color.White),
        modifier= Modifier
            .padding(start = 10.dp)

            .border(
                color = Color(0xFF3ddc84), width = 4.dp, shape = RoundedCornerShape(8.dp)
            ),

        keyboardOptions=KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine=true,
    )
    Spacer(modifier = Modifier.height(10.dp))
    TextField(value=disc ,
        onValueChange = onValueChange2,
        label={
            Text(
                color=Color.LightGray,
                text= stringResource(id =R.string.discount_rate),
                modifier=Modifier.fillMaxWidth() )
        },
        textStyle= TextStyle(color=Color.White),
        modifier= Modifier
            .padding(start = 10.dp)
            .border(
                color = Color(0xFF3ddc84), width = 4.dp, shape = RoundedCornerShape(8.dp)
            ),
        //color=Color(0xFF3ddc84),
        keyboardOptions=KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine=true,

    )
}
@Composable
fun RoundUpRow(
    roundUp : Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.round_up),
            modifier=Modifier.padding(start=10.dp),color=Color(0xFF3ddc84),
            fontSize=26.sp
        )
        Switch(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            checked = roundUp,
            onCheckedChange = onRoundUpChanged,
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = Color.Gray
            )
        )
    }
}
private fun calculate(amount:Double,discRate: Double,roundUp:Boolean):String{
    var discAmount=(100-discRate)/100 *amount
    if (discAmount<=0) {
        discAmount=0.0
    }
    if(roundUp) discAmount=kotlin.math.ceil(discAmount)
    return NumberFormat.getCurrencyInstance().format(discAmount)

}


