package com.example.randomuser.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.randomuser.R
import com.example.randomuser.domain.Dob
import com.example.randomuser.domain.Location
import com.example.randomuser.domain.Name
import com.example.randomuser.domain.Picture
import com.example.randomuser.domain.User
import com.example.randomuser.ui.theme.RandomUserTheme


@Composable
fun AllUsersScreen(
    modifier: Modifier = Modifier,
    users: List<User>,
    usersListState: LazyListState,
    onAddUserButtonClicked: () -> Unit,
    onUserCardClicked: (user: User) -> Unit,
){
    Scaffold(
        modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddUserButtonClicked() },
                content = { Icon(
                    painterResource(R.drawable.ic_add),
                    "Add user button"
                ) },
                containerColor = MaterialTheme.colorScheme.primary,
            )
        }
    ) { padding ->
        LazyColumn(
            Modifier.fillMaxSize(),
            state = usersListState,
        ) {
            items(users.size) { index ->
                UserCard(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .shadow(8.dp),
                    user = users[index],
                    onClick = onUserCardClicked
                )
            }
        }
        padding
    }
}

@Composable
fun UserCard(modifier: Modifier = Modifier, user: User, onClick: (user: User) -> Unit) {
    Row(
        modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable(
                onClick = { onClick(user) }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
            )

            AsyncImage(
                modifier = Modifier.size(130.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.picture.medium)
                    .build(),
                contentDescription = "${user.name.first}'s photo",
            )
        }


        Column(
            modifier = Modifier.height(100.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(user.name.first + " " + user.name.last, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleMedium)
            Text(user.phone, style = MaterialTheme.typography.bodySmall)
            Text(user.nat, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview
@Composable
private fun AllUsersScreenPreview() {
    val users = List(4) { 
        User(
            id = 1,
            gender = "male",
            name = Name(title = "Mr", first = "John", last = "Doe"),
            location = Location(
                streetNumber = 123,
                streetName = "Main St",
                city = "New York",
                state = "New York",
                country = "USA",
                postcode = "10001",
                latitude = "40.7128",
                longitude = "-74.0060",
                timezoneOffset = "-5:00",
                timezoneDescription = "Eastern Time"),
            email = "john.doe@example.com",
            dob = Dob(date = "1990-01-01", age = 34),
            phone = "123-456-7890",
            picture = Picture(
                large = "https://randomuser.me/api/portraits/men/75.jpg",
                medium = "https://randomuser.me/api/portraits/med/men/75.jpg",
                thumbnail = "https://randomuser.me/api/portraits/thumb/men/75.jpg"
            ),
            nat = "US"
        )
    }
    RandomUserTheme {
        Surface(Modifier.background(MaterialTheme.colorScheme.surface)) {
            AllUsersScreen(
                users = users,
                onAddUserButtonClicked = {},
                onUserCardClicked = {},
                usersListState = LazyListState()
            )
        }
    }
}
