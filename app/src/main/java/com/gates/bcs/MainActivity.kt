package com.gates.bcs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gates.bcs.ui.theme.BcsTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BcsTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

// This allows you to reuse a comosable like api slots
@Composable
fun MyApp(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("World", "Compose")
) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        for (name in names) {
            Greeting(name = name)
        }
    }
}

@Composable
private fun Greeting(name: String) {
    // State & MutableState hold some value and trigger UI updates(recompositions)
    //, whenever that value changes. To add state to a composable use mutablestateof.
    val expanded = remember { mutableStateOf(false)}
    // Creating the actual ui effect, such that it expands when clicked.(Using extra padding)
    // No remember required as it is a simple calculation.
    val extraPadding = if (expanded.value) 48.dp else 0.dp
    // Surface takes in a color as params
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(
            vertical = 4.dp,
            horizontal = 8.dp
        )
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello,")
                Text(text = name)
            }

            ElevatedButton(
                onClick = { expanded.value = !expanded.value },
            ) {
                Text(if(expanded.value) "Show less" else "Show more")
            }
            // ElevatedButton that wraps a Text as the ElevatedButton content.
            //
            //To achieve this you need to learn how to place a composable at the end of a row.
            // There's no alignEnd modifier so, instead, you give some weight to the composable at the start.
            // The weight modifier makes the element fill all available space, making it flexible,
            // effectively pushing away the other elements that don't have a weight, which are called inflexible.
            // It also makes the fillMaxWidth modifier redundant.

        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
private fun DefaultPreview() {
    BcsTheme {
        MyApp()
    }
}