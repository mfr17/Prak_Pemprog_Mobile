package id.mfr17.daftarmatakuliah.ui.theme

// Theme.kt
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = Color.White,
    primaryContainer = tertiaryDark,
    secondary = secondaryDark,
    onSecondary = Color.Black,
    secondaryContainer = tertiaryDark,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = Color.White,
    primaryContainer = tertiaryLight,
    secondary = secondaryLight,
    onSecondary = Color.Black,
    secondaryContainer = tertiaryLight,
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFFFFFFF),
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun DaftarMatakuliahTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}
