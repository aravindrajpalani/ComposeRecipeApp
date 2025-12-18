package dev.aravindraj.composerecipeapp.ui.ingredients

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.aravindraj.composerecipeapp.R
import dev.aravindraj.composerecipeapp.data.model.IngredientCategory
import dev.aravindraj.composerecipeapp.ui.base.UiState
import dev.aravindraj.composerecipeapp.ui.components.ErrorScreen
import dev.aravindraj.composerecipeapp.ui.components.LoadingScreen
import dev.aravindraj.composerecipeapp.ui.theme.Black
import dev.aravindraj.composerecipeapp.ui.theme.Grey
import dev.aravindraj.composerecipeapp.ui.theme.Grey9E
import dev.aravindraj.composerecipeapp.ui.theme.LightGrey
import dev.aravindraj.composerecipeapp.ui.theme.White
import dev.aravindraj.composerecipeapp.ui.theme.primaryContainerLight
import dev.aravindraj.composerecipeapp.ui.theme.primaryLight
import dev.aravindraj.composerecipeapp.ui.theme.secondaryContainerLight


@Composable
fun IngredientsScreen(
    viewModel: IngredientsViewModel, onNavigateToRecipes: (List<String>) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is UiState.Loading -> {
            LoadingScreen()
        }

        is UiState.Success -> {
            ExpandableIngredientList(
                ingredientCategories = state.data,
                onClick = { selectedIngredients -> onNavigateToRecipes(selectedIngredients) })
        }

        is UiState.Error -> {
            ErrorScreen(
                message = state.message, onRetry = { })
        }
    }
}

@Composable
fun ExpandableIngredientList(
    ingredientCategories: List<IngredientCategory>, onClick: (List<String>) -> Unit
) {

    val expandedStates = remember {
        mutableStateMapOf<String, Boolean>()
    }

    val selectedIngredients = remember { mutableStateListOf<String>() }

    Column(modifier = Modifier.fillMaxSize()) {

        ClearFilterButton(
            visible = selectedIngredients.isNotEmpty(), onClick = {
                selectedIngredients.clear()
            }, modifier = Modifier
                .align(Alignment.End)
                .padding(top = 12.dp, end = 14.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(ingredientCategories) { index, category ->
                val isExpanded = expandedStates[category.category] ?: false
                ExpandableIngredientCategory(
                    ingredientCategory = category,
                    selectedIngredients = selectedIngredients,
                    isExpanded = isExpanded,
                    onToggleExpand = {
                        expandedStates[category.category] = !isExpanded
                    })
            }
        }
        FindRecipesButton(
            selectedCount = selectedIngredients.size,
            onClick = { onClick(selectedIngredients.toList()) })
    }
}

@Composable
fun ExpandableIngredientCategory(
    ingredientCategory: IngredientCategory,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    selectedIngredients: MutableList<String>
) {
    val selectedCount = ingredientCategory.items.count { ingredient ->
        ingredient.name in selectedIngredients
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleExpand() }
                    .padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = ingredientCategory.category,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    if (selectedCount > 0) {
                        CategoryCountBadge(count = selectedCount)
                    }
                }

                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = stringResource(R.string.content_desc_toggle),
                    tint = Grey,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }


            if (isExpanded) {

                FlowRow(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    ingredientCategory.items.forEach { ingredient ->
                        val isSelected = ingredient.name in selectedIngredients

                        SelectableIngredientChip(
                            name = ingredient.name ?: "",
                            selected = isSelected,
                            onSelectedChange = {
                                if (it) selectedIngredients.add(ingredient.name ?: "")
                                else selectedIngredients.remove(ingredient.name)
                            })
                    }
                }

            }
        }
    }
}


@Composable
fun SelectableIngredientChip(
    name: String, selected: Boolean, onSelectedChange: (Boolean) -> Unit
) {
    FilterChip(
        selected = selected,
        modifier = Modifier.padding(end = 8.dp, bottom = 2.dp),
        shape = RoundedCornerShape(50),
        border = null,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = LightGrey,
            selectedContainerColor = primaryLight,
            labelColor = Black,
            selectedLabelColor = White
        ),
        onClick = { onSelectedChange(!selected) },
        label = { Text(name, style = MaterialTheme.typography.bodyMedium) })
}

@Composable
fun CategoryCountBadge(count: Int) {
    Badge(
        contentColor = primaryLight,
        containerColor = primaryContainerLight,
    ) {
        Text(
            text = count.toString(),
            color = primaryLight,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun FindRecipesButton(
    selectedCount: Int, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = selectedCount > 0,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = primaryLight,
            contentColor = White,
            disabledContainerColor = secondaryContainerLight,
            disabledContentColor = Grey9E
        )
    ) {
        Text(
            text = stringResource(R.string.find_recipes),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        if (selectedCount > 0) {
            Spacer(modifier = Modifier.width(12.dp))
            Badge(
                containerColor = White,
                contentColor = primaryLight,
            ) {
                Text(
                    text = selectedCount.toString(),
                    color = primaryLight,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
                )
            }
        }
    }
}


@Composable
fun ClearFilterButton(
    visible: Boolean, onClick: () -> Unit, modifier: Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.alpha(if (visible) 1f else 0f),
        enabled = visible,
        colors = ButtonDefaults.textButtonColors(
            contentColor = primaryLight
        )
    ) {
        Text(
            text = stringResource(R.string.clear_all), style = MaterialTheme.typography.labelLarge
        )
    }
}
