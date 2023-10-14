package qveex.ru.more.presentation.screens.department_info.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.BarStyle
import co.yml.charts.ui.barchart.models.SelectionHighlightData
import qveex.ru.more.data.models.OpenHours
import qveex.ru.more.ui.theme.Moretech5Theme

@Composable
fun LoadStatisticChart(
    openHours: OpenHours,
    load: List<Int>
) {
    val xAxisData = AxisData.Builder()
        .steps(load.size - 1)
        .axisStepSize(20.dp)
        .startPadding(12.dp)
        .topPadding(4.dp)
        .shouldDrawAxisLineTillEnd(true)
        .axisLabelColor(MaterialTheme.colorScheme.onBackground)
        .axisLineColor(MaterialTheme.colorScheme.onBackground)
        .axisLabelAngle(0f)
        .labelData { index -> load[index].toString() }
        .build()

    val yAxisData = AxisData.Builder()
        .steps(load.size - 1)
        .labelAndAxisLinePadding(0.dp)
        .axisOffset(2.dp)
        .axisLabelColor(MaterialTheme.colorScheme.onPrimaryContainer)
        .axisLineColor(MaterialTheme.colorScheme.onPrimaryContainer)
        .backgroundColor(MaterialTheme.colorScheme.background)
        .shouldDrawAxisLineTillEnd(true)
        .labelData { index -> "${load[index]}%" }
        .build()

    val barChartData = BarChartData(
        chartData = load.mapIndexed { index, i ->
            BarData(
                point = Point(index.toFloat(), i.toFloat()),
                color = MaterialTheme.colorScheme.primaryContainer
            )
        },
        backgroundColor = MaterialTheme.colorScheme.background,
        showYAxis = false,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        barStyle = BarStyle(
            paddingBetweenBars = 16.dp,
            barWidth = 22.dp,
            cornerRadius = 22.dp,
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
            openHours = OpenHours("9:00", "22:00"),
            load = listOf(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
        )
    }
}