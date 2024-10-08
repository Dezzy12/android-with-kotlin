package com.dezzy.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dezzy.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ArtworkScreen()
                }
            }
        }
    }
}

@Composable
fun ArtworkScreen() {

    val artworks = listOf(
        Artwork(R.drawable.monalisa, R.string.mona_lisa, R.string.mona_lisa_artist, R.string.mona_lisa_year),
        Artwork(R.drawable.girl_earing, R.string.girl_earring, R.string.girl_earring_artist, R.string.girl_earring_year),
        Artwork(R.drawable.night_watch, R.string.night_watch, R.string.night_watch_artist, R.string.night_watch_year),
        Artwork(R.drawable.ophelia, R.string.ophelia, R.string.ophelia_artist, R.string.ophelia_year),
        Artwork(R.drawable.feel_you, R.string.feel_you, R.string.feel_you_artist, R.string.feel_you_year)
    )

    var currIndex by remember {
        mutableStateOf(0)
    }

    val currentArtwork = artworks[currIndex]


    ArtworkSpaceLayout(
        artworkName = R.string.girl_earring,
        artworkArtist = currentArtwork.artist,
        artworkYear = currentArtwork.year,
        artworkImage = currentArtwork.image,
        onPreviousClick = {
            currIndex = when (currIndex) {
                0 -> artworks.size - 1
                else -> currIndex - 1
            }
        },
        onNextClick = {
            currIndex = when(currIndex) {
                artworks.size - 1 -> 0
                else -> currIndex + 1
            }
        }
        )
}

@Composable
fun ArtworkSpaceLayout(
    @StringRes artworkName: Int,
    @StringRes artworkArtist: Int,
    @StringRes artworkYear: Int,
    @DrawableRes artworkImage: Int,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ArtworkDisplay(artworkImage = artworkImage)
        Spacer(modifier = Modifier.height(16.dp))
        ArtworkTitle(artworkName, artworkArtist,artworkYear)
        Spacer(modifier = Modifier.height(24.dp))
        NavigationButtons(
            onPreviousClick = onPreviousClick,
            onNextClick = onNextClick
        )

    }

}

@Composable
fun ArtworkDisplay(
    @DrawableRes artworkImage: Int,
) {
    Box(
        modifier = Modifier
            .padding(20.dp)
            .aspectRatio(.8f)
            .shadow(elevation = 3.dp, shape = RoundedCornerShape(8.dp))
    ){
        Image(
            painter = painterResource(id = artworkImage),
            contentDescription = stringResource(R.string.artwork),
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
            ,
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
fun ArtworkTitle(
    @StringRes artworkName: Int,
    @StringRes artworkArtist: Int,
    @StringRes artworkYear: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = artworkName),
            fontFamily = FontFamily.SansSerif,
            color = colorResource(id = R.color.purple_grey),
            fontSize = 32.sp,
        )
        Row {
            Text(
                text = stringResource(id = artworkArtist),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = " (${stringResource(id = artworkYear)})",
                color = colorResource(id = R.color.purple_grey)
            )
        }

    }
}

@Composable
fun NavigationButtons(
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
){
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        Button(
            onClick = { onPreviousClick() },
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 3.dp,
                pressedElevation = 1.dp,
                disabledElevation = 0.dp
            )
        ) {
            Text(text = "Previous")
        }
        Button(
            onClick = { onNextClick() },
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 3.dp,
                pressedElevation = 1.dp,
                disabledElevation = 0.dp
            )
        ) {
            Text(text = "Next")
        }
    }
}

data class Artwork (
    @DrawableRes val image: Int,
    @StringRes val name:Int,
    @StringRes val artist:Int,
    @StringRes val year:Int,
)

@Preview(showBackground = true)
@Composable
fun ArtSpaceLayoutPreview() {
    ArtSpaceTheme {
        ArtworkScreen()
    }
}