package qveex.ru.more.presentation.screens.department_info.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.BarStyle
import co.yml.charts.ui.barchart.models.SelectionHighlightData
import qveex.ru.more.ui.theme.Moretech5Theme
import qveex.ru.more.ui.theme.errorColor
import qveex.ru.more.ui.theme.warningColor

@Composable
fun LoadStatisticChart(
    load: Map<String, Int>
) {
    val data = load.map { it.key to it.value }
    val xAxisData = AxisData.Builder()
        .steps(data.size - 1)
        .axisStepSize(25.dp)
        .startDrawPadding(12.dp)
        .topPadding(4.dp)
        .axisLabelFontSize(12.sp)
        .shouldDrawAxisLineTillEnd(true)
        .axisLabelColor(MaterialTheme.colorScheme.onBackground)
        .axisLineColor(MaterialTheme.colorScheme.onBackground)
        .axisLabelAngle(30f)
        .labelData { index -> data[index].first }
        .build()

    val yAxisData = AxisData.Builder()
        .steps(data.size - 1)
        .labelAndAxisLinePadding(0.dp)
        .axisOffset(2.dp)
        .axisLabelColor(MaterialTheme.colorScheme.onPrimaryContainer)
        .axisLineColor(MaterialTheme.colorScheme.onPrimaryContainer)
        .backgroundColor(MaterialTheme.colorScheme.background)
        .shouldDrawAxisLineTillEnd(true)
        .labelData { index -> "${data[index]}%" }
        .build()

    val barChartData = BarChartData(
        chartData = data.mapIndexed { index, i ->
            val color = when (i.second) {
                in 0..30 -> MaterialTheme.colorScheme.primaryContainer
                in 31..75 -> warningColor.copy(alpha = .6f)
                in 75..100 -> errorColor.copy(alpha = .6f)
                else -> MaterialTheme.colorScheme.primaryContainer
            }
            BarData(
                point = Point(index.toFloat(), i.second.toFloat()),
                color = color
            )
        },
        backgroundColor = MaterialTheme.colorScheme.background,
        showYAxis = false,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        barStyle = BarStyle(
            paddingBetweenBars = 12.dp,
            barWidth = 18.dp,
            cornerRadius = 20.dp,
            selectionHighlightData = SelectionHighlightData(
                isHighlightBarRequired = false,
                isHighlightFullBar = false,
                popUpLabel = { x, y -> "" }
            )
        )
    )

    BarChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        barChartData = barChartData
    )
}


@Preview
@Composable
private fun LoadStatisticChartPreview() {
    Moretech5Theme {
        LoadStatisticChart(
            load = mapOf("1" to 10,"2" to  20, "3" to 30, "4" to 40, "5" to 50, "6" to 60, "7" to 70, "8" to 80, "9" to 90)
        )
    }
}