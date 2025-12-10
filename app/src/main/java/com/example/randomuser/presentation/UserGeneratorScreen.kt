package com.example.randomuser.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.randomuser.R
import com.example.randomuser.domain.ApiResponse
import com.example.randomuser.domain.Dob
import com.example.randomuser.domain.Location
import com.example.randomuser.domain.Name
import com.example.randomuser.domain.Picture
import com.example.randomuser.domain.User
import com.example.randomuser.ui.theme.RandomUserTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserGeneratorScreen(
    modifier: Modifier = Modifier,
    userApiResponse: ApiResponse<User>,
    genders: Array<String>,
    nationalities: Map<String, String>,
    onUserGeneration: (String, String) -> Unit,
    onNavigateBack: () -> Unit
) {
    var genderMenuExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    var selectedGender by rememberSaveable {
        mutableStateOf(genders[0])
    }

    var nationalityMenuExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    var selectedNationalityKey by rememberSaveable {
        mutableStateOf(nationalities.keys.first())
    }

    var isButtonClicked by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                content = {
                    IconButton(
                        modifier = Modifier.align(Alignment.CenterStart),
                        onClick = { onNavigateBack() },
                        content = {
                            Icon(
                                painterResource(R.drawable.ic_arrow_back),
                                null,
                                Modifier.size(16.dp)
                            )
                        }
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth(),
                        text = "Generate user",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                },
            )
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                MMenuBox(
                    labelText = "Select gender:",
                    selectedItem = selectedGender,
                    expanded = genderMenuExpanded,
                    menuItems = genders,
                    onExpandedChange = { genderMenuExpanded = it },
                    onDismissRequest = { genderMenuExpanded = false },
                    onItemClick = { selectedGender = it}
                )

                MMenuBox(
                    labelText = "Select nationality:",
                    selectedItem = nationalities[selectedNationalityKey] ?: "",
                    expanded = nationalityMenuExpanded,
                    menuItems = nationalities.values.toTypedArray(),
                    onExpandedChange = { nationalityMenuExpanded = it },
                    onDismissRequest = { nationalityMenuExpanded = false },
                    onItemClick = { selectedNationality ->
                        selectedNationalityKey = nationalities.filterValues { it == selectedNationality }.keys.first()
                    }
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                isButtonClicked = true
                onUserGeneration(selectedGender, selectedNationalityKey)
            },
            enabled = (userApiResponse is ApiResponse.Loading<*> && !isButtonClicked) || userApiResponse !is ApiResponse.Loading<*>,
            content = {
                when (userApiResponse) {
                    is ApiResponse.Loading<*> if isButtonClicked -> {
                        CircularProgressIndicator()
                    }

                    is ApiResponse.Error -> {
                        isButtonClicked = false
                        Text(
                            text = "TRY AGAIN"
                        )
                    }

                    else -> {
                        Text(
                            text = "GENERATE"
                        )
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MMenuBox(
    labelText: String,
    expanded: Boolean,
    menuItems: Array<String>,
    onExpandedChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    selectedItem: String,
    onItemClick: (String) -> Unit
) {
    Text(
        modifier = Modifier.padding(vertical = 16.dp),
        text = labelText,
        style = MaterialTheme.typography.titleMedium
    )
    ExposedDropdownMenuBox(
        modifier = Modifier.fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = onExpandedChange
    ) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest
        ) {
            menuItems.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onItemClick(item)
                        onDismissRequest()
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun UserGeneratorScreenPreview() {
    RandomUserTheme {
        Surface(Modifier.background(Color.White)) {
            UserGeneratorScreen(
                modifier = Modifier.fillMaxSize(),
                userApiResponse = ApiResponse.Success(
                    User(
                        id = 0,
                        gender = "male",
                        name = Name(
                            title = "Mr",
                            first = "",
                            last = ""
                        ),
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
                        dob = Dob("", 10),
                        phone = "432424",
                        picture = Picture("", "", ""),
                        nat = "",
                    )
                ),
                genders = stringArrayResource(id = R.array.genders),
                nationalities = mapOf(
                    *
                    stringArrayResource(R.array.nats)
                        .zip(stringArrayResource(R.array.nationalities))
                        .toTypedArray(),
                ),
                onUserGeneration = { _, _ -> },
                onNavigateBack = {}
            )
        }
    }
}

