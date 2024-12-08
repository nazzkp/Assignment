package com.example.assignment.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.assignment.model.dataClass.DrugItem
import com.example.assignment.ui.theme.Purple40
import com.example.assignment.viewModel.HomeViewModel
import com.example.orderit.Utils.Utilities
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userName: String?,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text(text = "Assignment") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Purple40
                )
            )
        },
        content = { innerPadding ->
            HomeContent(innerPadding, userName, viewModel)
        }
    )
}

@Composable
fun HomeContent(innerPadding: PaddingValues, userName: String?, viewModel: HomeViewModel) {
    val currentTime = remember { LocalTime.now() }
    val timeFormatter = remember { DateTimeFormatter.ofPattern("hh:mm a") }
    val loading = remember { mutableStateOf(true) }
    val showDetails = remember { mutableStateOf(false) }
    val medicineList = viewModel.medicineData.observeAsState()
    val status by viewModel.operationStatus.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getDoseDetails()
    }
    val selectedItem = remember { mutableStateOf<DrugItem?>(null) }
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Hi $userName ${currentTime.format(timeFormatter)}",
            modifier = Modifier
                .padding(top = 8.dp)
                .semantics {
                    this.contentDescription = "Greeting"
                },
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Purple40
            )
        )
        Box(
            Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            if (loading.value) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.Center)
                        .testTag("Progress Bar")
                )
            }
            if (medicineList.value != null) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(medicineList.value!!) { item ->
                        Medicine(item, showDetails, selectedItem)
                        loading.value = false
                    }
                }
            }
        }
    }
    if (showDetails.value) {
        ShowDetailsDialog(selectedItem.value!!, showDetails, innerPadding)
    }

    if (status.isNotEmpty()) {
        loading.value = false
        Utilities.showToast(LocalContext.current,status)
    }
}

@Composable
fun Medicine(
    item: DrugItem,
    showDialog: MutableState<Boolean>,
    selectedItem: MutableState<DrugItem?>
) {
    Column(
        modifier = Modifier
            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
            .fillMaxWidth()
            .clickable {
                selectedItem.value = item
                showDialog.value = true
            }
    ) {
        Text(text = "Name: ${item.name}", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Dose: ${item.dose}", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Strength:  ${item.strength}", fontSize = 20.sp)
    }
}

@Composable
fun ShowDetailsDialog(
    item: DrugItem,
    showDialog: MutableState<Boolean>,
    innerPadding: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(vertical = 50.dp, horizontal = 25.dp)
            .clickable(enabled = false) {},
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Purple40,
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(RoundedCornerShape(16.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            WindowTexts("Problem", item.problem)
            WindowTexts("Drug Name", item.name)
            WindowTexts("Dose", item.dose.toString())
            WindowTexts("Strength", item.strength.toString())
            Button(
                onClick = {
                    showDialog.value = false
                },
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = 8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("OK")
            }
        }
    }
}

@Composable
fun WindowTexts(title: String, value: String) {
    Text(
        text = "$title : $value",
        modifier = Modifier.padding(top = 8.dp),
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    )
}
