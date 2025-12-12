package com.example.randomuser.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.randomuser.R
import com.example.randomuser.domain.Location
import com.example.randomuser.domain.Picture
import com.example.randomuser.domain.User
import com.example.randomuser.presentation.linearGradient
import com.example.randomuser.presentation.setColor
import com.example.randomuser.ui.theme.RandomUserTheme

@Composable
fun UserDetailsScreen(
    modifier: Modifier = Modifier,
    user: User,
    onNavigateBack: () -> Unit,
) {
    var selectedTabIndex by rememberSaveable() {
        mutableIntStateOf(0)
    }
    val userInfoTabs = remember {
        listOf(
            UserInfoTab(
                index = 0,
                iconId = R.drawable.ic_person,
                content = mapOf(
                    Pair("First name:", user.firstName),
                    Pair("Last name:", user.lastName),
                    Pair("Gender:", user.gender),
                    Pair("Age:", user.age.toString()),
                    Pair("Date of birth:", user.dateOfBirth)
                )
            ),
            UserInfoTab(
                index = 1,
                iconId = R.drawable.ic_phone,
                content = mapOf(
                    Pair("Phone:", user.phone)
                )
            ),
            UserInfoTab(
                index = 2,
                iconId = R.drawable.ic_email,
                content = mapOf(
                    Pair("Email:", user.email)
                )
            ),
            UserInfoTab(
                index = 3,
                iconId = R.drawable.ic_location,
                content = mapOf(
                    Pair("Country:", user.location.country),
                    Pair("City:", user.location.city),
                    Pair("Street:", "${user.location.streetName} ${user.location.streetNumber}"),
                    Pair("Postcode:", user.location.postcode)
                )
            )
        )
    }

    Box(
        modifier
    ) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.Start) {
            ImageCard(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth(),
                imageUrl = user.picture.large,
                firstName = user.firstName,
                lastName = user.lastName
            )
            UserInfoTabRow(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .padding(start = 16.dp, end = 16.dp)
                    .shadow(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer),
                selectedTabIndex = selectedTabIndex,
                tabs = userInfoTabs,
                onSelectedTabChanged = { newIndex ->
                    selectedTabIndex = newIndex
                }
            )
        }

        IconButton(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
                .clip(CircleShape)
                .size(32.dp)
                .background(MaterialTheme.colorScheme.surface),
            onClick = onNavigateBack,
            content = {
                Icon(
                    painterResource(R.drawable.ic_arrow_back),
                    "Navigate back",
                    Modifier.size(24.dp)
                )
            }
        )
    }

}

@Composable
fun ImageCard(
    modifier: Modifier,
    imageUrl: String,
    firstName: String,
    lastName: String
) {
    Box(modifier, contentAlignment = Alignment.TopCenter) {
        Box(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.primary.linearGradient()
                )
                .fillMaxWidth()
                .fillMaxSize(0.4f)
        )

        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
                AsyncImage(
                    modifier = Modifier
                        .size(150.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .crossfade(true)
                        .data(imageUrl)
                        .build(),
                    contentDescription = "$firstName's photo",
                )
            }

            Text(
                modifier = Modifier.padding(vertical = 24.dp),
                text = "Hi how are you today?\nI'm",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                "$firstName $lastName",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

data class UserInfoTab(
    val index: Int,
    val iconId: Int,
    val content: Map<String, String>
)

@Composable
fun UserInfoTabRow(
    modifier: Modifier = Modifier,
    tabs: List<UserInfoTab>,
    selectedTabIndex: Int,
    onSelectedTabChanged: (Int) -> Unit,
) {
    Column(
        modifier
    ) {
        PrimaryTabRow(
            modifier = Modifier.background(
                brush = MaterialTheme.colorScheme.primary.linearGradient()
            ),
            indicator = {
                TabRowDefaults.SecondaryIndicator(
                    color = MaterialTheme.colorScheme.surfaceDim,
                    modifier = Modifier.tabIndicatorOffset(selectedTabIndex)
                )
            },
            divider = {},
            containerColor = Color.Transparent,
            selectedTabIndex = selectedTabIndex,
            tabs = {
                tabs.forEach {
                    Tab(
                        modifier = Modifier
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                            .background(if (selectedTabIndex == it.index) MaterialTheme.colorScheme.surfaceContainer else Color.Transparent),
                        selected = selectedTabIndex == it.index,
                        onClick = { onSelectedTabChanged(it.index) },
                        icon = {
                            Icon(
                                painter = painterResource(id = it.iconId),
                                tint = if (selectedTabIndex == it.index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        )
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            tabs[selectedTabIndex].content.forEach { (label, text) ->
                Text(buildAnnotatedString {
                    withStyle(
                        MaterialTheme.typography.titleMedium.setColor(MaterialTheme.colorScheme.primary)
                            .toSpanStyle()
                    ) {
                        append("$label  ")
                    }
                    withStyle(MaterialTheme.typography.bodyMedium.toSpanStyle()) {
                        append(text)
                    }
                })
            }
        }
    }
}

@Preview
@Composable
private fun UserDetailScreenPreivew() {
    RandomUserTheme {
        Surface {
            UserDetailsScreen(
                modifier = Modifier.fillMaxSize(),
                User(
                    id = 0,
                    gender = "male",
                    firstName = "",
                    lastName = "",
                    location = Location(
                        streetNumber = 10,
                        streetName = "",
                        city = "",
                        state = "",
                        country = "",
                        postcode = "",
                        latitude = "",
                        longitude = "",
                        timezoneOffset = "",
                        timezoneDescription = "",
                    ),
                    email = "",
                    dateOfBirth = "",
                    age = 10,
                    phone = "432424",
                    picture = Picture("", "", ""),
                    nat = "",
                ),
                onNavigateBack = {}
            )
        }
    }
}