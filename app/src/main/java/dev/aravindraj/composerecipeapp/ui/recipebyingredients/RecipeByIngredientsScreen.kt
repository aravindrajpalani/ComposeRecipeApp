package dev.aravindraj.composerecipeapp.ui.recipebyingredients

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.aravindraj.composerecipeapp.data.model.RecipeByIngredients
import dev.aravindraj.composerecipeapp.ui.base.UiState
import dev.aravindraj.composerecipeapp.ui.components.ErrorScreen
import dev.aravindraj.composerecipeapp.ui.components.LoadingScreen
import dev.aravindraj.composerecipeapp.ui.theme.Grey
import dev.aravindraj.composerecipeapp.ui.theme.White
import dev.aravindraj.composerecipeapp.ui.theme.onPrimaryLight
import dev.aravindraj.composerecipeapp.ui.theme.primaryLight

@Composable
fun RecipeByIngredientsScreen(
    recipeByIngredientsViewModel: RecipeByIngredientsViewModel, onNavigateBack: () -> Unit
) {
    val uiState by recipeByIngredientsViewModel.recipesUiState.collectAsState()


    Scaffold(topBar = { RecipeByIngredientsTopBar(onBackClick = onNavigateBack) }) { paddingValues ->
        when (val state = uiState) {
            is UiState.Loading -> {
                LoadingScreen()
            }

            is UiState.Success -> {
                RecipeByIngredientsList(state.data, modifier = Modifier.padding(paddingValues))
            }

            is UiState.Error -> {
                ErrorScreen("Retry") { recipeByIngredientsViewModel.findRecipesByIngredients() }
            }
        }
    }
}

@Composable
fun RecipeByIngredientsList(items: List<RecipeByIngredients>, modifier: Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(items) { item ->
            RecipeByIngredientsItem(item)
        }
    }
}

@Composable
fun RecipeByIngredientsItem(item: RecipeByIngredients) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            AsyncImage(
                model = item.image,
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp, topEnd = 16.dp
                        )
                    ),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = item.title ?: "Untitled",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    minLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = item.usedIngredients.joinToString { it.name.orEmpty() },
                    style = MaterialTheme.typography.bodyMedium,
                    color = Grey,
                    maxLines = 2,
                    minLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeByIngredientsTopBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = "Selected Ingredients Recipes", style = MaterialTheme.typography.titleLarge)
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
