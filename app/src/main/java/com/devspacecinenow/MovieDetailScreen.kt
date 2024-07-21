package com.devspacecinenow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MovieDetailSceen() {

}

@Composable
private fun MovieDetailContent(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailPreview(modifier: Modifier = Modifier) {
    MovieDetailContent()
}