package ipca.example.newsapp.ui.articles

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage

@Composable
fun ArticleDetailView(
    modifier: Modifier = Modifier,
    id: String?,
    navController: NavController
) {
    // Usa o novo ViewModel
    val viewModel : ArticleDetailViewModel = viewModel()
    val uiState by viewModel.uiState


    LaunchedEffect(id) {
        viewModel.fetchProductDetails(id)
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else if (uiState.error != null) {
            Text(
                text = uiState.error!!,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        } else if (uiState.article != null) {

            val article = uiState.article!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título
                Text(
                    text = article.title ?: "Sem Título",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Imagem
                AsyncImage(
                    model = article.urlToImage,
                    contentDescription = article.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f) // Proporção da imagem
                        .padding(vertical = 16.dp),
                    contentScale = ContentScale.Crop
                )

                // Preço
                Text(
                    text = "Preço: ${article.price} €", // Mostra o preço
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Autor/Marca
                Text(
                    text = "Marca: ${article.author}", // Mostra a marca
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Descrição
                Text(
                    text = article.description ?: "Sem descrição.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}