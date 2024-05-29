package com.aimicor.uniflow.marsestates.presentation.estateviewmodel

/*
MIT License

Copyright (c) 2024 Aimicor Ltd.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

import androidx.lifecycle.viewModelScope
import com.aimicor.uniflow.Uniflow
import com.aimicor.uniflow.UniflowViewModel
import com.aimicor.uniflow.marsestates.domain.MarsEstateUseCase
import com.aimicor.uniflow.marsestates.ui.content.MarsEstateEvent
import com.aimicor.uniflow.marsestates.ui.content.MarsEstateUiState
import kotlinx.coroutines.launch

interface MarsEstateUniflow :
    Uniflow<MarsEstateEvent, MarsEstateUiState, MarsEstateSideEffect>

open class MarsEstateViewModel(
    private val estateUseCase: MarsEstateUseCase
) : MarsEstateUniflow,
    UniflowViewModel<MarsEstateEvent, MarsEstateUiState, MarsEstateSideEffect>(
        MarsEstateUiState(emptyList()),
    ) {
        init {
            viewModelScope.launch {
                estateUseCase()
                    .onSuccess { setUiState { MarsEstateUiState(it) } }
                    .onFailure { throw (it) }
            }
        }


    override fun handleEvent(event: MarsEstateEvent) {
        when (event) {
            MarsEstateEvent.OnClose -> sendSideEffect { MarsEstateSideEffect.Close }
        }
    }
}
