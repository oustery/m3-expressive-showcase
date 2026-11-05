package com.example.m3expressive.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.m3expressive.ui.components.Caption

@Composable
fun HomeScreen(onExplore: () -> Unit, onNotify: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        item {
            Card(
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 48.dp, bottomEnd = 48.dp, bottomStart = 48.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            ) {
                Box(
                    Modifier.fillMaxWidth().background(
                        Brush.linearGradient(
                            listOf(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.tertiaryContainer),
                        ),
                    ).padding(24.dp),
                ) {
                    Column {
                        Box(
                            Modifier.clip(CircleShape).background(MaterialTheme.colorScheme.primary).padding(horizontal = 12.dp, vertical = 6.dp),
                        ) {
                            Text("MATERIAL 3 · 2026", color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.labelMedium)
                        }
                        Spacer(Modifier.height(32.dp))
                        Text(
                            "Design that\nmoves you.",
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            "A hands-on playground for expressive color, shape, typography and motion.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = .78f),
                        )
                        Spacer(Modifier.height(24.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Button(onClick = onExplore, contentPadding = ButtonDefaults.ButtonWithIconContentPadding) {
                                Text("Explore")
                                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                Icon(Icons.Default.ArrowForward, null, Modifier.size(18.dp))
                            }
                            OutlinedButton(onClick = { onNotify("Welcome to the expressive side ✨") }) {
                                Icon(Icons.Default.PlayArrow, null, Modifier.size(18.dp))
                                Spacer(Modifier.size(8.dp))
                                Text("Say hello")
                            }
                        }
                    }
                    Icon(
                        Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.align(Alignment.TopEnd).padding(top = 60.dp).size(52.dp),
                    )
                }
            }
        }
        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PrincipleCard("35+", "New shapes", Icons.Default.Favorite, Modifier.weight(1f))
                PrincipleCard("Spring", "Natural motion", Icons.Default.AutoAwesome, Modifier.weight(1f))
            }
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Built to be felt", style = MaterialTheme.typography.headlineMedium)
                Text(
                    "Expressive is not a coat of paint. It is a connected system where every interaction reinforces personality.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                PrincipleRow("01", "Shape with intent", "Mix sharp corners with generous curves to create visual rhythm.")
                PrincipleRow("02", "Color with courage", "Tonal containers and vivid accents establish a clear focal point.")
                PrincipleRow("03", "Motion with meaning", "Spatial springs make interfaces responsive, physical and alive.")
            }
        }
        item {
            Caption("JETPACK COMPOSE · MATERIAL 3 1.5 ALPHA · KOTLIN")
        }
    }
}

@Composable
private fun PrincipleCard(value: String, label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier) {
    Card(modifier, shape = MaterialTheme.shapes.large, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(icon, null, tint = MaterialTheme.colorScheme.primary)
            Text(value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Black)
            Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun PrincipleRow(number: String, title: String, description: String) {
    Row(
        Modifier.fillMaxWidth().clip(MaterialTheme.shapes.medium).background(MaterialTheme.colorScheme.surfaceContainer).padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(number, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.tertiary)
        Column {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
