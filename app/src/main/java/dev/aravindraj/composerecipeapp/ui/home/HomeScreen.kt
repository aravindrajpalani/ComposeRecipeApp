package dev.aravindraj.composerecipeapp.ui.home


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.aravindraj.composerecipeapp.data.model.Recipe
import dev.aravindraj.composerecipeapp.ui.base.UiState
import dev.aravindraj.composerecipeapp.ui.components.ErrorScreen
import dev.aravindraj.composerecipeapp.ui.components.LoadingScreen
import dev.aravindraj.composerecipeapp.ui.theme.Grey

@Composable
fun HomeScreen(onRecipeClick: (Int) -> Unit, viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is UiState.Loading -> {
            LoadingScreen()
        }

        is UiState.Success -> {
            RecipeList(
                recipes = state.data, onRecipeClick = onRecipeClick
            )
        }

        is UiState.Error -> {
            ErrorScreen(
                message = state.message, onRetry = { })
        }
    }
}

@Composable
fun RecipeList(
    recipes: List<Recipe>, onRecipeClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(recipes) { recipe ->
            RecipeCard(
                recipe = recipe, onClick = { onRecipeClick(recipe.id ?: 0) })
        }
    }
}


@Composable
fun RecipeCard(
    recipe: Recipe, onClick: () -> Unit
) {
    Row(modifier = Modifier.clickable(onClick = onClick)) {

        AsyncImage(
            model = recipe.image,
            contentDescription = recipe.title,
            modifier = Modifier
                .widthIn(min = 120.dp, max = 150.dp)
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 2.dp)
        ) {
            Text(
                recipe.title ?: "Untitled", style = MaterialTheme.typography.titleMedium,

                maxLines = 2, overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = buildAnnotatedString {
                    append("${recipe.readyInMinutes} mins")
                    append(" • ")
                    append("${recipe.servings} servings")
                },
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                recipe.extendedIngredients.joinToString { it.name ?: "" },
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                color = Grey,
                overflow = TextOverflow.Ellipsis
            )

        }
    }
}









