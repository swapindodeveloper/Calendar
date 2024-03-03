package io.wojciechosak.calendar.calendar.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import io.wojciechosak.calendar.config.rememberCalendarState
import io.wojciechosak.calendar.utils.asYearMonth
import io.wojciechosak.calendar.utils.today
import io.wojciechosak.calendar.view.CalendarDay
import io.wojciechosak.calendar.view.CalendarView
import io.wojciechosak.calendar.view.HorizontalCalendarView
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

class MultipleSelectionScreen : Screen {
    @Composable
    override fun Content() {
        Column {
            val selectedDates = remember { mutableStateListOf<LocalDate>() }

            HorizontalCalendarView { monthOffset ->
                CalendarView(
                    day = { dayState ->
                        CalendarDay(
                            state = dayState,
                            onClick = {
                                if (selectedDates.size > 2) {
                                    selectedDates.removeFirst()
                                }
                                selectedDates.add(dayState.date)
                            },
                        )
                    },
                    config = rememberCalendarState(
                        yearMonth = LocalDate
                            .today()
                            .plus(monthOffset, DateTimeUnit.MONTH)
                            .asYearMonth(),
                        showWeekdays = true,
                        showPreviousMonthDays = true,
                        showNextMonthDays = true,
                    ),
                    isActiveDay = {
                        it in selectedDates
                    }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            if (!selectedDates.isEmpty()) {
                Text("Selected:\n${selectedDates.map { "$it\n" }}")
            }
        }
    }
}