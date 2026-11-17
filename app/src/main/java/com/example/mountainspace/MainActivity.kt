    package com.example.mountainspace

    import android.os.Bundle
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.activity.enableEdgeToEdge
    import androidx.annotation.DrawableRes
    import androidx.annotation.StringRes
    import androidx.compose.foundation.BorderStroke
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.statusBarsPadding
    import androidx.compose.foundation.rememberScrollState
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.foundation.verticalScroll
    import androidx.compose.material3.Button
    import androidx.compose.material3.ButtonDefaults
    import androidx.compose.material3.Card
    import androidx.compose.material3.CardDefaults
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Scaffold
    import androidx.compose.material3.Surface
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableIntStateOf
    import androidx.compose.runtime.saveable.rememberSaveable
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.draw.shadow
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.layout.ContentScale
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.res.stringResource
    import androidx.compose.ui.text.font.FontStyle
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import com.example.mountainspace.ui.theme.MountainSpaceTheme

    data class Mountain(
        @param:DrawableRes val imageRes: Int,
        @param:StringRes val titleRes: Int,
        @param:StringRes val locationRes: Int,
        @param:StringRes val elevationRes: Int,
        @param:StringRes val descriptionRes: Int
    )

    val mountainList = listOf(
        Mountain(
            R.drawable.gede,
            titleRes = R.string.gede_title,
            locationRes = R.string.gede_location,
            elevationRes = R.string.gede_elevation,
            descriptionRes = R.string.gede_desc
        ),
        Mountain(
            R.drawable.ciremai,
            titleRes = R.string.ciremai_title,
            locationRes = R.string.ciremai_location,
            elevationRes = R.string.ciremai_elevation,
            descriptionRes = R.string.ciremai_desc
        ),
        Mountain(
            R.drawable.rinjani,
            titleRes = R.string.rinjani_title,
            locationRes = R.string.rinjani_location,
            elevationRes = R.string.rinjani_elevation,
            descriptionRes = R.string.rinjani_desc
        ),
        Mountain(
            R.drawable.gunung_merbabu,
            titleRes = R.string.merbabu_title,
            locationRes = R.string.merbabu_location,
            elevationRes = R.string.merbabu_elevation,
            descriptionRes = R.string.merbabu_desc
        ),
        Mountain(
            R.drawable.papandayan,
            titleRes = R.string.papandayan_title,
            locationRes = R.string.papandayan_location,
            elevationRes = R.string.papandayan_elevation,
            descriptionRes = R.string.papandayan_desc
        )
    )

    class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContent {
                MountainSpaceTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        MountainSpaceScreen(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun MountainSpaceScreen(modifier: Modifier = Modifier) {
        var currentIndex by rememberSaveable { mutableIntStateOf(0) }
        val currentMountain = mountainList[currentIndex]

        Column(
            modifier = modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App Title Header
            Text(
                text = "Mountain Space",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Mountain Artwork Display (Wall) with Blur Elevation & Shadow
            MountainWall(
                imageRes = currentMountain.imageRes,
                contentDescription = stringResource(currentMountain.titleRes)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Mountain Details Descriptor
            MountainDescriptor(
                title = stringResource(currentMountain.titleRes),
                location = stringResource(currentMountain.locationRes),
                elevation = stringResource(currentMountain.elevationRes),
                description = stringResource(currentMountain.descriptionRes)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Navigation Controller
            MountainController(
                onPreviousClick = {
                    currentIndex = if (currentIndex > 0) currentIndex - 1 else mountainList.size - 1
                },
                onNextClick = {
                    currentIndex = if (currentIndex < mountainList.size - 1) currentIndex + 1 else 0
                }
            )
        }
    }

    @Composable
    fun MountainWall(
        @DrawableRes imageRes: Int,
        contentDescription: String,
        modifier: Modifier = Modifier
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .padding(6.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(16.dp),
                    clip = false
                ),
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            border = BorderStroke(1.dp, Color(0xFFE0E0E0))
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .padding(20.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }

    @Composable
    fun MountainDescriptor(
        title: String,
        location: String,
        elevation: String,
        description: String,
        modifier: Modifier = Modifier
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 6.dp,
                    shape = RoundedCornerShape(16.dp),
                    clip = false
                ),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                ) {
                    Text(
                        text = "$location • $elevation",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = description,
                    fontSize = 13.sp,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.85f),
                    lineHeight = 18.sp
                )
            }
        }
    }

    @Composable
    fun MountainController(
        onPreviousClick: () -> Unit,
        onNextClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onPreviousClick,
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.previous_button),
                    fontWeight = FontWeight.Bold
                )
            }
            Button(
                onClick = onNextClick,
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.next_button),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MountainSpaceScreenPreview() {
        MountainSpaceTheme {
            MountainSpaceScreen()
        }
    }
