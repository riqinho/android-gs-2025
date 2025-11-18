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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LoginScreen (modifier: Modifier = Modifier, navController: NavController){
    var user = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var showErrorCard by remember { mutableStateOf(false) }
    var showEmptyFieldsError by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxSize()
    ){
        Column(
            modifier.padding(24.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Login",
                fontSize = 24.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
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

            // para login incorreto
            if(showErrorCard){
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
                            text = "Usuário ou senha incorretos.",
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            fontSize = 14.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // campo usuário
            Text(
                text = "Usuário",
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
            OutlinedTextField(
                value = user.value,
                onValueChange = { user.value = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Ex.: admin")
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            // campo senha
            Text(
                text = "Senha",
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
            OutlinedTextField(
                value = password.value,
                onValueChange = {password.value = it},
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Ex.: 123456")
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            // botão de entrar
            Button(
                onClick = {
                    if (user.value.isBlank() || password.value.isBlank()) {
                        showEmptyFieldsError = true
                        showErrorCard = false
                    }
                    if (user.value.toString() == "admin" && password.value.toString() == "123456"){
                        navController.navigate("menu")
                        showErrorCard = false
                        showEmptyFieldsError = false
                    } else {
                        showErrorCard = true
                        showEmptyFieldsError = false
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
            ) {
                Text(
                    text = "Entrar",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }
        }
    }
}
