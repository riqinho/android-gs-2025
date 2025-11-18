package riqinho.com.github.androidgs.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.math.pow

@Composable
fun CalculadoraScreen (modifier: Modifier = Modifier, navController: NavController){
    var name = remember { mutableStateOf("") }
    var peso = remember { mutableStateOf("") }
    var altura = remember { mutableStateOf("") }
    var imc = remember { mutableStateOf(0.0) }
    var statusImc = remember { mutableStateOf("") }
    var showEmptyFieldsError by remember { mutableStateOf(false) }
    var showResultCard by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxSize()
    ){
        Column(
            modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Cálculadora de IMC",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            // para os campos em branco
            if (showEmptyFieldsError) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Erro",
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Preencha todos os campos para continuar.",
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            fontSize = 14.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // campo de nome
            Text(
                text = "Seu nome",
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Ex.: Maria") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            // campo de peso
            Text(
                text = "Seu peso (kg)",
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            OutlinedTextField(
                value = peso.value,
                onValueChange = { peso.value = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Ex.: 72.5") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // campo de altura
            Text(
                text = "Sua altura (cm)",
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            OutlinedTextField(
                value = altura.value,
                onValueChange = { altura.value = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Ex.: 170") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                keyboardType =
                    KeyboardType.Decimal
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // botão de calcular
            Button(
                onClick = {
                    val pesoValue = peso.value.toDoubleOrNull()
                    val alturaValue = altura.value.toDoubleOrNull()

                    if (name.value.isNotBlank() && pesoValue != null && alturaValue != null) {
                        imc.value = calcularImc(alturaValue, pesoValue)
                        statusImc.value = determinarClassificacaoIMC(imc.value)
                        showEmptyFieldsError = false
                        showResultCard = true
                    } else {
                        showEmptyFieldsError = true
                        showResultCard = false
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
            ) {
                Text(
                    text = "Calcular",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // botão de voltar
            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    Color.Gray.copy(alpha = 0.5f)
                )
            ) {
                Text(
                    text = "Voltar",
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // card de resultado
            if (showResultCard) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xff329F6B)),
                    elevation = CardDefaults.cardElevation(4.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "${name.value}, seu resultado é:",
                            color = Color.White,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = statusImc.value,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = String.format("%.1f", imc.value),
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 36.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
    }
}

fun calcularImc(altura: Double, peso: Double): Double {
    return peso / (altura / 100).pow(2.0)
}

fun determinarClassificacaoIMC(imc: Double): String {
    return if(imc < 18.5) {
        "Abaixo do peso"
    } else if (imc >= 18.5 && imc < 25.0) {
        "Peso Ideal"
    } else if (imc >= 25.0 && imc < 30.0) {
        "Levemente acima do peso"
    } else if (imc >= 30.0 && imc < 35.0) {
        "Obesidade Grau I"
    } else if (imc >= 35.0 && imc < 40.0) {
        "Obesidade Grau II"
    } else {"Obesidade Grau III"}
}
