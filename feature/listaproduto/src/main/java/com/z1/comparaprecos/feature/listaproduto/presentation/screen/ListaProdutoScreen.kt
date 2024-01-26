@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.z1.comparaprecos.feature.listaproduto.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCartCheckout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.z1.comparaprecos.common.ui.components.CustomBottomSheetDialogAviso
import com.z1.comparaprecos.common.ui.components.CustomDialogOpcoes
import com.z1.comparaprecos.common.ui.components.CustomFloatingActionButton
import com.z1.comparaprecos.common.ui.components.CustomSnackBar
import com.z1.comparaprecos.common.ui.components.CustomTopAppBar
import com.z1.comparaprecos.common.ui.components.ETipoSnackbar
import com.z1.comparaprecos.common.ui.components.Mensagem
import com.z1.comparaprecos.common.ui.theme.MediumSeaGreen
import com.z1.comparaprecos.common.util.ordenacaoOptions
import com.z1.comparaprecos.core.common.R
import com.z1.comparaprecos.feature.listaproduto.presentation.components.FormularioProduto
import com.z1.comparaprecos.feature.listaproduto.presentation.components.ListaProduto
import com.z1.comparaprecos.feature.listaproduto.presentation.components.TituloListaProduto
import com.z1.comparaprecos.feature.listaproduto.presentation.state.UiEvent
import com.z1.comparaprecos.feature.listaproduto.presentation.state.UiState
import com.z1.comparaprecos.feature.listaproduto.presentation.viewmodel.OnEvent
import java.util.concurrent.TimeUnit

@Composable
fun ListaProdutoScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    uiState: UiState,
    uiEvent: UiEvent,
    onEvent: (OnEvent) -> Unit
) {
    var isOnTopoLista by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Scaffold(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
                .systemBarsPadding(),
            topBar = {
                CustomTopAppBar(
                    elevateTopAppBar = isOnTopoLista
                ) {
                    TituloListaProduto(
                        titulo = uiState.listaCompra.titulo,
                        valorLista = uiState.listaProduto.sumOf { (it.valorProduto()) },
                        onOrdenarListaClick = {
                            onEvent(OnEvent.UpdateUiEvent(UiEvent.ShowDialogOrdenarLista))
                        }
                    )
                }
            },
            floatingActionButton = {

            }
        ) { innerPadding ->

            ListaProduto(
                innerPadding = PaddingValues(
                    top = innerPadding.calculateTopPadding(),
                    start = dimensionResource(id = R.dimen.medium),
                    end = dimensionResource(id = R.dimen.medium),
                    bottom = dimensionResource(id = R.dimen.medium)
                ),
                listaProduto = uiState.listaProduto,
                isOnTopOfList = {
                    isOnTopoLista = it
                },
                onProdutoClick = {produto ->
                    onEvent(OnEvent.ProdutoSelecionado(produto))
                }
            )
        }

        FormularioProduto(
            produtoSelecionado = uiState.produtoSelecionado,
            idListaCompra = uiState.listaCompra.id,
            onAdicionarProdutoClick = { produto ->
                if (produto.id == 0L) onEvent(OnEvent.InsertProduto(produto))
                else onEvent(OnEvent.UpdateProduto(produto))
            },
            onCancelarEdicaoProduto = { onEvent(OnEvent.ProdutoSelecionado(null)) },
            onDeletarProdutoClick = { onEvent(OnEvent.DeleteProduto(it))}
        )
    }

    when (uiEvent) {
        is UiEvent.Default -> Unit
        is UiEvent.Success -> Unit
        is UiEvent.Error -> {
            CustomBottomSheetDialogAviso(
                titulo = stringResource(id = R.string.label_atencao),
                mensagem = uiState.produtoJaExiste?.run {
                    uiEvent.message.asString()
                } ?: "",
                onDismissRequest = {
                    onEvent(OnEvent.UpdateQuantidadeProdutoExistente(null))
                },
                textoBotaoPositivo = stringResource(id = R.string.label_sim),
                onAcaoPositivaClick = {
                    onEvent(OnEvent.UpdateQuantidadeProdutoExistente(uiState.produtoJaExiste))
                },
                textoBotaoNegativo = stringResource(id = R.string.label_nao),
                onAcaoNegativaClick = {
                    onEvent(OnEvent.UpdateQuantidadeProdutoExistente(null))
                }
            )
        }
        is UiEvent.ShowSnackbar -> {
            val message = Mensagem(
                uiEvent.message.asString(),
                ETipoSnackbar.SUCESSO
            )
            CustomSnackBar(
                modifier = Modifier.width(220.dp),
                mensagem = message, // aqui vai a event.message do uiEvent (corrigir lógica)
                duracao = TimeUnit.SECONDS.toMillis(2),
                onFimShowMensagem = {
                    onEvent(OnEvent.UpdateUiEvent(UiEvent.Default))
                }
            )
        }
        is UiEvent.ShowDialogOrdenarLista -> {
            CustomDialogOpcoes(
                title = stringResource(id = R.string.label_ordenacao_lista),
                optionList = ordenacaoOptions,
                atualSelectedOption = uiState.ordenacaoSelecionada.id,
                onAtualSelectedOptionClick = {
                    onEvent(OnEvent.ChangeOrdenacaoLista(it))
                }
            )
        }
        is UiEvent.NavigateUp -> navigateUp()
        else -> Unit
    }
}

@Composable
private fun PesquisarProdutoActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    CustomFloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.secondary,
        iconTint = MaterialTheme.colorScheme.onSecondary,
        imageVector = Icons.Rounded.Search
    )
}