package com.z1.comparaprecos.feature.listaproduto.presentation.components

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.z1.comparaprecos.common.ui.theme.ComparaPrecosTheme
import com.z1.comparaprecos.core.model.Produto
import com.z1.comparaprecos.testing.data.listaProdutoDataTest
import com.z1.comparaprecos.core.common.R
import org.junit.Rule
import org.junit.Test
import java.text.DecimalFormat

class FormularioProdutoTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private fun init(produto: Produto?) {
        composeTestRule.setContent {
            ComparaPrecosTheme {
                FormularioProduto(
                    produtoSelecionado = produto,
                    idListaCompra = 0,
                    onAdicionarProdutoClick = {},
                    onDeletarProdutoClick = {},
                    onCancelarEdicaoProduto = {}
                )
            }
        }
    }

    @Test
    fun shouldShowAddProdutoNameWhenUserAddingProduto() {
        //When - Quando
        init(null)

        //Then - Entao
        composeTestRule.onNodeWithText("Adicionar produto").assertIsDisplayed()
    }

    @Test
    fun shouldShowEditProdutoNameWhenUserEditingProduto() {
        //Given - Dado
        val produto = listaProdutoDataTest[0]

        //When - Quando
        init(produto)

        //Then - Entao
        composeTestRule.onNodeWithText("Editar produto").assertIsDisplayed()
    }

    @Test
    fun shouldShowPesoNameWhenUserClickInIsPesoCheckBox() {
        //Given - Dado
        val symbols = DecimalFormat().decimalFormatSymbols
        val decimalSeparator = symbols.decimalSeparator
        init(null)

        //When - Quando
        composeTestRule.onNodeWithText("Peso").performClick()

        //Then - Entao
        composeTestRule.onNodeWithText("0${decimalSeparator}000 kg").assertIsDisplayed()
    }

    @Test
    fun shouldShowRedBorderWhenUserClickAddProdutoWithoutName() {
        //Given - Dado
        val produtoTexto = composeTestRule.activity.resources.getQuantityString(R.plurals.label_plural_produto, 0)
        init(null)

        //When - Quando
        composeTestRule.onNodeWithText(produtoTexto).performClick()
        composeTestRule.onNodeWithText("Adicionar produto").performClick()
        //Then - Entao
        composeTestRule.onNodeWithText("Esse produto é …").assertIsDisplayed()
    }

    @Test
    fun shouldShowNewQuantityWhenUserClickAddQuantity() {
        //Given - Dado
        init(null)

        //When - Quando
        composeTestRule.onNodeWithContentDescription("Adicionar quantidade").performClick()

        //Then - Entao
        composeTestRule.onNodeWithText("2").assertIsDisplayed()
    }

    @Test
    fun shouldShowNewQuantityWhenUserClickRemoveQuantity() {
        //Given - Dado
        init(null)

        //When - Quando
        composeTestRule.onNodeWithContentDescription("Adicionar quantidade").performClick()
        composeTestRule.onNodeWithContentDescription("Remover quantidade").performClick()

        //Then - Entao
        composeTestRule.onNodeWithText("1").assertIsDisplayed()
    }
}