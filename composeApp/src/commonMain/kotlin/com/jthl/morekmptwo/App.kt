package com.jthl.morekmptwo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jthl.morekmptwo.net.UserRepository
import com.jthl.morekmptwo.net.onError
import com.jthl.morekmptwo.net.onSuccess
import com.jthl.morekmptwo.page.TreePage
import com.jthl.morekmptwo.resources.NotoSansSC_Regular
import com.jthl.morekmptwo.resources.Res
import com.jthl.morekmptwo.resources.compose_multiplatform
import com.jthl.morekmptwo.resources.icon_people_bg
import com.jthl.morekmptwo.utils.IconWithMsg
import com.jthl.morekmptwo.utils.PulseIndicator
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitDialogSettings
import io.github.vinceglb.filekit.dialogs.FileKitMode
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.dialogs.openFilePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import navigation.DetailScreen
import navigation.HomeScreen
import navigation.TreePage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App(navController: NavHostController = rememberNavController()) { // 定义中文字体
// 加载中文字体
    val chineseFont = FontFamily(
        org.jetbrains.compose.resources.Font(
            Res.font.NotoSansSC_Regular
        )
    )

    MaterialTheme(
        typography = androidx.compose.material3.Typography().copy(
            displayLarge = androidx.compose.material3.Typography().displayLarge.copy(fontFamily = chineseFont),
            displayMedium = androidx.compose.material3.Typography().displayMedium.copy(fontFamily = chineseFont),
            displaySmall = androidx.compose.material3.Typography().displaySmall.copy(fontFamily = chineseFont),
            headlineLarge = androidx.compose.material3.Typography().headlineLarge.copy(fontFamily = chineseFont),
            headlineMedium = androidx.compose.material3.Typography().headlineMedium.copy(fontFamily = chineseFont),
            headlineSmall = androidx.compose.material3.Typography().headlineSmall.copy(fontFamily = chineseFont),
            titleLarge = androidx.compose.material3.Typography().titleLarge.copy(fontFamily = chineseFont),
            titleMedium = androidx.compose.material3.Typography().titleMedium.copy(fontFamily = chineseFont),
            titleSmall = androidx.compose.material3.Typography().titleSmall.copy(fontFamily = chineseFont),
            bodyLarge = androidx.compose.material3.Typography().bodyLarge.copy(fontFamily = chineseFont),
            bodyMedium = androidx.compose.material3.Typography().bodyMedium.copy(fontFamily = chineseFont),
            bodySmall = androidx.compose.material3.Typography().bodySmall.copy(fontFamily = chineseFont),
            labelLarge = androidx.compose.material3.Typography().labelLarge.copy(fontFamily = chineseFont),
            labelMedium = androidx.compose.material3.Typography().labelMedium.copy(fontFamily = chineseFont),
            labelSmall = androidx.compose.material3.Typography().labelSmall.copy(fontFamily = chineseFont),
        )
    ) {
        NavHost(
            navController = navController,
            startDestination = HomeScreen
        ) {
            composable<HomeScreen> {
                HomePage(navController)
            }
            composable<DetailScreen> {
                DetailPage(navController)
            }

            composable<TreePage> {
                TreePage(navController)
            }
        }

    }
}

@Composable
fun HomePage(navController: NavController) {
    var showContent by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = { showContent = !showContent }) {
            Text("Click me!")
        }
        AnimatedVisibility(showContent) {
            val greeting = remember { Greeting().greet() }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(painterResource(Res.drawable.compose_multiplatform), null)
                Text("Compose: $greeting")
            }
        }
        Text("你好啊，中国认领", modifier = Modifier.clickable {
            navController.navigate(DetailScreen(0L))
        })
    }
}

@Composable
fun DetailPage(navController: NavHostController) {

    val scope = rememberCoroutineScope()
    var directory: PlatformFile? by remember { mutableStateOf(null) }
    val rememberFilePickerLauncher = rememberFilePickerLauncher(
        type = FileKitType.File(),
        mode = FileKitMode.Multiple(maxItems = 1),
        title = "Multipe files picker",
        directory = directory,
        onResult = {},
        dialogSettings = FileKitDialogSettings.createDefault()
    )
    var msgCount by remember {
        mutableIntStateOf(0)
    }
    Scaffold(contentWindowInsets = WindowInsets.systemBars) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            Text("这是第二个界面", modifier = Modifier.clickable {
                navController.navigate(TreePage)
            })
            Image(painter = painterResource(Res.drawable.compose_multiplatform), null)
            Button(onClick = {
                openFile(scope)
//            rememberFilePickerLauncher.launch()
            }) {
                Text("请选择文件")
            }

            Column {
                Text("发起网络请求！！！", modifier = Modifier.clickable {
                    scope.launch {
//                    ApiClient.get("article/list/0/json")
                        val user = UserRepository.getUser(0)
                        user.onSuccess { it ->
                            println(it.data.datas)
                            platformService.showToast("请求成功---数据：${it.data.datas}")
                        }
                        user.onError { code, message ->
                            println("这是错误的信息")
                            platformService.showToast("请求失败")
                        }
                    }
                })
            }
            PulseIndicator(Res.drawable.compose_multiplatform, Modifier)

            IconWithMsg(Res.drawable.icon_people_bg, msgCount)
            Button(onClick = {
                msgCount++
            }) {
                Text("加1")
            }

//        Column(modifier = Modifier.dragAndDropTarget(shouldStartDragAndDrop = {event->
//
//        }, target = {
//
//        })) {
//            Image(painter = painterResource(Res.drawable.icon_add_file_picture), null)
//            Text("请拖动文件自此")
//        }

        }
    }


}

val platformService = getPlatformService()
fun openFile(scope: CoroutineScope) {
    platformService.showToast("开始获取文件")
    scope.launch {
        FileKit.openFilePicker()
    }
}


@Serializable
data class SecondData(val id: String)

@Composable
fun SecondPage() {
    Column(modifier = Modifier.fillMaxSize()) {

    }
}