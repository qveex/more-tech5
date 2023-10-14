package qveex.ru.more.presentation.screens.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import qveex.ru.more.R
import qveex.ru.more.ui.theme.Moretech5Theme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartItem(image: Painter, text: String, onClickListener: () -> Unit) {
    Card(onClick = onClickListener) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = image,
                modifier = Modifier.height(32.dp),
                contentDescription = "loan"
            )

            Text(text)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen() {
    Scaffold(topBar = { LargeTopAppBar(title = { Text(text = "Какая услуга Вам нужна?") }) }) {
        Column(Modifier.padding(it)) {
            StartItem(
                image = painterResource(id = R.drawable.ic_loan),
                text = "Оформить кредит или ипотеку"
            ) {
// Todo
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
