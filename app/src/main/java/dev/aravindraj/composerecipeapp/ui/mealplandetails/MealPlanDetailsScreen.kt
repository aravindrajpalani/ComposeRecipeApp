package dev.aravindraj.composerecipeapp.ui.mealplandetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import dev.aravindraj.composerecipeapp.R
import dev.aravindraj.composerecipeapp.data.model.mealplan.MealPlanDay
import dev.aravindraj.composerecipeapp.data.model.mealplan.MealPlanDayUI
import dev.aravindraj.composerecipeapp.data.model.mealplan.MealSlotUI
import dev.aravindraj.composerecipeapp.data.model.mealplan.NutrientItems
import dev.aravindraj.composerecipeapp.data.model.mealplan.Nutrients
import dev.aravindraj.composerecipeapp.data.model.mealplan.NutritionSummaryUI
import dev.aravindraj.composerecipeapp.ui.base.UiState
import dev.aravindraj.composerecipeapp.ui.components.ErrorScreen
import dev.aravindraj.composerecipeapp.ui.components.LoadingScreen
import dev.aravindraj.composerecipeapp.ui.theme.Black
import dev.aravindraj.composerecipeapp.ui.theme.Grey
import dev.aravindraj.composerecipeapp.ui.theme.Grey9E
import dev.aravindraj.composerecipeapp.ui.theme.LightGreyE0
import dev.aravindraj.composerecipeapp.ui.theme.White
import dev.aravindraj.composerecipeapp.ui.theme.breakfastBGColor
import dev.aravindraj.composerecipeapp.ui.theme.caloriesColor
import dev.aravindraj.composerecipeapp.ui.theme.carbsColor
import dev.aravindraj.composerecipeapp.ui.theme.dinnerBGColor
import dev.aravindraj.composerecipeapp.ui.theme.fatColor
import dev.aravindraj.composerecipeapp.ui.theme.lunchBGColor
import dev.aravindraj.composerecipeapp.ui.theme.nutritionBGColor
import dev.aravindraj.composerecipeapp.ui.theme.onPrimaryLight
import dev.aravindraj.composerecipeapp.ui.theme.primaryLight
import dev.aravindraj.composerecipeapp.ui.theme.proteinColor
import dev.aravindraj.composerecipeapp.utils.capitalizeFirstLetter


@Composable
fun MealPlanDetailsScreen(
    mealPlanDetailsViewModel: MealPlanDetailsViewModel = hiltViewModel(), onNavigateBack: () -> Unit
) {

    val uiState by mealPlanDetailsViewModel.uiState.collectAsState()
    val selectedDayIndex by mealPlanDetailsViewModel.selectedDayIndex.collectAsState()

    when (val state = uiState) {
        is UiState.Loading -> {
            LoadingScreen()
        }

        is UiState.Success -> {
            val selectedDay = state.data.days.get(selectedDayIndex)
            val nutritionSummary =
                mealPlanDetailsViewModel.buildNutritionSummary(selectedDay.nutritionSummary?.nutrients)
            val mealPlanDay = mealPlanDetailsViewModel.buildMealPlanDayUI(selectedDay)
            val appBarTitle = state.data.name.orEmpty()

            Scaffold(topBar = {
                MealPlanDetailsTopBar(title = appBarTitle, onBackClick = onNavigateBack)
            }) { paddingValues ->

                LazyColumn(modifier = Modifier.padding(paddingValues)) {
                    item {
                        MealPlanDaysList(mealPlanDays = state.data.days, onMealPlanDaySelected = {
                            mealPlanDetailsViewModel.selectDay(it)
                        }, selectedIndex = selectedDayIndex)
                    }

                    item { Spacer(modifier = Modifier.height(12.dp)) }

                    item {
                        MealPlanDayDetails(
                            nutritionSummary, mealPlanDay
                        )
                    }
                }
            }
        }

        is UiState.Error -> {
            ErrorScreen("") { }
        }
    }

}


@Composable
fun MealPlanDayDetails(nutritionSummaryUI: NutritionSummaryUI, mealPlanDayUI: MealPlanDayUI) {

    Column(
        modifier = Modifier.padding(18.dp)
    ) {
        SectionTitle(
            nutritionBGColor, R.drawable.ic_nutrition, stringResource(R.string.nutrition_summary)
        )
        NutritionSummaryGrid(nutritionSummaryUI.asList())
        MealPlanDaySection(
            stringResource(R.string.breakfast),
            R.drawable.ic_meal_breakfast,
            breakfastBGColor,
            mealPlanDayUI.breakfastSummary,
            mealPlanDayUI.breakfastSlotUI
        )
        MealPlanDaySection(
            stringResource(R.string.lunch),
            R.drawable.ic_meal_lunch,
            lunchBGColor,
            mealPlanDayUI.lunchSummary,
            mealPlanDayUI.lunchSlotUI
        )
        MealPlanDaySection(
            stringResource(R.string.dinner),
            R.drawable.ic_meal_dinner,
            dinnerBGColor,
            mealPlanDayUI.dinnerSummary,
            mealPlanDayUI.dinnerSlotUI
        )
    }
}


@Composable
fun NutritionSummaryGrid(nutrients: List<Nutrients?>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        nutrients.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { item ->
                    if (item != null) NutritionCard(
                        label = item.name.orEmpty(),
                        value = item.amount.toString(),
                        unit = item.unit.orEmpty(),
                        percentage = item.percentOfDailyNeeds ?: 0,
                        color = getNutrientColor(item.name),
                        icon = getNutrientIcon(item.name),
                        modifier = Modifier.weight(1f)
                    )
                }
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}


@Composable
private fun MealPlanDaySection(
    title: String,
    icon: Int,
    color: Color,
    nutrientSummaryUI: NutritionSummaryUI,
    mealSlotUI: MealSlotUI
) {

    SectionTitle(color, icon, title)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 18.dp, start = 18.dp, end = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NutrientInfoCard(
            stringResource(R.string.calories),
            nutrientSummaryUI.calories?.amount.toString(),
            nutrientSummaryUI.calories?.unit.toString(),
            Modifier.weight(1f)
        )
        NutrientInfoCard(
            stringResource(R.string.protein),
            nutrientSummaryUI.protein?.amount.toString(),
            nutrientSummaryUI.protein?.unit.toString(),
            Modifier.weight(1f)
        )
        NutrientInfoCard(
            stringResource(R.string.carbs),
            nutrientSummaryUI.carbs?.amount.toString(),
            nutrientSummaryUI.carbs?.unit.toString(),
            Modifier.weight(1f)
        )
        NutrientInfoCard(
            stringResource(R.string.fat),
            nutrientSummaryUI.fat?.amount.toString(),
            nutrientSummaryUI.fat?.unit.toString(),
            Modifier.weight(1f)
        )
    }
    if (mealSlotUI.recipes.isNotEmpty()) {
        MealItemView(MealItemType.RECIPE, mealSlotUI.recipes)
    }
    if (mealSlotUI.products.isNotEmpty()) {
        MealItemView(MealItemType.PRODUCT, mealSlotUI.products)
    }
    if (mealSlotUI.ingredients.isNotEmpty()) {
        MealItemView(MealItemType.INGREDIENTS, mealSlotUI.ingredients)
    }

}

@Composable
private fun SectionTitle(
    color: Color, icon: Int, title: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 12.dp, top = 18.dp)
    ) {
        Box(
            modifier = Modifier.background(
                color = color, shape = RoundedCornerShape(10.dp)
            ), contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = "icon",
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(8.dp)
                    .size(22.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 17.sp),
        )
    }
}

@Composable
private fun MealItemView(mealItemType: MealItemType, recipes: List<NutrientItems>) {
    Column {
        recipes.forEach { recipe ->
            Card(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp)
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (mealItemType == MealItemType.INGREDIENTS) {
                        AsyncImage(
                            model = recipe.value?.ingredients?.firstOrNull()?.image,
                            modifier = Modifier
                                .size(30.dp)
                                .clip(
                                    RoundedCornerShape(
                                        8.dp
                                    )
                                ),
                            contentDescription = recipe.value?.ingredients?.first()?.name.orEmpty()
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        if (mealItemType == MealItemType.INGREDIENTS) {
                            recipe.value?.ingredients?.firstOrNull()?.name.capitalizeFirstLetter()
                        } else {
                            recipe.value?.title.orEmpty()
                        },
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

@Composable
fun NutritionCard(
    label: String,
    value: String,
    unit: String,
    percentage: Int,
    color: Color,
    icon: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(
            containerColor = Color.White
        ), elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label, fontSize = 15.sp, color = Grey, fontWeight = FontWeight.Medium
                )

                Icon(
                    painter = painterResource(icon),
                    contentDescription = label,
                    tint = color,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Black
                )
                Text(
                    text = unit,
                    fontSize = 16.sp,
                    color = Grey,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(
                            LightGreyE0, RoundedCornerShape(4.dp)
                        )
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth((percentage.coerceIn(0, 100)) / 100f)
                        .height(4.dp)
                        .background(
                            color, RoundedCornerShape(4.dp)
                        )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$percentage%",
                fontSize = 12.sp,
                color = color,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}


@Composable
fun MealPlanDaysList(
    mealPlanDays: ArrayList<MealPlanDay>,
    onMealPlanDaySelected: (selectedDayIndex: Int) -> Unit,
    selectedIndex: Int
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(
            start = 16.dp, end = 16.dp
        ), modifier = Modifier.padding(top = 18.dp)
    ) {
        itemsIndexed(mealPlanDays) { index, mealPlayDayItem ->
            val isSelected = index == selectedIndex
            Card(
                modifier = Modifier.clickable {
                    onMealPlanDaySelected(index)
                },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) primaryLight else LightGreyE0,
                    contentColor = if (isSelected) White else Grey
                ),
                shape = RoundedCornerShape(8.dp),
                border = if (isSelected) {
                    null
                } else {
                    BorderStroke(0.5.dp, LightGreyE0)
                },
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(
                        start = 18.dp, end = 18.dp, top = 12.dp, bottom = 12.dp
                    )
                ) {
                    Text(
                        text = stringResource(R.string.day),
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = mealPlayDayItem.day.orEmpty(),
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealPlanDetailsTopBar(
    title: String, onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = primaryLight, titleContentColor = onPrimaryLight
        ), navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = onPrimaryLight
                )
            }
        })
}


fun getNutrientColor(name: String?): Color {
    return when (name?.lowercase()) {
        "calories", "energy" -> caloriesColor
        "protein" -> proteinColor
        "fat", "total fat" -> fatColor
        "carbohydrates", "carbs" -> carbsColor
        else -> Grey9E
    }
}


fun getNutrientIcon(name: String?): Int {
    return when (name?.lowercase()) {
        "calories", "energy" -> R.drawable.ic_calories
        "protein" -> R.drawable.ic_protein
        "fat", "total fat" -> R.drawable.ic_fat
        "carbohydrates", "carbs" -> R.drawable.ic_carbs
        else -> R.drawable.ic_carbs
    }
}

@Composable
fun NutrientInfoCard(label: String, value: String, unit: String, modifier: Modifier) {

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(6.dp))
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = value, maxLines = 1, overflow = TextOverflow.Ellipsis,

                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = unit,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )
    }

}

enum class MealSlot(val slot: Int) {
    BREAKFAST(1), LUNCH(2), DINNER(3)
}

enum class MealItemType(val value: String) {
    RECIPE("RECIPE"),
    INGREDIENTS("INGREDIENTS"),
    PRODUCT("PRODUCT")
}


















