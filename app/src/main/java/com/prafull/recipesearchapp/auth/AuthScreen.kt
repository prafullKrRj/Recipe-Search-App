package com.prafull.recipesearchapp.auth

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn.getClient
import com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.prafull.recipesearchapp.MainScreens
import com.prafull.recipesearchapp.R
import com.prafull.recipesearchapp.navigateAndPopBackStack
import com.prafull.recipesearchapp.ui.theme.orange
import com.prafull.recipesearchapp.utils.Const
import org.koin.androidx.compose.getViewModel

@Composable
fun AuthScreen(navController: NavController, mAuth: FirebaseAuth) {
    val authViewModel: AuthViewModel = getViewModel()
    val context = LocalContext.current
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(Const.WEB_ID)
        .requestEmail().build()
    val googleSignInClient = getClient(context, gso)
    val signInLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            val googleSignInAccountTask = getSignedInAccountFromIntent(result.data)
            val exception = googleSignInAccountTask.exception
            if (googleSignInAccountTask.isSuccessful) {
                try {
                    val account = googleSignInAccountTask.getResult(ApiException::class.java)!!
                    mAuth.signInWithCredential(
                            GoogleAuthProvider.getCredential(
                                    account.idToken, null
                            )
                    ).addOnCompleteListener { authResultTask ->
                        if (authResultTask.isSuccessful) {
                            Toast.makeText(
                                    context, "Google SignIn Successful", Toast.LENGTH_SHORT
                            ).show()
                            navController.navigateAndPopBackStack(MainScreens.MainApp)
                        } else {
                            Toast.makeText(context, "Google SignIn Failed", Toast.LENGTH_SHORT)
                                .show()
                            authViewModel.loading = false
                        }
                    }
                } catch (e: Exception) {
                    authViewModel.loading = false
                    Toast.makeText(context, "Google SignIn Failed task", Toast.LENGTH_SHORT).show()
                }
            } else {
                authViewModel.loading = false
                Toast.makeText(context, "Google SignIn Failed $exception", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    Box(
            modifier = Modifier
                .fillMaxSize()
    ) {
        Image(
                painter = painterResource(id = R.drawable.authback),
                contentDescription = "null",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.fillMaxSize()
        )
        Column(
                Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp), verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                    text = "Welcome to",
                    fontStyle = FontStyle.Italic,
                    fontSize = 45.sp,
                    color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                    text = AnnotatedString(
                            text = "Rec",
                            spanStyle = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                            )
                    ) + AnnotatedString(
                            text = "ii"
                    ) + AnnotatedString(
                            text = "p",
                            spanStyle = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                            )
                    ) + AnnotatedString(
                            text = "ii"
                    ) + AnnotatedString(
                            text = "e",
                            spanStyle = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                            )

                    ),
                    fontSize = 50.sp,
                    color = Color.White,
                    fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Please Sign In to Continue", color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                    colors = ButtonDefaults.buttonColors(
                            containerColor = orange, contentColor = Color.White
                    ),
                    enabled = !authViewModel.loading,
                    onClick = {
                        authViewModel.loading = true
                        signInLauncher.launch(googleSignInClient.signInIntent)
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                            painter = painterResource(id = R.drawable.img),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.height(28.dp)
                    )
                    Text(text = "Continue with Google", modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}