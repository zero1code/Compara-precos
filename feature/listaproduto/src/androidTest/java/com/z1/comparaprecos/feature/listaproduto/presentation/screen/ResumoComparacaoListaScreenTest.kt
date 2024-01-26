package com.z1.comparaprecos.feature.listaproduto.presentation.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.test.swipeUp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.z1.comparaprecos.common.extensions.getPercentageDifference
import com.z1.comparaprecos.common.extensions.toMoedaLocal
import com.z1.comparaprecos.common.ui.theme.ComparaPrecosTheme
import com.z1.comparaprecos.common.ui.theme.CoralRed
import com.z1.comparaprecos.common.ui.theme.MediumSeaGreen
import com.z1.comparaprecos.core.common.R
import com.z1.comparaprecos.testing.assertTextColor
import com.z1.comparaprecos.testing.data.listaProdutoDataTest
import com.z1.comparaprecos.testing.data.listaProdutoDataTest2
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ResumoComparacaoListaScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val listaProduto = "Lista compra" to listaProdutoDataTest
    private val listaProdutoComparada = "Lista compra comparada" to listaProdutoDataTest2
    
    private fun init() {
        composeTestRule.setContent { 
            ComparaPrecosTheme {
                ResumoComparacaoListaScreen(
                    listaProduto = listaProduto,
                    listaProdutoComparada = listaProdutoComparada
                )
            }
        }
    }

    @Test
    fun shouldShowDetailsOfTwoListaCompraSelectedByUser() {
        //Given - Dado
        val listaCompraSoma = listaProduto.second.sumOf { it.valorProduto() }.toMoedaLocal()
        val listaCompraComparadaSoma = listaProdutoComparada.second.sumOf { it.valorProduto() }.toMoedaLocal()

        //When - Quando
        init()

        //Then - Entao
        composeTestRule.onNodeWithText("Lista compra").assertIsDisplayed()
        composeTestRule.onNodeWithText("3").assertIsDisplayed()
        composeTestRule.onNodeWithText("7").assertIsDisplayed()

        composeTestRule.onNodeWithText(listaCompraSoma).assertIsDisplayed()

        composeTestRule.onNodeWithText("Lista compra comparada").assertIsDisplayed()
        composeTestRule.onNodeWithText("4").assertIsDisplayed()
        composeTestRule.onNodeWithText("8").assertIsDisplayed()
        composeTestRule.onNodeWithText(listaCompraComparadaSoma).assertIsDisplayed()
    }

    @Test
    fun shouldShowDetailsOfProdutosInSameListaCompra() {
        //Given - Dado
        val listaProdutoIgual = listaProduto.second.filter { produto ->
            listaProdutoComparada.second.any { produtoComparado ->
                produto.nomeProduto == produtoComparado.nomeProduto
            }
        }

        val listaProdutoComparadaIgual = listaProdutoComparada.second.filter { produtoComparado ->
            listaProduto.second.any { produto ->
                produto.nomeProduto == produtoComparado.nomeProduto
            }
        }
        val valorListaAtual = listaProdutoIgual.sumOf { it.valorProduto() }
        val valorListaComparada = listaProdutoComparadaIgual.sumOf { it.valorProduto() }
        val diferencaPreco = valorListaAtual.minus(valorListaComparada)
        val diferencaPorcentagem = valorListaAtual.getPercentageDifference(valorListaComparada)

        val produtosNaMesmaLista =
            composeTestRule.activity
                .getString(R.string.label_desc_somando_produtos_nas_listas, listaProdutoIgual.size)

        //When - Quando
        init()

        //Then - Entao
        composeTestRule.onNodeWithText(produtosNaMesmaLista).assertIsDisplayed()
        composeTestRule.onNodeWithText(valorListaAtual.toMoedaLocal()).assertIsDisplayed()
        composeTestRule.onNodeWithText(valorListaComparada.toMoedaLocal()).assertIsDisplayed()
        composeTestRule.onNodeWithText(diferencaPreco.toMoedaLocal()).assertIsDisplayed()

        composeTestRule.onNodeWithText(diferencaPorcentagem, useUnmergedTree = true)
            .assertIsDisplayed()
            .assertTextColor(CoralRed)
    }

    @Test
    fun shouldShowDetailsOfAllProdutoInTwoListaCompra() {
        //Given - Dado
        val valorListaAtual = listaProduto.second.sumOf { it.valorProduto() }
        val valorListaComparada = listaProdutoComparada.second.sumOf { it.valorProduto() }
        val diferencaPreco = valorListaAtual.minus(valorListaComparada)
        val diferencaPorcentagem = valorListaAtual.getPercentageDifference(valorListaComparada)

        val somandoTodosOsProdutos =
            composeTestRule.activity
                .getString(R.string.label_desc_somando_todos_produtos)

        //When - Quando
        init()
        composeTestRule.onRoot().performTouchInput { swipeUp() }
//        composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentTree")

        //Then - Entao
        composeTestRule.onNodeWithText(somandoTodosOsProdutos).assertIsDisplayed()
        composeTestRule.onNodeWithText(valorListaAtual.toMoedaLocal()).assertIsDisplayed()
        composeTestRule.onNodeWithText(valorListaComparada.toMoedaLocal()).assertIsDisplayed()
        composeTestRule.onNodeWithText(diferencaPreco.toMoedaLocal()).assertIsDisplayed()
        composeTestRule.onNodeWithText(diferencaPorcentagem, useUnmergedTree = true)
            .assertIsDisplayed()
            .assertTextColor(MediumSeaGreen)
    }
}