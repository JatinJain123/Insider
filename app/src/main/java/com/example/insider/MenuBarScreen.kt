package com.example.insider

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import coil.compose.rememberAsyncImagePainter

@Composable
fun MenuBarScreen(
    navigateBackToMainScreen: () -> Unit,
    loginStatus: MutableState<LoginStatus>,
    userProfileData: MutableState<UserProfileData>
){
    val userName = remember { mutableStateOf("") }
    val passWord = remember { mutableStateOf("") }

    val context: Context = LocalContext.current
    val scope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        GoogleSignInUtils.doGoogleSignIn(
            context = context,
            scope = scope,
            launcher = null,
            onLogin =  { it ->
                userProfileData.value.name = it?.displayName
                userProfileData.value.email = it?.email
                userProfileData.value.imageId = it?.photoUrl.toString()
                loginStatus.value.loginStatus = true
                Toast.makeText(context, "Login Successfully!!", Toast.LENGTH_SHORT).show()
                navigateBackToMainScreen()
            }
        )
    }

    if(!loginStatus.value.loginStatus) {
        Dialog(
            onDismissRequest = navigateBackToMainScreen,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ){
            val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
            val window = dialogWindowProvider.window
            window.setDimAmount(0.1f)

            Box(
                modifier = Modifier
                    .size(280.dp, 470.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF4A90E2), // Light Blue
                                Color(0xFF2A6EBB)  // Darker Blue
                            )
                        )
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.75f)
                        .clip(
                            shape = RoundedCornerShape(
                                topStart = 45.dp,
                                topEnd = 45.dp,
                                bottomStart = 12.dp,
                                bottomEnd = 12.dp
                            )
                        )
                        .background(color = Color.White)
                        .align(Alignment.BottomCenter)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "WELCOME",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4A90E2),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = userName.value,
                        onValueChange = { userName.value = it },
                        label = { Text("Username", fontWeight = FontWeight.Bold, color = Color.Black)},
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(vertical = 4.dp)
                    )

                    OutlinedTextField(
                        value = passWord.value,
                        onValueChange = { passWord.value = it },
                        label = { Text("Password", fontWeight = FontWeight.Bold, color = Color.Black) },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A90E2)),
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .padding(vertical = 8.dp)
                    ) {
                        Text(text = "LOGIN", color = Color.White, fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            GoogleSignInUtils.doGoogleSignIn(
                                context = context,
                                scope = scope,
                                launcher = launcher,
                                onLogin =  {
                                    userProfileData.value.name = it?.displayName
                                    userProfileData.value.email = it?.email
                                    userProfileData.value.imageId = it?.photoUrl.toString()
                                    loginStatus.value.loginStatus = true
                                    Toast.makeText(context, "Login Successfully!!", Toast.LENGTH_SHORT).show()
                                    navigateBackToMainScreen()
                                }
                            )
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7ec8e3)),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(vertical = 8.dp)
                            .height(40.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(R.drawable.google_icon_image),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(36.dp)
                            )

                            Spacer(Modifier.weight(1f))

                            Text(
                                text = "LOGIN with google",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.25f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Don't you have account?",
                            fontSize = 16.sp,
                            color = Color.White
                        )
                        Spacer(Modifier.height(8.dp))

                        Button(
                            onClick = {
                                GoogleSignInUtils.doGoogleSignIn(
                                    context = context,
                                    scope = scope,
                                    launcher = launcher,
                                    onLogin =  {
                                        userProfileData.value.name = it?.displayName
                                        userProfileData.value.email = it?.email
                                        userProfileData.value.imageId = it?.photoUrl.toString()
                                        loginStatus.value.loginStatus = true
                                        Toast.makeText(context, "Login Successfully!!", Toast.LENGTH_SHORT).show()
                                        navigateBackToMainScreen()
                                    }
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(text = "Sign up",
                                color = Color(0xFF4A90E2),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    } else {
        Dialog (
            onDismissRequest = navigateBackToMainScreen,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
            val window = dialogWindowProvider.window
            window.setDimAmount(0.1f)

            Box(
                modifier = Modifier
                    .size(width = 320.dp, height = 240.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.55f)
                        .clip(
                            RoundedCornerShape(
                                topEnd = 12.dp,
                                topStart = 12.dp
                            )
                        )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        if (userProfileData.value.imageId?.isBlank() == true) {
                            Image(
                                painter = painterResource(R.drawable.account_circle),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(90.dp)
                            )
                        } else {
                            Image(
                                painter = rememberAsyncImagePainter(Uri.parse(userProfileData.value.imageId)),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(90.dp)
                                    .clip(RoundedCornerShape(50))
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = if (userProfileData.value.name?.isBlank() == true) "No Name" else userProfileData.value.name.toString(),
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black,
                                textAlign = TextAlign.Start,
                                fontSize = 24.sp
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = if (userProfileData.value.email?.isBlank() == true) "No Email" else userProfileData.value.email.toString(),
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                color = Color.Gray,
                            )
                        }
                    }
                }

                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxHeight(0.45f)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                ) {
                    HorizontalDivider(
                        color = Color.DarkGray,
                        thickness = 2.dp,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Settings",
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .clickable { /* Navigate to settings screen */ }
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                    )

                    Text(
                        text = "About",
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .clickable { /* Navigate to privacy settings */ }
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                    )

                    Text(
                        text = "Logout ->",
                        fontWeight = FontWeight.Medium,
                        color = Color.Red,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .clickable {
                                // Handle logout logic
                            }
                            .padding(vertical = 2.dp, horizontal = 8.dp)
                    )
                }
            }
        }
    }
}