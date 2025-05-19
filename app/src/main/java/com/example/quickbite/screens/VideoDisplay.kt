package com.example.quickbite.screens

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.quickbite.model.Video
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun VideoDisplay(video: Video) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .width(300.dp)
            .fillMaxHeight(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize()
        ) {
            Text(
                text = video.name,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (video.site == "YouTube") {
                AndroidView(
                    factory = { ctx ->
                        WebView(ctx).apply {
                            settings.javaScriptEnabled = true
                            settings.domStorageEnabled = true
                            webViewClient = WebViewClient()
                            val html = """
                                 <html>
                                     <body style="margin:0">
                                         <iframe 
                                             width="100%" 
                                             height="180" 
                                             src="https://www.youtube.com/embed/${video.key}?autoplay=0" 
                                             frameborder="0" 
                                             allowfullscreen>
                                         </iframe>
                                     </body>
                                 </html>
                             """.trimIndent()
                            Log.d(TAG, "Loading YouTube video: ${video.key}")
                            loadDataWithBaseURL("https://www.youtube.com", html, "text/html", "UTF-8", null)
                        }
                    },
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth()
                )
            } else {
                val exoPlayer = remember {
                    ExoPlayer.Builder(context).build().apply {
                        setMediaItem(MediaItem.fromUri(video.url))
                        prepare()
                    }
                }

                AndroidView(
                    factory = { PlayerView(it).apply { player = exoPlayer } },
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, video.url.toUri())
                    context.startActivity(intent)
                    Log.d(TAG, "Opening in external app: ${video.url}")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Open in YouTube")
            }
        }
    }
}
