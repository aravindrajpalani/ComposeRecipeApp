package dev.aravindraj.composerecipeapp.ui.mealplan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aravindraj.composerecipeapp.R
import dev.aravindraj.composerecipeapp.data.model.MealTemplate
import dev.aravindraj.composerecipeapp.ui.base.UiState
import dev.aravindraj.composerecipeapp.ui.components.ErrorScreen
import dev.aravindraj.composerecipeapp.ui.components.LoadingScreen
import dev.aravindraj.composerecipeapp.ui.theme.Grey9E
import dev.aravindraj.composerecipeapp.ui.theme.White
import dev.aravindraj.composerecipeapp.ui.theme.onPrimaryLight

val mealPlanColors = listOf(
    Color(0xFFE91E63),
    Color(0xFF2196F3),
    Color(0xFF00BCD4),
    Color(0xFF9C27B0),
    Color(0xFFFF9800),
    Color(0xFF4CAF50),
    Color(0xFF3F51B5),
    Color(0xFFFF5722),
    Color(0xFF009688)
)

@Composable
fun MealPlanScreen(onMealPlanClick: (Int) -> Unit, mealPlanViewModel: MealPlanViewModel) {

    val uiState by mealPlanViewModel.uiState.collectAsState()

    when (val state = uiState) {
        is UiState.Loading -> {
            LoadingScreen()
        }

        is UiState.Success -> {
            MealTemplateListScreen(
                mealTemplates = state.data, onMealPlanClick = onMealPlanClick
            )
        }

        is UiState.Error -> {
            ErrorScreen(
                message = state.message, onRetry = { })
        }
    }
}

@Composable
fun MealTemplateListScreen(
    mealTemplates: List<MealTemplate>, onMealPlanClick: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

        MealPlanScreenHeader()
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            items(mealTemplates) { item ->
                MealTemplateItem(
                    template = item, onClick = { onMealPlanClick(item.id) })
            }
        }
    }
}

@Composable
fun MealPlanScreenHeader() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(R.string.meal_plans), style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.choose_a_plan),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun MealTemplateItem(
    template: MealTemplate, onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        color = mealPlanColors.random(), shape = CircleShape
                    ), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = template.name?.trim()?.firstOrNull()?.uppercase() ?: "M",
                    color = onPrimaryLight,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = template.name ?: "",
                style = MaterialTheme.typography.titleMedium,
                minLines = 1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                tint = Grey9E,
                contentDescription = null
            )
        }
    }
}







