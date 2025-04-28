package com.example.imagegenclient.ui

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.example.imagegenclient.R

@Composable
fun CheckOriginalityScreen() {
    val context = LocalContext.current

    var photoBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // --- Сначала создаем лаунчеры для камеры и галереи

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            photoBitmap = bitmap
        } else {
            Toast.makeText(context, "Не удалось сделать фото", Toast.LENGTH_SHORT).show()
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            selectedImageUri = uri
        } else {
            Toast.makeText(context, "Не удалось выбрать фото", Toast.LENGTH_SHORT).show()
        }
    }

    // --- Теперь создаем лаунчеры для запроса разрешений

    val requestCameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(null)
        } else {
            Toast.makeText(context, "Камера не разрешена", Toast.LENGTH_SHORT).show()
        }
    }

    val requestGalleryPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            galleryLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Доступ к галерее запрещён", Toast.LENGTH_SHORT).show()
        }
    }

    // --- UI (экран)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Кнопка "Сделать фото"
        Button(
            onClick = {
                val permissionCheck = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                )
                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    cameraLauncher.launch(null)
                } else {
                    requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.take_photo_button))
        }

        // Кнопка "Загрузить фото"
        Button(
            onClick = {
                val permissionCheck = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_MEDIA_IMAGES
                )
                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    galleryLauncher.launch("image/*")
                } else {
                    requestGalleryPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = stringResource(id = R.string.upload_photo_button))
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Показать сделанное фото, если оно есть
        photoBitmap?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Сделанное фото",
                modifier = Modifier.size(200.dp)
            )
        }
    }
}
