package dev.aravindraj.composerecipeapp.ui.recipedetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.aravindraj.composerecipeapp.R
import dev.aravindraj.composerecipeapp.data.model.Recipe
import dev.aravindraj.composerecipeapp.ui.base.UiState
import dev.aravindraj.composerecipeapp.ui.components.ErrorScreen
import dev.aravindraj.composerecipeapp.ui.components.ExpandableText
import dev.aravindraj.composerecipeapp.ui.components.LoadingScreen
import dev.aravindraj.composerecipeapp.ui.home.HomeViewModel
import dev.aravindraj.composerecipeapp.ui.theme.LightGrey
import dev.aravindraj.composerecipeapp.utils.capitalizeFirstLetter
import dev.aravindraj.composerecipeapp.utils.toIntIfWhole

private val headerHeight = 250.dp
private val toolbarHeight = 56.dp

private val paddingMedium = 8.dp

private val titlePaddingStart = 16.dp
private val titlePaddingEnd = 72.dp

private const val titleFontScaleStart = 1f
private const val titleFontScaleEnd = 0.66f

@Composable
fun RecipeDetailsScreen(recipeId: Int, onNavigateBack: () -> Unit, viewModel: HomeViewModel) {

    val uiState by viewModel.uiState.collectAsState()


    when (val state = uiState) {
        is UiState.Loading -> {
            LoadingScreen()
        }

        is UiState.Success -> {
            val item: Recipe? = state.data.firstOrNull { it.id == recipeId }
            if (item != null) {
                val lazyListState: LazyListState = rememberLazyListState()
                val scrollOffset = remember {
                    derivedStateOf {
                        if (lazyListState.firstVisibleItemIndex == 0) {
                            lazyListState.firstVisibleItemScrollOffset.toFloat()
                        } else {
                            Float.MAX_VALUE
                        }
                    }
                }
                val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
                val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }

                Scaffold { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .background(Color.White)
                    ) {
                        Header(item.image ?: "", scrollOffset, headerHeightPx)
                        Body(item, lazyListState)
                        Toolbar(scrollOffset, headerHeightPx, toolbarHeightPx, onNavigateBack)
                        Title(item.title ?: "", scrollOffset, headerHeightPx, toolbarHeightPx)
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
private fun Header(imageUrl: String, scroll: State<Float>, headerHeightPx: Float) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight)
            .graphicsLayer {
                translationY = -scroll.value.toFloat() / 2f
                alpha = (-1f / headerHeightPx) * scroll.value + 1
            }) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xAA000000)),
                        startY = 3 * headerHeightPx / 4
                    )
                )
        )
    }
}

@Composable
private fun Body(recipeItem: Recipe, lazyListState: LazyListState) {
    LazyColumn(
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(top = toolbarHeight + 12.dp)
    ) {
        item { Spacer(Modifier.height(headerHeight)) }
        item {
            ExpandableText(
                text = AnnotatedString.Companion.fromHtml(recipeItem.summary ?: ""),
                modifier = Modifier.padding(start = 18.dp, end = 18.dp)
            )
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp, start = 18.dp, end = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoCard(
                    R.drawable.ic_cooking_time,
                    stringResource(R.string.cooking_time),
                    "${recipeItem.readyInMinutes ?: 0} mins",
                    Modifier.weight(1f)
                )
                InfoCard(
                    R.drawable.ic_servings,
                    stringResource(R.string.servings),
                    "${recipeItem.servings ?: 0}",
                    Modifier.weight(1f)
                )
                InfoCard(
                    R.drawable.ic_health_score,
                    stringResource(R.string.health_score),
                    "${recipeItem.healthScore ?: 0}",
                    Modifier.weight(1f)
                )
            }
        }
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp, start = 18.dp, end = 18.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_ingredients),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.ingredients),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium)
                )
            }

        }
        items(recipeItem.extendedIngredients.size) { index ->
            IngredientItem(
                recipeItem.extendedIngredients.get(index).name.capitalizeFirstLetter(),
                "${recipeItem.extendedIngredients.get(index).measures?.metric?.amount.toIntIfWhole() ?: ""}",
                recipeItem.extendedIngredients.get(index).measures?.metric?.unitShort ?: "",
                Modifier.padding(start = 18.dp, end = 18.dp)
            )
        }
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 14.dp, top = 14.dp, start = 18.dp, end = 18.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_instructions),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.instructions),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium)
                )
            }

        }
//        val totalSteps = recipeItem.analyzedInstructions.first().steps.size
        recipeItem.analyzedInstructions.first().steps.forEach { steps ->
            stickyHeader {
                Text(
                    text = "Step ${steps.number ?: 0}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
                    lineHeight = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(LightGrey)
                        .padding(start = 18.dp, end = 18.dp, top = 12.dp, bottom = 12.dp)
                )
            }

            item {
                InstructionItem(
                    steps.step ?: "",
                    "",
                    Modifier.padding(start = 18.dp, end = 18.dp, top = 18.dp, bottom = 18.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(
    scroll: State<Float>,
    headerHeightPx: Float,
    toolbarHeightPx: Float,
    onNavigateBack: () -> Unit
) {

    val toolbarBottom = headerHeightPx - toolbarHeightPx
    val showToolbar by remember {
        derivedStateOf { scroll.value >= toolbarBottom }
    }

    AnimatedVisibility(
        visible = showToolbar,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        Box {
            TopAppBar(
                title = {}, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent, navigationIconContentColor = Color.Black
                ), windowInsets = WindowInsets(0), navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, contentDescription = null
                        )
                    }
                })
        }

    }
}

@Composable
private fun Title(
    recipeName: String, scroll: State<Float>, headerHeightPx: Float, toolbarHeightPx: Float
) {
    var titleHeightPx by remember { mutableStateOf(0f) }
    val titleHeightDp = with(LocalDensity.current) { titleHeightPx.toDp() }
    var titleWidthPx by remember { mutableStateOf(0f) }


    Text(
        text = recipeName,
        fontSize = 28.sp,
        minLines = 1,
        maxLines = 2,
        style = TextStyle(
            lineHeight = 32.sp
        ),
        overflow = TextOverflow.Ellipsis,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(end = 18.dp)
            .graphicsLayer {
                val collapseRange: Float = (headerHeightPx - toolbarHeightPx)
                val collapseFraction: Float = (scroll.value / collapseRange).coerceIn(0f, 1f)
                val scaleXY = lerp(
                    titleFontScaleStart.dp, titleFontScaleEnd.dp, collapseFraction
                )
                val titleExtraStartPadding = titleWidthPx.toDp() * (1 - scaleXY.value) / 2
                val titleYFirstInterpolatedPoint = lerp(
                    headerHeight + paddingMedium, headerHeight / 2, collapseFraction
                )
                val titleYSecondInterpolatedPoint = lerp(
                    headerHeight / 2, toolbarHeight / 2 - titleHeightDp / 2, collapseFraction
                )
                val titleY = lerp(
                    titleYFirstInterpolatedPoint, titleYSecondInterpolatedPoint, collapseFraction
                )
                val titleXFirstInterpolatedPoint = lerp(
                    titlePaddingStart,
                    (titlePaddingEnd - titleExtraStartPadding) * 5 / 4,
                    collapseFraction
                )

                val titleXSecondInterpolatedPoint = lerp(
                    (titlePaddingEnd - titleExtraStartPadding) * 5 / 4,
                    titlePaddingEnd - titleExtraStartPadding,
                    collapseFraction
                )
                val titleX = lerp(
                    titleXFirstInterpolatedPoint, titleXSecondInterpolatedPoint, collapseFraction
                )


                translationY = titleY.toPx()
                translationX = titleX.toPx()
                scaleX = scaleXY.value
                scaleY = scaleXY.value
            }
            .onGloballyPositioned {
                titleHeightPx = it.size.height.toFloat()
                titleWidthPx = it.size.width.toFloat()
            })
}

@Composable
fun InfoCard(icon: Int, label: String, value: String, modifier: Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = icon), contentDescription = "", tint = Color.Black
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = value,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = label,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )

    }
}

@Composable
fun IngredientItem(
    ingredientName: String, measurement: String, unitSize: String, modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF9FAFB))
            .padding(14.dp)
    ) {
        Text(
            text = ingredientName,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(0.7f)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Row(modifier = Modifier.weight(0.3f)) {
            Text(
                text = measurement,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier.widthIn(min = 22.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = unitSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
            )
        }
    }
}

@Composable
fun InstructionItem(instructions: String, duration: String, modifier: Modifier) {
    Text(
        text = instructions, style = MaterialTheme.typography.bodyLarge, modifier = modifier
    )
}

@Preview
@Composable
fun RecipeDetailsScreenPreview() {
}