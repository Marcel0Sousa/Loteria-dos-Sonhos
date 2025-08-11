package com.marcelo.loteriadossonhos.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marcelo.loteriadossonhos.R
import com.marcelo.loteriadossonhos.ui.theme.Green

@Composable
fun LotteryHeaderItemTypeCustom(
    backgroundColor: Color = Color.Transparent,
    colorLabel: Color = Color.Black,
    labelBet: String,
    @DrawableRes icon: Int = R.drawable.trevo
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .wrapContentSize()
            .background(backgroundColor)
    ) {

        Image(
            painter = painterResource(icon),
            contentDescription = stringResource(R.string.label_trevo),
            modifier = Modifier
                .size(100.dp)
                .padding(10.dp)
        )

        Text(
            text = labelBet,
            fontWeight = FontWeight.Bold,
            color = colorLabel,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.padding(top = 4.dp))

    }
}

@Preview(showBackground = true)
@Composable
private fun LotteryHeaderPreview() {
    LotteryHeaderItemTypeCustom(
        backgroundColor = Green,
        colorLabel = Color.White,
        labelBet = "Mega Sena"
    )
}