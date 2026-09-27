package com.barkat.dastarkhwan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.*

private val Green = Color(0xFF0B4638)
private val GreenDark = Color(0xFF062D25)
private val Cream = Color(0xFFF7F0DF)
private val Gold = Color(0xFFD5B35D)
private val Ink = Color(0xFF17342D)

class MainActivity : ComponentActivity() { override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContent { BarkatApp() } } }

@Composable fun BarkatApp() {
    val nav = rememberNavController()
    MaterialTheme { NavHost(navController = nav, startDestination = "home") {
        composable("home") { HomeScreen { nav.navigate("quote") } }
        composable("quote") { QuoteScreen { nav.popBackStack() } }
    } }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable private fun HomeScreen(onQuote: () -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text("Barkat Dastarkhwan", fontWeight = FontWeight.Bold) }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Green, titleContentColor = Color.White)) }) { p ->
        Column(Modifier.fillMaxSize().background(Cream).verticalScroll(rememberScrollState()).padding(p).padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Card(colors = CardDefaults.cardColors(containerColor = Green), shape = RoundedCornerShape(28.dp)) {
                Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("PLANNING A GATHERING?", color = Gold, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Text("Made for Gatherings. Shared with Love.", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                    Text("Request a quote for any dish, any quantity — for Majlis, Tabarruk, functions or family/community gatherings.", color = Color.White.copy(.9f))
                    Button(onClick = onQuote, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Gold, contentColor = Ink)) { Text("📋 Request Your Catering Quote", fontWeight = FontWeight.Bold) }
                    OutlinedButton(onClick = { }, modifier = Modifier.fillMaxWidth()) { Text("💬 Chat on WhatsApp") }
                }
            }
            Text("Majlis • Tabarruk • Functions • Gatherings", color = Green, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            Feature("🍽", "Any dish", "Chicken Biryani and Chicken Korma are favourites, but you can request any dish you need.")
            Feature("⚖", "Flexible quantities", "Regular catering can be specified by weight or by portion, with multiple dishes in one request.")
            Feature("✦", "Tabarruk arrangement", "Create packs with sweets, bread, juice/drinks, food or other items. Choose pack contents and number of packs.")
            Feature("✓", "Quote first", "Prices are not fixed. Barkat reviews your request and sends a quote, with delivery separate when applicable.")
            Text("Gule Zehra Manzil, Tayyab Colony, Aligarh", color = Ink.copy(.7f), fontSize = 13.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        }
    }
}

@Composable private fun Feature(icon: String, title: String, body: String) { Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFAF0)), shape = RoundedCornerShape(22.dp)) { Row(Modifier.padding(20.dp), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.Top) { Text(icon, fontSize = 24.sp); Column { Text(title, color = Green, fontSize = 20.sp, fontWeight = FontWeight.Bold); Text(body, color = Ink.copy(.75f)) } } } }

@OptIn(ExperimentalMaterial3Api::class)
@Composable private fun QuoteScreen(onBack: () -> Unit) {
    var name by remember { mutableStateOf("") }; var phone by remember { mutableStateOf("") }; var occasion by remember { mutableStateOf("") }; var details by remember { mutableStateOf("") }
    Scaffold(topBar = { TopAppBar(title = { Text("Request a Quote") }, navigationIcon = { TextButton(onClick = onBack) { Text("←", color = Color.White, fontSize = 22.sp) } }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Green, titleContentColor = Color.White)) }) { p ->
        Column(Modifier.fillMaxSize().background(Cream).verticalScroll(rememberScrollState()).padding(p).padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Your gathering", color = Green, fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Text("Regular catering, Tabarruk, or both can be included in one request.", color = Ink.copy(.75f))
            OutlinedTextField(name, { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            OutlinedTextField(phone, { phone = it }, label = { Text("WhatsApp / Phone") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            OutlinedTextField(occasion, { occasion = it }, label = { Text("Occasion") }, modifier = Modifier.fillMaxWidth(), placeholder = { Text("Majlis / Tabarruk / Function / Other") })
            OutlinedTextField(details, { details = it }, label = { Text("What would you like?") }, modifier = Modifier.fillMaxWidth(), minLines = 7, placeholder = { Text("Example: Chicken Biryani — 10 kg; Tabarruk — 200 packs, 1 sweet + 1 juice + 1 bread per pack") })
            Button(onClick = { }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Green)) { Text("Submit Quote Request", fontWeight = FontWeight.Bold) }
            Text("No online payment. Barkat will review the request and send a quote.", color = Ink.copy(.65f), fontSize = 12.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        }
    }
}
