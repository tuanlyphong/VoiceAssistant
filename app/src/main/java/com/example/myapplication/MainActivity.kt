package com.example.myapplication
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.foundation.layout.padding


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VoiceInputScreen(this)
                }
            }
        }
    }
}

@Composable
fun VoiceInputScreen(activity: Activity) {
    val recognizedText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { activity.startVoiceInput() }) {
            Text(text = "Voice Input")
        }
        if (recognizedText.isNotBlank()) {
            Text(
                text = "You said: $recognizedText",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

fun Activity.startVoiceInput() {
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi-VN") // Vietnamese language
    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Đang nghe...") // Prompt for the user

    try {
        startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
    } catch (e: ActivityNotFoundException) {
        // Speech to text not supported, handle error
        // You can display a toast or a message to the user
    }
}

const val REQUEST_CODE_SPEECH_INPUT = 100

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}
