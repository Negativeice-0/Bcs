package com.gates.bcs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gates.bcs.ui.theme.BcsTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

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
fun MyApp(modifier: Modifier = Modifier) {
    // Adding the logic to show the different screens in MyApp,
    //  and hoist/lift/elevate the state.
    var shouldShowOnboarding by remember { mutableStateOf(true)}

    Surface(modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false})
        } else {
            Greetings()
        }
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) {"1,2,3,4,5,6,7,8,9"}
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
private fun Greeting(name: String) {
    // State & MutableState hold some value and trigger UI updates(recompositions)
    //, whenever that value changes. To add state to a composable use mutable state of.
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

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the DOJO!")
        Button(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 32.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }

}
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BcsTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    BcsTheme {
        Greetings()
    }
}

@Preview
@Composable
fun MyAppPreview() {
    BcsTheme {
        MyApp(Modifier.fillMaxSize())
    }
}