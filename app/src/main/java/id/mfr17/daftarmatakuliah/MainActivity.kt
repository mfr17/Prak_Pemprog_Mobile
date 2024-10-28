package id.mfr17.daftarmatakuliah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.mfr17.daftarmatakuliah.data.Data
import id.mfr17.daftarmatakuliah.model.Matakuliah
import id.mfr17.daftarmatakuliah.ui.theme.DaftarMatakuliahTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DaftarMatakuliahTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DaftarMatakuliahApp()
                }
            }
        }
    }
}

@Composable
fun DaftarMatakuliahApp() {
    Scaffold(
//      Menampilkan nama aplikasi pada topbar atau bagian atas
        topBar = {
            TopAppBar()
        }
    )
    { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            items(Data.matakuliahList) { matakuliah ->
                MatakuliahItem(matakuliah = matakuliah, modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
            }
        }
    }
}

@Composable
fun MatakuliahItem(matakuliah: Matakuliah, modifier: Modifier = Modifier) {

//    variabel yang digunakan untuk menyimpan state
    var expanded by remember { mutableStateOf(false) }
    var editable by remember { mutableStateOf(false) }
    var nama by remember { mutableStateOf(matakuliah.nama) }
    var sks by remember { mutableStateOf(matakuliah.sks.toString()) }
    var dosen by remember { mutableStateOf(matakuliah.dosen) }

//    Card untuk menampilkan data matakuliah
    Card(
        modifier = modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
            .padding(dimensionResource(id = R.dimen.padding_small)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
//          Datalist yang ditampilkan
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_grain),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(MaterialTheme.shapes.small),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = nama,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface // Warna teks
                    ),
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
//          Jika tombol di klik akan menampilkan deskripsi
            if (expanded) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondaryContainer, shape = MaterialTheme.shapes.small)
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .fillMaxWidth()
                ) {
                    Text("SKS: $sks", style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface))
                    Text("Dosen: $dosen", style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface))
                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = nama,
                        onValueChange = { if (editable) nama = it },
                        label = { Text("Nama Matakuliah") },
                        enabled = editable,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                        ),
                        shape = MaterialTheme.shapes.medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = sks,
                        onValueChange = { if (editable) sks = it },
                        label = { Text("Jumlah SKS") },
                        enabled = editable,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                        ),
                        shape = MaterialTheme.shapes.medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = dosen,
                        onValueChange = { if (editable) dosen = it },
                        label = { Text("Dosen") },
                        enabled = editable,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                        ),
                        shape = MaterialTheme.shapes.medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
//  Tombol untuk edit dan save
                    Button(
                        onClick = {
                            if (editable) {
                                Data.updateMatakuliah(matakuliah.copy(nama = nama, sks = sks.toInt(), dosen = dosen))
                            }
                            editable = !editable
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = if (editable) "Save" else "Edit")
                    }
                }
            }
        }
    }
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(onSettingsClick: () -> Unit = {}) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary // Use contrasting color for visibility
                )
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary, // Background color of the TopAppBar
            titleContentColor = MaterialTheme.colorScheme.onPrimary // Title text color
        )
    )
}



@Preview
@Composable
fun PreviewDaftarMatakuliah() {
    DaftarMatakuliahTheme {
        DaftarMatakuliahApp()
    }
}
