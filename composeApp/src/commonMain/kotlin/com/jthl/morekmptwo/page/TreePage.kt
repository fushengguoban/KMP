package com.jthl.morekmptwo.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jthl.morekmptwo.fetchPlatformUser
import com.jthl.morekmptwo.sendOtherInfoPage
import com.jthl.morekmptwo.utils.loadTreeConfig
import com.jthl.morekmptwo.view.CascadeSelector
import com.jthl.morekmptwo.view.createMockData
import kotlinx.coroutines.launch

/**
 * @author wanglei
 * @date 2025/12/17 15:06
 * @Description：
 */
@Composable
fun TreePage(navController: NavHostController) {
    LaunchedEffect(Unit) {
        val loadTreeConfig = loadTreeConfig()
    }

    var showDialog by remember { mutableStateOf(false) }
    var selectedTeamsText by remember { mutableStateOf("未选择") }
    //模拟数据
    val mockData = remember { createMockData() }
    var selectedCount by remember { mutableStateOf(0) }

    var parentRect by remember { mutableStateOf(Rect.Zero) } // 存储父 View 边界
    val scope = rememberCoroutineScope()
    var name by remember { mutableStateOf("未加载！") }
    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF5F5F5)) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "KMP 三级联动选择器示例",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "已选择车队", fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = selectedTeamsText,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Button(
                        onClick = { showDialog = true },
                        modifier = Modifier.fillMaxWidth().onGloballyPositioned { coordinates ->
                            parentRect = coordinates.boundsInWindow()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF409EFF)
                        )
                    ) {
                        Text("选择车队组织")
                    }
                }

            }

            Button(onClick = {
//                scope.launch {
//                    name = fetchPlatformUser("9527")
//                }
                sendOtherInfoPage("我是从KMP过来的！")
            }) {
                Text("获取数据：$name")
            }

        }
        if (showDialog) {
//            CascadeSelectorDialog(
//                data=mockData,
//                onDismiss = { showDialog = false },
//                onConfirm = { selectedTeams ->
//                    selectedTeamsText = if (selectedTeams.isEmpty()) {
//                        "未选择"
//                    } else {
//                        selectedTeams.joinToString("、") { it.name }
//                    }
//                    showDialog = false
//                }
//            )
            CascadeSelector(
                data = mockData,
                onSelectionChange = { selectedTeams ->
                    selectedCount = selectedTeams.size
                    selectedTeamsText = if (selectedTeams.isEmpty()) {
                        "未选择"
                    } else {
                        selectedTeams.joinToString("、") { it.name }
                    }
                },
                parentRect
            )
        }
    }
}