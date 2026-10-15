package com.seminario2026.appseminario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seminario2026.appseminario.data.Deposito
import com.seminario2026.appseminario.data.Rubro
import com.seminario2026.appseminario.ui.theme.AppSeminarioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppSeminarioTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MaestroScreen(
                        modifier = Modifier.padding(innerPadding),
                        onNavegarAlDetalle = { idDeposito, idRubro ->
                            // Lógica de navegación al inventario detallado con idDeposito e idRubro
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MaestroScreen(
    modifier: Modifier = Modifier,
    onNavegarAlDetalle: (Int, Int) -> Unit
) {
    var depositoSeleccionado by remember { mutableStateOf<Deposito?>(null) }
    var rubroSeleccionado by remember { mutableStateOf<Rubro?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Seleccione Depósito",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        DepositoDropdown(onSelect = { depositoSeleccionado = it })

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Seleccione Rubro",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        RubrosSelector(onSelect = { rubroSeleccionado = it })

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                depositoSeleccionado?.let { dep ->
                    rubroSeleccionado?.let { rub ->
                        onNavegarAlDetalle(dep.id, rub.id)
                    }
                }
            },
            enabled = depositoSeleccionado != null && rubroSeleccionado != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver Inventario Detallado")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositoDropdown(
    modifier: Modifier = Modifier,
    onSelect: (Deposito) -> Unit
) {
    val depositos = remember {
        listOf(
            Deposito(1, "Depósito Central", "DEP-001", "Ana López", "Buenos Aires"),
            Deposito(2, "Depósito Norte", "DEP-002", "Carlos Ruiz", "Córdoba"),
            Deposito(3, "Depósito Sur", "DEP-003", "María Gomez", "Rosario")
        )
    }

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true)
                .fillMaxWidth(),
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            label = { Text("Depósito") },
            placeholder = { Text("Seleccione un depósito") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            depositos.forEach { deposito ->
                DropdownMenuItem(
                    text = { Text("${deposito.nombre} (${deposito.localidad})") },
                    onClick = {
                        selectedText = deposito.nombre
                        expanded = false
                        onSelect(deposito)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Composable
fun RubrosSelector(
    modifier: Modifier = Modifier,
    onSelect: (Rubro) -> Unit
) {
    val rubros = remember {
        listOf(
            Rubro(1, "Electrónica"),
            Rubro(2, "Alimentos y Bebidas"),
            Rubro(3, "Ferretería"),
            Rubro(4, "Indumentaria")
        )
    }
    var selectedRubroId by remember { mutableStateOf<Int?>(null) }

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(rubros) { rubro ->
            FilterChip(
                selected = selectedRubroId == rubro.id,
                onClick = {
                    selectedRubroId = rubro.id
                    onSelect(rubro)
                },
                label = { Text(rubro.descripcion) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MaestroScreenPreview() {
    AppSeminarioTheme {
        MaestroScreen(onNavegarAlDetalle = { _, _ -> })
    }
}

