package qveex.ru.more.presentation.screens.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import qveex.ru.more.R
import qveex.ru.more.ui.theme.Moretech5Theme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartItem(image: Painter, text: String, backgroundColor: Color, onClickListener: () -> Unit) {
    Card(
        onClick = onClickListener,
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = image,
                modifier = Modifier,
                contentDescription = "loan"
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(text, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen() {
    Scaffold(topBar = {
        LargeTopAppBar(
            title = { Text(text = "Какая услуга Вам нужна?") },
        )
    }) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(it),
        ) {
            item {
                StartItem(
                    image = painterResource(id = R.drawable.ic_loan),
                    text = "Кредит или\nИпотека",
                    backgroundColor = Color(0xFFF8EAA2)
                ) {
// Todo
                }
            }
            item {
                StartItem(
                    image = painterResource(id = R.drawable.ic_loan),
                    text = "Найти\nбанкомат",
                    backgroundColor = Color(0xFFD9FFDE)

                ) {
// Todo
                }
            }
            item {
                StartItem(
                    image = painterResource(id = R.drawable.ic_loan),
                    text = "Просмотр\nотделений",
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer

                ) {
// Todo
                }
            }
            item {
                StartItem(
                    image = painterResource(id = R.drawable.ic_loan),
                    text = "Открыть\nвклад",
                    backgroundColor = Color(0x49F57EFF)

                ) {
// Todo
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewStartScreen() {
    Moretech5Theme {
        StartScreen()
    }
}
