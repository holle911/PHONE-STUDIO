package fr.phonestudio.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val Ink = Color(0xFF090D16)
private val Panel = Color(0xCC182235)
private val Accent = Color(0xFF7CE7D5)
private val Muted = Color(0xFF9BA9BD)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PhoneStudioApp() }
    }
}

data class StudioApp(val name: String, val symbol: String, val tint: Color)

private val demoApps = listOf(
    StudioApp("Téléphone", "☎", Color(0xFF69D6A6)),
    StudioApp("Messages", "✉", Color(0xFF69B9F2)),
    StudioApp("Photos", "▧", Color(0xFFF3B96C)),
    StudioApp("Appareil photo", "◉", Color(0xFFD6A4F5)),
    StudioApp("Chrome", "◎", Color(0xFF7CB8F5)),
    StudioApp("Musique", "♫", Color(0xFFF28DA8)),
    StudioApp("Paramètres", "⚙", Color(0xFFB4C0D2)),
    StudioApp("Agenda", "▦", Color(0xFFE88C75))
)

@Composable
fun PhoneStudioApp() {
    var page by remember { mutableStateOf("home") }
    var drawerOpen by remember { mutableStateOf(false) }
    var editorOpen by remember { mutableStateOf(false) }
    var accent by remember { mutableStateOf(Accent) }
    var glass by remember { mutableStateOf(true) }
    var gridColumns by remember { mutableIntStateOf(4) }
    var screenName by remember { mutableStateOf("HOME 01") }

    MaterialTheme(colorScheme = darkColorScheme(
        background = Ink, surface = Panel, primary = accent,
        onBackground = Color.White, onSurface = Color.White
    )) {
        Box(
            Modifier.fillMaxSize().background(
                Brush.linearGradient(listOf(Color(0xFF111D2B), Ink, Color(0xFF172D35)))
            )
        ) {
            if (page == "home") {
                HomeScreen(
                    accent = accent,
                    onDrawer = { drawerOpen = true },
                    onEdit = { editorOpen = true },
                    screenName = screenName,
                    onNextScreen = { screenName = if (screenName == "HOME 01") "HOME 02" else "HOME 01" }
                )
            } else {
                StudioEditor(
                    accent = accent,
                    onAccent = { accent = it },
                    glass = glass,
                    onGlass = { glass = it },
                    columns = gridColumns,
                    onColumns = { gridColumns = it },
                    onBack = { page = "home" }
                )
            }

            if (drawerOpen) {
                AppDrawer(accent = accent, columns = gridColumns, onClose = { drawerOpen = false })
            }

            if (editorOpen) {
                ModalBottomSheet(
                    onDismissRequest = { editorOpen = false },
                    containerColor = Color(0xFF111A29),
                    contentColor = Color.White
                ) {
                    Column(Modifier.fillMaxWidth().padding(horizontal = 22.dp).padding(bottom = 28.dp)) {
                        Text("ÉDITION RAPIDE", color = accent, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                        Spacer(Modifier.height(8.dp))
                        Text("Personnalise ton écran sans quitter l'accueil.", color = Muted)
                        Spacer(Modifier.height(18.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            listOf(Color(0xFF7CE7D5), Color(0xFFB58CFF), Color(0xFFFFB86B), Color(0xFF77A9FF)).forEach { c ->
                                Box(Modifier.size(38.dp).clip(CircleShape).background(c).clickable { accent = c })
                            }
                        }
                        Spacer(Modifier.height(18.dp))
                        Button(onClick = { editorOpen = false; page = "editor" }, colors = ButtonDefaults.buttonColors(containerColor = accent)) {
                            Text("OUVRIR L'ÉDITEUR COMPLET", color = Ink)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeScreen(
    accent: Color,
    onDrawer: () -> Unit,
    onEdit: () -> Unit,
    screenName: String,
    onNextScreen: () -> Unit
) {
    val date = remember { SimpleDateFormat("EEEE d MMMM", Locale.FRENCH).format(Date()).uppercase() }
    Column(Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding().padding(22.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("PHONE STUDIO", color = accent, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 3.sp)
                Text(screenName, color = Muted, fontSize = 10.sp, letterSpacing = 2.sp)
            }
            IconButton(onClick = onEdit) { Icon(Icons.Default.Tune, "Personnaliser", tint = Color.White) }
        }
        Spacer(Modifier.weight(1f))
        Column(Modifier.fillMaxWidth().padding(bottom = 20.dp)) {
            Text("14:28", color = Color.White, fontSize = 66.sp, fontWeight = FontWeight.Light, letterSpacing = (-3).sp)
            Text(date, color = accent, fontSize = 12.sp, letterSpacing = 2.sp)
        }
        GlassPanel(accent) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("TON ESPACE", color = accent, fontSize = 10.sp, letterSpacing = 2.sp, fontWeight = FontWeight.Bold)
                    Text("Tout commence ici.", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                    Text("Appuie longuement pour personnaliser", color = Muted, fontSize = 11.sp)
                }
                Icon(Icons.Default.AutoAwesome, null, tint = accent, modifier = Modifier.size(30.dp))
            }
        }
        Spacer(Modifier.height(22.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            demoApps.take(4).forEach { AppShortcut(it, accent) }
        }
        Spacer(Modifier.height(18.dp))
        Row(
            Modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp)).background(Panel).padding(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onDrawer) { Icon(Icons.Default.GridView, "Applications", tint = accent) }
            IconButton(onClick = onNextScreen) { Icon(Icons.Default.Swipe, "Changer d'écran", tint = Color.White) }
            IconButton(onClick = onEdit) { Icon(Icons.Default.Edit, "Modifier", tint = Color.White) }
        }
        Spacer(Modifier.height(10.dp))
        Text("Glisse sur l'icône centrale pour changer d'écran • Appuie sur la grille pour ouvrir les apps",
            color = Muted, fontSize = 9.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

@Composable
private fun GlassPanel(accent: Color, content: @Composable RowScope.() -> Unit) {
    Row(
        Modifier.fillMaxWidth().clip(RoundedCornerShape(26.dp))
            .background(Brush.linearGradient(listOf(Color(0xCC26364A), Color(0x88202B3B))))
            .padding(18.dp),
        content = content
    )
}

@Composable
private fun AppShortcut(app: StudioApp, accent: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(68.dp)) {
        Box(Modifier.size(54.dp).clip(RoundedCornerShape(19.dp)).background(app.tint.copy(alpha = .18f)),
            contentAlignment = Alignment.Center) {
            Text(app.symbol, color = app.tint, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(6.dp))
        Text(app.name, color = Color.White, fontSize = 10.sp, maxLines = 1)
    }
}

@Composable
private fun AppDrawer(accent: Color, columns: Int, onClose: () -> Unit) {
    Column(Modifier.fillMaxSize().background(Color(0xF20A101B)).statusBarsPadding().padding(20.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("APPLICATIONS", color = accent, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                Text("Ton espace, tes règles", color = Muted, fontSize = 12.sp)
            }
            IconButton(onClick = onClose) { Icon(Icons.Default.Close, "Fermer", tint = Color.White) }
        }
        Spacer(Modifier.height(18.dp))
        LazyVerticalGrid(columns = GridCells.Fixed(columns), verticalArrangement = Arrangement.spacedBy(22.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(demoApps) { AppShortcut(it, accent) }
        }
    }
}

@Composable
private fun StudioEditor(
    accent: Color,
    onAccent: (Color) -> Unit,
    glass: Boolean,
    onGlass: (Boolean) -> Unit,
    columns: Int,
    onColumns: (Int) -> Unit,
    onBack: () -> Unit
) {
    Column(Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding().padding(22.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Retour", tint = Color.White) }
            Text("STUDIO DE PERSONNALISATION", color = accent, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
        Spacer(Modifier.height(20.dp))
        Text("Compose ton interface.", color = Color.White, fontSize = 27.sp, fontWeight = FontWeight.Light)
        Text("Les réglages essentiels sont ici. D'autres modules arriveront ensuite.", color = Muted, fontSize = 12.sp)
        Spacer(Modifier.height(26.dp))
        Text("COULEUR PRINCIPALE", color = accent, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            listOf(Color(0xFF7CE7D5), Color(0xFFB58CFF), Color(0xFFFFB86B), Color(0xFF77A9FF), Color(0xFFFF7F9B)).forEach { c ->
                Box(Modifier.size(42.dp).clip(CircleShape).background(c).clickable { onAccent(c) })
            }
        }
        Spacer(Modifier.height(28.dp))
        Text("APPARENCE", color = accent, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text("Effet verre / glassmorphism", color = Color.White)
                Text("Panneaux translucides", color = Muted, fontSize = 11.sp)
            }
            Switch(checked = glass, onCheckedChange = onGlass)
        }
        Spacer(Modifier.height(22.dp))
        Text("GRILLE D'APPLICATIONS", color = accent, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("$columns colonnes", color = Color.White, modifier = Modifier.weight(1f))
            IconButton(onClick = { if (columns > 3) onColumns(columns - 1) }) { Icon(Icons.Default.Remove, null, tint = Color.White) }
            IconButton(onClick = { if (columns < 6) onColumns(columns + 1) }) { Icon(Icons.Default.Add, null, tint = Color.White) }
        }
        Spacer(Modifier.weight(1f))
        Text("À VENIR : IA DE THÈMES • WIDGET STUDIO • WALLPAPER STUDIO • ANIMATIONS",
            color = Muted, fontSize = 10.sp, lineHeight = 17.sp)
    }
}
