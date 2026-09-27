package com.barkat.dastarkhwan

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { BarkatApp() }
    }
}

@Composable
fun BarkatApp() {
    val nav = rememberNavController()
    MaterialTheme {
        NavHost(navController = nav, startDestination = "home") {
            composable("home") { HomeScreen { nav.navigate("quote") } }
            composable("quote") { QuoteScreen { nav.popBackStack() } }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(onQuote: () -> Unit) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Barkat Dastarkhwan", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Green,
                    titleContentColor = Color.White
                )
            )
        }
    ) { p ->
        Column(
            Modifier.fillMaxSize().background(Cream).verticalScroll(rememberScrollState())
                .padding(p).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(colors = CardDefaults.cardColors(containerColor = Green), shape = RoundedCornerShape(28.dp)) {
                Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("PLANNING A GATHERING?", color = Gold, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Text("Made for Gatherings. Shared with Love.", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                    Text(
                        "Request a quote for any dish, any quantity — for Majlis, Tabarruk, functions or family/community gatherings.",
                        color = Color.White.copy(.9f)
                    )
                    Button(
                        onClick = onQuote,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Gold, contentColor = Ink)
                    ) { Text("📋 Request Your Catering Quote", fontWeight = FontWeight.Bold) }
                    OutlinedButton(
                        onClick = {
                            val uri = Uri.parse("https://wa.me/918279409493?text=Assalamualaikum%2C%20I%27d%20like%20a%20catering%20quote%20from%20Barkat%20Dastarkhwan.")
                            context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("💬 Chat on WhatsApp", color = Color.White) }
                }
            }
            Text(
                "Majlis • Tabarruk • Functions • Family/Community Gatherings",
                color = Green, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Feature("🍽", "Any dish", "Chicken Biryani and Chicken Korma are favourites, but you can request any dish you need.")
            Feature("⚖", "Flexible quantities", "Regular catering can be specified by weight or by portion, with multiple dishes in one request.")
            Feature("✦", "Tabarruk arrangement", "Create packs with sweets, bread, juice/drinks, food or other items. Choose pack contents and number of packs.")
            Feature("✓", "Quote first", "Prices are not fixed. Barkat reviews your request and sends a quote, with delivery separate when applicable.")
            Text("Gule Zehra Manzil, Tayyab Colony, Aligarh", color = Ink.copy(.7f), fontSize = 13.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun Feature(icon: String, title: String, body: String) {
    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFAF0)), shape = RoundedCornerShape(22.dp)) {
        Row(Modifier.padding(20.dp), horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.Top) {
            Text(icon, fontSize = 24.sp)
            Column {
                Text(title, color = Green, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(body, color = Ink.copy(.75f))
            }
        }
    }
}

private data class CateringItem(
    val name: String,
    val quantity: String = "",
    val unit: String = "kg"
)

private data class TabarrukItem(
    val name: String,
    val perPack: String = "1"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuoteScreen(onBack: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var occasion by remember { mutableStateOf("Majlis") }
    var eventDate by remember { mutableStateOf("") }
    var eventTime by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var specialRequirements by remember { mutableStateOf("") }

    var cateringItems by remember {
        mutableStateOf(
            listOf(
                CateringItem("Chicken Biryani"),
                CateringItem("Chicken Korma")
            )
        )
    }
    var tabarrukItems by remember {
        mutableStateOf(
            listOf(
                TabarrukItem("Sweet"),
                TabarrukItem("Juice / Drink"),
                TabarrukItem("Bread")
            )
        )
    }
    var packCount by remember { mutableStateOf("200") }
    var includeTabarruk by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Request a Quote") },
                navigationIcon = { TextButton(onClick = onBack) { Text("←", color = Color.White, fontSize = 22.sp) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Green, titleContentColor = Color.White)
            )
        }
    ) { p ->
        Column(
            Modifier.fillMaxSize().background(Cream).verticalScroll(rememberScrollState())
                .padding(p).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Your gathering", color = Green, fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Text("Regular catering, Tabarruk, or both can be included in one request.", color = Ink.copy(.75f))

            SectionTitle("1. Your details")
            OutlinedTextField(name, { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            OutlinedTextField(
                phone, { phone = it }, label = { Text("WhatsApp / Phone") },
                modifier = Modifier.fillMaxWidth(), singleLine = true,
                placeholder = { Text("Example: 8279409493") }
            )
            OutlinedTextField(occasion, { occasion = it }, label = { Text("Occasion") }, modifier = Modifier.fillMaxWidth(), singleLine = true)

            SectionTitle("2. Regular catering")
            Text("Add as many dishes as you need. Each can be quoted by kg or portion.", color = Ink.copy(.7f), fontSize = 13.sp)

            cateringItems.forEachIndexed { index, item ->
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFAF0)), shape = RoundedCornerShape(18.dp)) {
                    Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            item.name,
                            { value ->
                                cateringItems = cateringItems.toMutableList().also { it[index] = item.copy(name = value) }
                            },
                            label = { Text("Dish") }, modifier = Modifier.fillMaxWidth(), singleLine = true
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                item.quantity,
                                { value ->
                                    cateringItems = cateringItems.toMutableList().also { it[index] = item.copy(quantity = value) }
                                },
                                label = { Text("Quantity") }, modifier = Modifier.weight(1f), singleLine = true
                            )
                            OutlinedTextField(
                                item.unit,
                                { value ->
                                    cateringItems = cateringItems.toMutableList().also { it[index] = item.copy(unit = value) }
                                },
                                label = { Text("Unit") }, modifier = Modifier.weight(1f), singleLine = true,
                                placeholder = { Text("kg / portion") }
                            )
                        }
                        if (cateringItems.size > 1) {
                            TextButton(onClick = { cateringItems = cateringItems.toMutableList().also { it.removeAt(index) } }) {
                                Text("Remove dish", color = Color(0xFF9B3B2E))
                            }
                        }
                    }
                }
            }
            OutlinedButton(
                onClick = { cateringItems = cateringItems + CateringItem("") },
                modifier = Modifier.fillMaxWidth()
            ) { Text("+ Add another dish") }

            SectionTitle("3. Tabarruk arrangement")
            Text(
                "Tabarruk is an arrangement, not a dish. Barkat can prepare, source and pack requested items when available.",
                color = Ink.copy(.7f), fontSize = 13.sp
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = includeTabarruk, onCheckedChange = { includeTabarruk = it })
                Text("Include Tabarruk in this request", fontWeight = FontWeight.SemiBold)
            }

            if (includeTabarruk) {
                OutlinedTextField(
                    packCount, { packCount = it },
                    label = { Text("Number of packs") },
                    modifier = Modifier.fillMaxWidth(), singleLine = true
                )
                Text("Items per pack — default is 1 of each. You can change the quantity.", color = Green, fontWeight = FontWeight.Bold)

                tabarrukItems.forEachIndexed { index, item ->
                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFAF0)), shape = RoundedCornerShape(18.dp)) {
                        Row(Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                            OutlinedTextField(
                                item.name,
                                { value ->
                                    tabarrukItems = tabarrukItems.toMutableList().also { it[index] = item.copy(name = value) }
                                },
                                label = { Text("Item") }, modifier = Modifier.weight(1f), singleLine = true
                            )
                            OutlinedTextField(
                                item.perPack,
                                { value ->
                                    tabarrukItems = tabarrukItems.toMutableList().also { it[index] = item.copy(perPack = value) }
                                },
                                label = { Text("Per pack") }, modifier = Modifier.width(105.dp), singleLine = true
                            )
                        }
                    }
                }
                OutlinedButton(
                    onClick = { tabarrukItems = tabarrukItems + TabarrukItem("") },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("+ Add Tabarruk item") }
            }

            SectionTitle("4. Event details")
            OutlinedTextField(eventDate, { eventDate = it }, label = { Text("Event date") }, modifier = Modifier.fillMaxWidth(), singleLine = true, placeholder = { Text("DD/MM/YYYY") })
            OutlinedTextField(eventTime, { eventTime = it }, label = { Text("Event time") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            OutlinedTextField(location, { location = it }, label = { Text("Delivery / pickup location") }, modifier = Modifier.fillMaxWidth(), minLines = 2)
            OutlinedTextField(
                specialRequirements, { specialRequirements = it },
                label = { Text("Special requirements") }, modifier = Modifier.fillMaxWidth(), minLines = 4,
                placeholder = { Text("Anything else Barkat should know?") }
            )

            Button(
                onClick = { showSuccess = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Green),
                enabled = name.isNotBlank() && phone.isNotBlank() && eventDate.isNotBlank()
            ) { Text("Submit Quote Request", fontWeight = FontWeight.Bold) }

            Text(
                "No online payment. Barkat will review your request and send a quote. Delivery is quoted separately when applicable.",
                color = Ink.copy(.65f), fontSize = 12.sp, textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            if (showSuccess) {
                Card(colors = CardDefaults.cardColors(containerColor = Green), shape = RoundedCornerShape(20.dp)) {
                    Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Request ready", color = Gold, fontWeight = FontWeight.Bold)
                        Text("Your details are captured. The next step is connecting this form to Supabase so Barkat receives the request and a request ID can be generated.", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text, color = Green, fontSize = 19.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
}
